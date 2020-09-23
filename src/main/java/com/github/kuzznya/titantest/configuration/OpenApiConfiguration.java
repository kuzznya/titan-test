package com.github.kuzznya.titantest.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title("Calculation API").version(appVersion));
    }

    @Bean
    public GroupedOpenApi calculationOpenApi() {
        return GroupedOpenApi.builder().group("calculations").pathsToMatch("/api/v1/calculations/**")
                .build();
    }

}
