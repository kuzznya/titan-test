package com.github.kuzznya.titantest.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class OrderedCalculationResultTest {

    @Test
    public void create_WhenIndicesAreEqual_DoNotThrow() {
        assertDoesNotThrow(() -> OrderedCalculationResult
                .builder()
                .func1Result(new CalculationResult(1, null, Duration.ZERO))
                .func1FurtherResultsCount(1)
                .func2Result(new CalculationResult(1, 0, Duration.ZERO))
                .build()
        );
    }

    @Test
    public void create_WhenDifferentIndices_ThrowException() {
        assertThrows(IllegalArgumentException.class, () -> OrderedCalculationResult
                .builder()
                .func1Result(new CalculationResult(1, null, Duration.ZERO))
                .func1FurtherResultsCount(1)
                .func2Result(new CalculationResult(2, 0, Duration.ZERO))
                .build()
        );
    }
}