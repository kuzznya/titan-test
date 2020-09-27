package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.CalculationScriptPreprocessor;
import com.github.kuzznya.titantest.model.JsCalculation;

public class JsCalculationFactory implements CalculationFactory {
    @Override
    public Calculation createCalculation(String code, int timeoutMillis) {
        return new JsCalculation(
                new CalculationScriptPreprocessor(timeoutMillis)
                        .preprocessScript(code)
        );
    }
}
