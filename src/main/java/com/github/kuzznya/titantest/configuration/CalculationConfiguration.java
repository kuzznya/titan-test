package com.github.kuzznya.titantest.configuration;

import com.github.kuzznya.titantest.exception.AppInternalException;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import com.github.kuzznya.titantest.service.CalculationFactoryService;
import com.github.kuzznya.titantest.service.JsCalculationFactoryService;
import com.github.kuzznya.titantest.service.V8CalculationFactoryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CalculationProperties.class)
public class CalculationConfiguration {

    private final CalculationProperties properties;

    public CalculationConfiguration(CalculationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public CalculationFactoryService calculationFactoryService() {
        switch (properties.getJsEngine()) {
            case NASHORN:
                return new JsCalculationFactoryService();
            case V8:
                return new V8CalculationFactoryService();
        }
        throw new AppInternalException("Cannot create CalculationFactoryService: no suitable JS engine");
    }
}
