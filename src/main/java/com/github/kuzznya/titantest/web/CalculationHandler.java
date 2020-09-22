package com.github.kuzznya.titantest.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CalculationHandler {

    public Mono<ServerResponse> calculate(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        Flux.just(1, 2, 3, 4, 5, 6, 7)
                                .delayElements(Duration.ofSeconds(1)),
                        Integer.class
                );
    }
}
