package com.github.kuzznya.titantest.calculation;

import com.github.kuzznya.titantest.service.JsCalculationFactory;
import org.junit.jupiter.api.Test;

public class NashornCalculationTest extends CalculationTest {

    public NashornCalculationTest() {
        super(new JsCalculationFactory());
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

    @Override
    public void calculate_WhenNoReturn_ReturnNull() {
        super.calculate_WhenNoReturn_ReturnNull();
    }

    @Test
    @Override
    public void calculate_WhenExecutionError_ThrowException() {
        super.calculate_WhenExecutionError_ThrowException();
    }
}
