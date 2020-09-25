package com.github.kuzznya.titantest.model;

import lombok.*;

import java.util.List;
import java.util.Optional;

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
                Optional.ofNullable(func1Result.getResult()).orElse("null"),
                func1Result.getExecutionTime().toMillis(),
                func1FurtherResultsCount,
                Optional.ofNullable(func2Result.getResult()).orElse("null"),
                func2Result.getExecutionTime().toMillis(),
                func2FurtherResultsCount
        );
    }
}
