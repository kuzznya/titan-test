package com.github.kuzznya.titantest.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CalculationResult {
    private final int calculationId;
    private final Object result;
    private final Duration executionTime;
}
