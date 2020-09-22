package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;

public interface CalculationFactoryService {
    Calculation createCalculation(String code);
}
