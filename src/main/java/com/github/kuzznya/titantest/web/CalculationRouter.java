package com.github.kuzznya.titantest.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculationRouter {

    @Bean
    public RouterFunction<ServerResponse> calculationRoutes(CalculationHandler handler) {
        return RouterFunctions
                .route()
                .POST("/api/v1/calculations", handler::calculate)
                .GET("/api/v1/calculations", handler::calculate)
                .build();
    }
}
