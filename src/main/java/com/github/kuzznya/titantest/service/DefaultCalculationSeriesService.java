package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.CalculationResult;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

@Service
public class DefaultCalculationSeriesService implements CalculationSeriesService {

    private final CalculationFactoryService factoryService;
    private final CalculationProperties properties;

    public DefaultCalculationSeriesService(CalculationFactoryService factoryService,
                                           CalculationProperties properties) {
        this.factoryService = factoryService;
        this.properties = properties;
    }

    private Flux<CalculationResult> calculate(String function, int count) {
        final Calculation calculation = factoryService.createCalculation(function);
        return Flux.fromStream(IntStream.range(0, count).boxed())
                .delayElements(properties.getEvaluationDelay())
                .map(calculation::calculate);
    }

    @Override
    public Flux<UnorderedCalculationResult> calculateUnordered(String function1, String function2, int count) {
        return Flux.merge(
                calculate(function1, count)
                        .map(calculationResult -> new UnorderedCalculationResult(
                                calculationResult.getCalculationId(),
                                1,
                                calculationResult.getResult(),
                                calculationResult.getExecutionTime())
                        ),
                calculate(function2, count)
                        .map(calculationResult -> new UnorderedCalculationResult(
                                calculationResult.getCalculationId(),
                                2,
                                calculationResult.getResult(),
                                calculationResult.getExecutionTime())
                        )
        );
    }

    @Override
    public Flux<OrderedCalculationResult> calculateOrdered(String function1, String function2, int count) {
        return null;
    }
}
