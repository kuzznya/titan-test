package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.V8Calculation;

public class V8CalculationFactoryService implements CalculationFactoryService {
    @Override
    public Calculation createCalculation(String code) {
        return new V8Calculation(code);
    }
}
