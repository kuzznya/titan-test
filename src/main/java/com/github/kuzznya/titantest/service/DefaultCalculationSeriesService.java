package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.OrderedCalculation;
import com.github.kuzznya.titantest.model.UnorderedCalculation;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

@Service
public class DefaultCalculationSeriesService implements CalculationSeriesService {

    private final CalculationProperties properties;

    public DefaultCalculationSeriesService(CalculationProperties properties) {
        this.properties = properties;
    }

    @Override
    public Flux<UnorderedCalculation> calculateUnordered(String function1, String function2, int count) {
        return null;
    }

    @Override
    public Flux<OrderedCalculation> calculateOrdered(String function1, String function2, int count) {
        return null;
    }
}
