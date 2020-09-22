package com.github.kuzznya.titantest.model;

import com.eclipsesource.v8.V8;

import java.time.Duration;
import java.time.Instant;

public class V8Calculation implements Calculation {

    private final String code;

    public V8Calculation(String code) {
        this.code = code;
    }

    @Override
    public CalculationResult calculate(int idx) {
        V8 runtime = V8.createV8Runtime();
        runtime.executeScript("function test(idx) {" + code + "}");

        Instant start = Instant.now();
        Object result = runtime.executeScript("test(" + idx + ");");
        Instant end = Instant.now();

        runtime.release();
        return new CalculationResult(idx, result, Duration.between(start, end));
    }
}
