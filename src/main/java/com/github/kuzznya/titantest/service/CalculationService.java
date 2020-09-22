package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.CalculationResult;

public interface CalculationService {
    CalculationResult calculate(String code, int idx);
}
