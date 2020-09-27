package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.exception.ExecutionTimeoutException;
import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.model.CalculationResult;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
public class DefaultCalculationSeriesService implements CalculationSeriesService {

    private final CalculationFactory calculationFactory;
    private final CalculationProperties properties;

    public DefaultCalculationSeriesService(CalculationFactory calculationFactory,
                                           CalculationProperties properties) {
        this.calculationFactory = calculationFactory;
        this.properties = properties;
    }

    private Flux<CalculationResult> calculate(String function, int count) {
        final Mono<Calculation> calculation = Mono
                .fromSupplier(() -> calculationFactory
                        .createCalculation(function, properties.getTimeoutMillis())
                );

        return calculation
                .flatMapMany(calc -> Flux.fromStream(IntStream.range(0, count).boxed())
                        .delayElements(properties.getEvaluationDelay())
                        .map(calc::calculate)
        );
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
                        )
                ,
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
        AtomicInteger counter = new AtomicInteger(0);

        Flux<CalculationResult> calculation1 = calculate(function1, count)
                .doOnNext(result -> counter.incrementAndGet());

        Flux<CalculationResult> calculation2 = calculate(function2, count)
                .doOnNext(result -> counter.decrementAndGet());

        return calculation1.zipWith(calculation2,
                (result1, result2) -> OrderedCalculationResult
                        .builder()
                        .func1Result(result1)
                        .func1FurtherResultsCount(Math.max(counter.get(), 0))
                        .func2Result(result2)
                        .func2FurtherResultsCount(Math.max(-counter.get(), 0))
                        .build()
        );
    }
}
