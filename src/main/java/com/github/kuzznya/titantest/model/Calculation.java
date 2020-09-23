package com.github.kuzznya.titantest.model;

import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;

public interface Calculation {
    CalculationResult calculate(int idx) throws FunctionEvaluationException, FunctionExecutionException;
}
