package com.github.kuzznya.titantest.calculation;

import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;
import com.github.kuzznya.titantest.service.CalculationFactory;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CalculationTest {

    private final CalculationFactory calculationFactory;

    public CalculationTest(CalculationFactory calculationFactory) {
        this.calculationFactory = calculationFactory;
    }

    public void createCalculation_WhenValidCode_ReturnCalculation() {
        assertDoesNotThrow(() -> calculationFactory.createCalculation(
                "for (var i = 0; i < 100; i++) {\n" +
                        "idx += 1;\n" +
                        "}\n" +
                        "return idx;"
        ));
    }

    public void createCalculation_WhenInvalidCode_ThrowException() {
        assertThrows(FunctionEvaluationException.class,
                () -> calculationFactory.createCalculation(
                        "for (var i = 0; i < 100; i++) {\n"
                )
        );
    }

    public void calculate_WhenNoErrors_ReturnValue() {
        Object result = calculationFactory
                .createCalculation("return idx * 2;")
                .calculate(1)
                .getResult();

        assertTrue(result instanceof Integer || result instanceof Double);
        if (result instanceof Integer)
            assertEquals(2, result);
        else
            assertEquals(2.0, result);

        assertEquals("String with 1",
                calculationFactory
                        .createCalculation("return 'String with ' + idx;")
                        .calculate(1)
                        .getResult()
        );
    }

    public void calculate_WhenNoReturn_ReturnNull() {
        assertNull(
                calculationFactory
                        .createCalculation("idx *= 2;")
                        .calculate(1)
                        .getResult()
        );
    }

    public void calculate_WhenExecutionError_ThrowException() {
        assertThrows(FunctionExecutionException.class,
                () -> calculationFactory
                        .createCalculation("throw new Error('some error')")
                        .calculate(0)
        );
    }
}
