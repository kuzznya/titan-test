package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DefaultCalculationSeriesService implements CalculationSeriesService {

    private final CalculationProperties properties;

    public DefaultCalculationSeriesService(CalculationProperties properties) {
        this.properties = properties;
    }

    @Override
    public Flux<UnorderedCalculationResult> calculateUnordered(String function1, String function2, int count) {
        return null;
    }

    @Override
    public Flux<OrderedCalculationResult> calculateOrdered(String function1, String function2, int count) {
        return null;
    }
}
