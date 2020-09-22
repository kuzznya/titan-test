package com.github.kuzznya.titantest.configuration;

import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CalculationProperties.class)
public class CalculationConfiguration {
}
