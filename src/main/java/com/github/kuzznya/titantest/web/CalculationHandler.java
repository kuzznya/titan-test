package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.dto.CalculationRequest;
import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.service.CalculationSeriesService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Component
public class CalculationHandler {

    private final CalculationSeriesService seriesService;

    public CalculationHandler(CalculationSeriesService seriesService) {
        this.seriesService = seriesService;
    }

    public Mono<ServerResponse> calculateUnordered(ServerRequest request) {
        Mono<CalculationRequest> bodyMono = request.bodyToMono(CalculationRequest.class);
        return bodyMono.flatMap(calculationRequest ->
                ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        seriesService.calculateUnordered(
                                calculationRequest.getFunction1(),
                                calculationRequest.getFunction2(),
                                calculationRequest.getCount())
                                .map(UnorderedCalculationResult::getDataAsString)
                                .onErrorResume(FunctionExecutionException.class,
                                        ex -> Mono.just("EXECUTION " + ex.getExecutionId() + " ERROR"))
                                .onErrorReturn(Predicate.not(e -> e instanceof FunctionEvaluationException),
                                        "EXECUTION ERROR"),
                        String.class
                )
        );
    }

    public Mono<ServerResponse> calculateOrdered(ServerRequest request) {
        Mono<CalculationRequest> bodyMono = request.bodyToMono(CalculationRequest.class);
        return bodyMono.flatMap(calculationRequest ->
                ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(
                                seriesService.calculateOrdered(
                                        calculationRequest.getFunction1(),
                                        calculationRequest.getFunction2(),
                                        calculationRequest.getCount())
                                        .map(OrderedCalculationResult::getDataAsString)
                                        .onErrorResume(FunctionExecutionException.class,
                                                ex -> Mono.just("EXECUTION " + ex.getExecutionId() + " ERROR"))
                                        .onErrorReturn(Predicate.not(e -> e instanceof FunctionEvaluationException),
                                                "EXECUTION ERROR"),
                                String.class
                        )
        );
    }
}
