package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.dto.CalculationRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

public abstract class CalculationApiTest {

    private final WebTestClient webClient;

    public CalculationApiTest(WebTestClient webClient) {
        this.webClient = webClient;
    }

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
            Predicate<String> resultPredicate = s ->
                    s.matches(finalI + ", 1, " + finalI + "(\\.0)?, \\d+") ||
                            s.matches(finalI + ", 2, " + finalI * 2 + "(\\.0)?, \\d+");

            step = step.expectNextMatches(resultPredicate);
            step = step.expectNextMatches(resultPredicate);
        }
        step.expectComplete().verify();
    }

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

    public void calculateUnordered_WhenFunctionReturnNull_ReturnResultWithNullOrUndefined() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/unordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "idx *= 2;",
                                "var now = new Date().getTime();\n" +
                                        "while(new Date().getTime() < now + 100){ }\n" +
                                        "return idx * 2;",
                                1
                        ))
                )
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNextMatches(s -> s.matches("0, 1, (null|undefined), \\d+"))
                .expectNextMatches(s -> s.matches("0, 2, 0(\\.0)?, \\d+"))
                .expectComplete()
                .verify();
    }

    public void calculateUnordered_WhenTooLong_ReturnExecutionTimeout() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/unordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "while (true) {} ",
                                        "return idx * 2;",
                                1
                        ))
                )
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNextMatches(s -> s.matches("0, 2, 0(\\.0)?, \\d+"))
                .expectNext("EXECUTION TIMEOUT")
                .expectComplete()
                .verify();
    }

    public void calculateOrdered_WhenValidRequest_GetResult() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/ordered")
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
            step = step.expectNextMatches(s ->
                    s.matches(finalI + ", " + finalI + "(\\.0)?, \\d+, 0, " + finalI * 2 + "(\\.0)?, \\d+, 0"));
        }
        step.expectComplete().verify();
    }

    public void calculateOrdered_WhenInvalidRequest_ReturnError() {
        webClient.post()
                .uri("/api/v1/calculations/ordered")
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

    public void calculateOrdered_WhenExecutionError_ReturnErrorMessage() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/ordered")
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
                .expectNextMatches(s -> s.matches("0, 0(\\.0)?, \\d+, 0, 0(\\.0)?, \\d+, 0"))
                .expectNextMatches(s -> s.matches("1, 1(\\.0)?, \\d+, 0, 2(\\.0)?, \\d+, 0"))
                .expectNextMatches(s -> s.matches("EXECUTION (\\d+ )?ERROR"))
                .expectComplete()
                .verify();
    }

    public void calculateOrdered_WhenTimeoutReached_ReturnExecutionTimeout() {
        Flux<String> result = webClient.post()
                .uri("/api/v1/calculations/ordered")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new CalculationRequest(
                                "if (idx === 1) while (true) {} ",
                                "return idx * 2;",
                                2
                        ))
                )
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNextCount(1)
                .expectNext("EXECUTION TIMEOUT")
                .expectComplete()
                .verify();
    }
}
