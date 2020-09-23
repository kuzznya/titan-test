package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.configuration.CalculationConfiguration;
import com.github.kuzznya.titantest.dto.CalculationRequest;
import com.github.kuzznya.titantest.service.DefaultCalculationSeriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(properties = "titantest.js-engine=v8")
@Import({CalculationConfiguration.class, DefaultCalculationSeriesService.class, CalculationHandler.class, CalculationRouter.class})
public class CalculationApiTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void calculateUnordered_WhenValidRequest_GetResult() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/unordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "return idx;",
                                "var now = new Date().getTime();\n" +
                                        "while(new Date().getTime() < now + 100){ }\n" +
                                        "return idx * 2;",
                                5
                        ))
                )
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class).getResponseBody();

        StepVerifier.Step<String> step = StepVerifier.create(result);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            step = step.expectNextMatches(s -> s.matches(finalI + ", 1, " + finalI + "(\\.0)?, \\d+"));
            step = step.expectNextMatches(s -> s.matches(finalI + ", 2, " + finalI * 2 + "(\\.0)?, \\d+"));
        }
        step.expectComplete().verify();
    }

    @Test
    public void calculateUnordered_WhenInvalidRequest_ReturnError() {
        webClient.post()
                .uri("/api/v1/calculations/unordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "INVALID_SYNTAX_FU{N}C",
                                "var now = new Date().getTime();\n" +
                                        "while(new Date().getTime() < now + 100){ }\n" +
                                        "return idx * 2;",
                                5
                        ))
                )
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void calculateUnordered_WhenExecutionError_ReturnErrorMessage() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/unordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "if (idx === 2) throw new Error('error'); " +
                                        "return idx;",
                                "var now = new Date().getTime();\n" +
                                        "while(new Date().getTime() < now + 100){ }\n" +
                                        "return idx * 2;",
                                4
                        ))
                )
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNextMatches(s -> s.matches("0, 1, 0(\\.0)?, \\d+"))
                .expectNextMatches(s -> s.matches("0, 2, 0(\\.0)?, \\d+"))
                .expectNextMatches(s -> s.matches("1, 1, 1(\\.0)?, \\d+"))
                .expectNextMatches(s -> s.matches("1, 2, 2(\\.0)?, \\d+"))
                .expectNextMatches(s -> s.matches("EXECUTION (\\d+ )?ERROR"))
                .expectComplete()
                .verify();
    }
}
