package com.github.kuzznya.titantest.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@Builder
public class UnorderedCalculationResult extends CalculationResult implements CsvResult {
    private final int calculationId;
    private final int funcId;

    public UnorderedCalculationResult(int calculationId, int funcId, Object result, Duration executionTime) {
        super(result, executionTime);
        this.calculationId = calculationId;
        this.funcId = funcId;
    }

    @Override
    public List<Object> getData() {
        return List.of(calculationId, funcId, super.getResult(), super.getExecutionTime());
    }
}
