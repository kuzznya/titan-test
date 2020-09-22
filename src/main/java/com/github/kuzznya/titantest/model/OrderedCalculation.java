package com.github.kuzznya.titantest.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class OrderedCalculation implements CsvResult {
    private final int calculationId;
    private final Calculation func1Calculation;
    private final int func1FurtherCalculationsCount;
    private final Calculation func2Calculation;
    private final int func2FurtherCalculationsCount;

    @Override
    public List<Object> getData() {
        return List.of(
                calculationId,
                func1Calculation.getResult(),
                func1Calculation.getExecutionTime(),
                func1FurtherCalculationsCount,
                func2Calculation.getResult(),
                func2Calculation.getExecutionTime(),
                func2FurtherCalculationsCount
        );
    }
}
