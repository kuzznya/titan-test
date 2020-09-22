package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.OrderedCalculation;
import com.github.kuzznya.titantest.model.UnorderedCalculation;
import reactor.core.publisher.Flux;

public interface CalculationSeriesService {
    Flux<UnorderedCalculation> calculateUnordered(String function1, String function2, int count);
    Flux<OrderedCalculation> calculateOrdered(String function1, String function2, int count);
}
