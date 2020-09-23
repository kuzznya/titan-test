package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;

public interface CalculationFactory {
    Calculation createCalculation(String code);
}
