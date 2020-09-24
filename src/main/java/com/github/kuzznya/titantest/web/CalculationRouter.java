package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.service.CalculationSeriesService;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculationRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/calculations/unordered",
                    method = RequestMethod.POST,
                    beanClass = CalculationSeriesService.class,
                    beanMethod = "calculateUnordered",
                    produces = "text/event-stream"
            ),
            @RouterOperation(
                    path = "/api/v1/calculations/ordered",
                    method = RequestMethod.POST,
                    beanClass = CalculationSeriesService.class,
                    beanMethod = "calculateOrdered",
                    produces = "text/event-stream"
            )
    })
    public RouterFunction<ServerResponse> calculationRoutes(CalculationHandler handler) {
        return RouterFunctions
                .route()
                .POST("/api/v1/calculations/unordered", handler::calculateUnordered)
                .POST("/api/v1/calculations/ordered", handler::calculateOrdered)
                .build();
    }
}
