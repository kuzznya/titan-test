package com.github.kuzznya.titantest.web;

import com.github.kuzznya.titantest.configuration.CalculationConfiguration;
import com.github.kuzznya.titantest.service.DefaultCalculationSeriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(properties = "titantest.js-engine=v8")
@Import({CalculationConfiguration.class, DefaultCalculationSeriesService.class, CalculationHandler.class, CalculationRouter.class})
public class V8CalculationApiTest extends CalculationApiTest {

    public V8CalculationApiTest(@Autowired WebTestClient webClient) {
        super(webClient);
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateUnordered_WhenValidRequest_GetResult() {
        super.calculateUnordered_WhenValidRequest_GetResult();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateUnordered_WhenInvalidRequest_ReturnError() {
        super.calculateUnordered_WhenInvalidRequest_ReturnError();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateUnordered_WhenExecutionError_ReturnErrorMessage() {
        super.calculateUnordered_WhenExecutionError_ReturnErrorMessage();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateUnordered_WhenFunctionReturnNull_ReturnResultWithNullOrUndefined() {
        super.calculateUnordered_WhenFunctionReturnNull_ReturnResultWithNullOrUndefined();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateUnordered_WhenTooLong_ReturnExecutionTimeout() {
        super.calculateUnordered_WhenTooLong_ReturnExecutionTimeout();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateOrdered_WhenValidRequest_GetResult() {
        super.calculateOrdered_WhenValidRequest_GetResult();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateOrdered_WhenInvalidRequest_ReturnError() {
        super.calculateOrdered_WhenInvalidRequest_ReturnError();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateOrdered_WhenExecutionError_ReturnErrorMessage() {
        super.calculateOrdered_WhenExecutionError_ReturnErrorMessage();
    }

    @Test
    @ResourceLock("V8_ENGINE")
    @Override
    public void calculateOrdered_WhenTimeoutReached_ReturnExecutionTimeout() {
        super.calculateOrdered_WhenTimeoutReached_ReturnExecutionTimeout();
    }
}
