package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.dto.CalculationRequest;
import com.github.kuzznya.titantest.exception.ExecutionTimeoutException;
import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.service.CalculationSeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            requestBody = @RequestBody(
                    description = "Calculation params",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CalculationRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Calculation started",
                            content = @Content(mediaType = "text/event-stream",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request - calculation cannot be started"
                    )
            },
            description = "Start the calculation series & return data as soon as it is calculated"
    )
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
                                .onErrorResume(ExecutionTimeoutException.class,
                                        ex -> Mono.just("EXECUTION TIMEOUT"))
                                .onErrorResume(FunctionExecutionException.class,
                                        ex -> Mono.just("EXECUTION " + ex.getExecutionId() + " ERROR"))
                                .onErrorReturn(Predicate.not(e -> e instanceof FunctionEvaluationException),
                                        "EXECUTION ERROR"),
                        String.class
                )
        );
    }

    @Operation(
            requestBody = @RequestBody(
                    description = "Calculation params",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CalculationRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK - Calculation started",
                            content = @Content(mediaType = "text/event-stream",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request - calculation cannot be started"
                    )
            },
            description = "Start the calculation series & format data by execution index (return two results at the same time)"
    )
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
                                        .onErrorResume(ExecutionTimeoutException.class,
                                                ex -> Mono.just("EXECUTION TIMEOUT"))
                                        .onErrorResume(FunctionExecutionException.class,
                                                ex -> Mono.just("EXECUTION " + ex.getExecutionId() + " ERROR"))
                                        .onErrorReturn(Predicate.not(e -> e instanceof FunctionEvaluationException),
                                                "EXECUTION ERROR"),
                                String.class
                        )
        );
    }
}
