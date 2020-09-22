package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.JsCalculation;

public class JsCalculationFactoryService implements CalculationFactoryService {
    @Override
    public Calculation createCalculation(String code) {
        return new JsCalculation(code);
    }
}
