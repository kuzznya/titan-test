package com.github.kuzznya.titantest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "titantest")
@Data
public class CalculationProperties {
    private int delayMillis = 1000;

    public Duration getEvaluationDelay() {
        return Duration.ofMillis(delayMillis);
    }
}
