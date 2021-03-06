package com.github.kuzznya.titantest.model;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8ScriptExecutionException;
import com.github.kuzznya.titantest.exception.ExecutionTimeoutException;
import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;

import java.time.Duration;
import java.time.Instant;

public class V8Calculation implements Calculation {

    private final String code;

    public V8Calculation(String code) {
        this.code = code;

        V8 runtime = V8.createV8Runtime();
        try {
            runtime.executeScript("function test(idx) {" + code + "}");
        } catch (Exception ex) {
            throw new FunctionEvaluationException(ex);
        }
        runtime.release();
    }

    @Override
    public CalculationResult calculate(int idx)
            throws FunctionEvaluationException, FunctionExecutionException {
        V8 runtime = V8.createV8Runtime();
        try {
            runtime.executeScript(code);
        } catch (Exception ex) {
            throw new FunctionEvaluationException(ex);
        }

        try {
            Instant start = Instant.now();
            Object result = runtime.executeScript("test(" + idx + ");");
            Instant end = Instant.now();

            runtime.release();
            return new CalculationResult(idx, result, Duration.between(start, end));
        } catch (V8ScriptExecutionException ex) {
            if (ex.getJSMessage().contains(CalculationScriptPreprocessor.EXECUTION_TIMEOUT_ERROR_MESSAGE))
                throw new ExecutionTimeoutException(ex);
            throw new FunctionExecutionException(idx, ex);
        }
        catch (Exception ex) {
            throw new FunctionExecutionException(idx, ex);
        }
    }
}
