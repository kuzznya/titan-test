package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.UnorderedCalculation;
import reactor.core.publisher.Flux;

public interface CalculationService {
    Calculation calculate(String code, int idx);
}
