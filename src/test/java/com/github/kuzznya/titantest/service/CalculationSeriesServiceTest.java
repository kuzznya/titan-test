package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.exception.ExecutionTimeoutException;
import com.github.kuzznya.titantest.model.OrderedCalculationResult;
import com.github.kuzznya.titantest.model.UnorderedCalculationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@SpringBootTest(properties = {"titantest.delay-millis=100", "titantest.timeout-millis=3000"})
public class CalculationSeriesServiceTest {

    private final CalculationSeriesService seriesService;

    public CalculationSeriesServiceTest(@Autowired CalculationSeriesService seriesService) {
        this.seriesService = seriesService;
    }

    private boolean isResultEqualToInt(int expected, Object result) {
        if (result instanceof Integer)
            return result.equals(expected);
        else
            return Double.valueOf(expected).equals(result);
    }

    @Test
    public void calculateUnordered_WhenValidFunctions_ReturnValidResults() {
        Flux<UnorderedCalculationResult> result =
                seriesService.calculateUnordered(
                        "return idx + 1;",
                        "return idx * 2;",
                        3
                );

        Predicate<UnorderedCalculationResult> resultPredicate = calcResult -> {
            int expected = calcResult.getFuncId() == 1 ?
                    calcResult.getCalculationId() + 1 : calcResult.getCalculationId() * 2;

            return isResultEqualToInt(expected, calcResult.getResult());
        };

        StepVerifier.create(result)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectComplete()
                .verify();
    }

    @Test
    public void calculateUnordered_WhenTimeoutReached_ThrowException() {
        Flux<UnorderedCalculationResult> result =
                seriesService.calculateUnordered(
                        "while (true) {}",
                        "return idx;",
                        1
                );

        StepVerifier.create(result)
                .expectNextCount(1)
                .expectError(ExecutionTimeoutException.class)
                .verify();

        Flux<UnorderedCalculationResult> result2 =
                seriesService.calculateUnordered(
                        "for (;;) idx += 1;",
                        "return idx;",
                        1
                );

        StepVerifier.create(result2)
                .expectNextCount(1)
                .expectError(ExecutionTimeoutException.class)
                .verify();
    }

    @Test
    public void calculateOrdered_WhenValidFunctions_ReturnValidResults() {
        Flux<OrderedCalculationResult> result =
                seriesService.calculateOrdered(
                        "return idx + 1;",
                        "return idx * 2;",
                        3
                );

        Predicate<OrderedCalculationResult> resultPredicate = calcResult ->
                isResultEqualToInt(
                        calcResult.getCalculationId() + 1,
                        calcResult.getFunc1Result().getResult()
                ) && isResultEqualToInt(
                        calcResult.getCalculationId() * 2,
                        calcResult.getFunc2Result().getResult()
                );

        StepVerifier.create(result)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectNextMatches(resultPredicate)
                .expectComplete()
                .verify();
    }

    @Test
    public void calculateOrdered_WhenTimeoutReached_ThrowException() {
        Flux<OrderedCalculationResult> result =
                seriesService.calculateOrdered(
                        "while (true) {}",
                        "return idx;",
                        3
                );

        StepVerifier.create(result)
                .expectError(ExecutionTimeoutException.class)
                .verify();

        Flux<OrderedCalculationResult> result2 =
                seriesService.calculateOrdered(
                        "for (;;) idx += 1;",
                        "return idx;",
                        3
                );

        StepVerifier.create(result2)
                .expectError(ExecutionTimeoutException.class)
                .verify();
    }

}