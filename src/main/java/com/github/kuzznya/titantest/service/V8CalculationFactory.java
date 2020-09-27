package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.CalculationScriptPreprocessor;
import com.github.kuzznya.titantest.model.V8Calculation;

public class V8CalculationFactory implements CalculationFactory {
    @Override
    public Calculation createCalculation(String code, int timeoutMillis) {
        return new V8Calculation(
                new CalculationScriptPreprocessor(timeoutMillis)
                        .preprocessScript(code)
        );
    }
}
