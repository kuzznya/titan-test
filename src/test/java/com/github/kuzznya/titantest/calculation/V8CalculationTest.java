package com.github.kuzznya.titantest.calculation;

import com.github.kuzznya.titantest.service.V8CalculationFactory;
import org.junit.jupiter.api.Test;

public class V8CalculationTest extends CalculationTest {

    public V8CalculationTest() {
        super(new V8CalculationFactory());
    }

    @Test
    @Override
    public void createCalculation_WhenValidCode_ReturnCalculation() {
        super.createCalculation_WhenValidCode_ReturnCalculation();
    }

    @Test
    @Override
    public void createCalculation_WhenInvalidCode_ThrowException() {
        super.createCalculation_WhenInvalidCode_ThrowException();
    }

    @Test
    @Override
    public void calculate_WhenNoErrors_ReturnValue() {
        super.calculate_WhenNoErrors_ReturnValue();
    }

    @Test
    @Override
    public void calculate_WhenExecutionError_ThrowException() {
        super.calculate_WhenExecutionError_ThrowException();
    }
}
