package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.dto.CalculationRequest;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.service.CalculationSeriesService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
                                .map(UnorderedCalculationResult::getDataAsString),
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
                                        .map(OrderedCalculationResult::getDataAsString),
                                String.class
                        )
        );
    }

    public Mono<ServerResponse> calculateTest(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        seriesService.calculateOrdered(
                                "return idx;",
                                "return idx * 2;",
                                100
                        ).map(OrderedCalculationResult::getDataAsString),
                        String.class
                );
    }
}
