package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import reactor.core.publisher.Flux;

public interface CalculationSeriesService {
    Flux<UnorderedCalculationResult> calculateUnordered(String function1, String function2, int count);
    Flux<OrderedCalculationResult> calculateOrdered(String function1, String function2, int count);
}
