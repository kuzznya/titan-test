package com.github.kuzznya.titantest.configuration;

import com.github.kuzznya.titantest.exception.InternalCalculationException;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import com.github.kuzznya.titantest.service.CalculationFactory;
import com.github.kuzznya.titantest.service.JsCalculationFactory;
import com.github.kuzznya.titantest.service.V8CalculationFactory;
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
    public CalculationFactory calculationFactoryService() {
        switch (properties.getJsEngine()) {
            case NASHORN:
                return new JsCalculationFactory();
            case V8:
                return new V8CalculationFactory();
        }
        throw new InternalCalculationException("Cannot create CalculationFactoryService: no suitable JS engine");
    }
}
