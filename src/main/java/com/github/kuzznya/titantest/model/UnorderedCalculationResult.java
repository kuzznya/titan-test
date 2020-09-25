package com.github.kuzznya.titantest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UnorderedCalculationResult extends CalculationResult implements CsvResult {
    private final int funcId;

    public UnorderedCalculationResult(int calculationId, int funcId, Object result, Duration executionTime) {
        super(calculationId, result, executionTime);
        this.funcId = funcId;
    }

    @Override
    public List<Object> getData() {
        return List.of(
                super.getCalculationId(),
                funcId,
                Optional.ofNullable(super.getResult()).orElse("null"),
                super.getExecutionTime().toMillis()
        );
    }
}
