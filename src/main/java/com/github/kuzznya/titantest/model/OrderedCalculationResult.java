package com.github.kuzznya.titantest.model;

import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@Builder
public class OrderedCalculationResult implements CsvResult {
    private final CalculationResult func1Result;
    private final int func1FurtherResultsCount;
    private final CalculationResult func2Result;
    private final int func2FurtherResultsCount;

    public OrderedCalculationResult(CalculationResult func1Result,
                                    int func1FurtherResultsCount,
                                    CalculationResult func2Result,
                                    int func2FurtherResultsCount) {
        if (func1Result.getCalculationId() != func2Result.getCalculationId())
            throw new IllegalArgumentException("Execution ids of two results are different");
        this.func1Result = func1Result;
        this.func1FurtherResultsCount = func1FurtherResultsCount;
        this.func2Result = func2Result;
        this.func2FurtherResultsCount = func2FurtherResultsCount;
    }

    public int getCalculationId() {
        return func1Result.getCalculationId();
    }

    @Override
    public List<Object> getData() {
        return List.of(
                getCalculationId(),
                Optional.ofNullable(func1Result.getResult()).orElse("null"),
                func1Result.getExecutionTime().toMillis(),
                func1FurtherResultsCount,
                Optional.ofNullable(func2Result.getResult()).orElse("null"),
                func2Result.getExecutionTime().toMillis(),
                func2FurtherResultsCount
        );
    }
}
