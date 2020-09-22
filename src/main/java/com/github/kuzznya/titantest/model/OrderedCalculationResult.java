package com.github.kuzznya.titantest.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class OrderedCalculationResult implements CsvResult {
    private final int calculationId;
    private final CalculationResult func1Result;
    private final int func1FurtherResultsCount;
    private final CalculationResult func2Result;
    private final int func2FurtherResultsCount;

    @Override
    public List<Object> getData() {
        return List.of(
                calculationId,
                func1Result.getResult(),
                func1Result.getExecutionTime().toMillis(),
                func1FurtherResultsCount,
                func2Result.getResult(),
                func2Result.getExecutionTime().toMillis(),
                func2FurtherResultsCount
        );
    }
}
