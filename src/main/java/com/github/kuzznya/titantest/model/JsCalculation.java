package com.github.kuzznya.titantest.model;

import com.github.kuzznya.titantest.exception.ExecutionTimeoutException;
import com.github.kuzznya.titantest.exception.FunctionEvaluationException;
import com.github.kuzznya.titantest.exception.FunctionExecutionException;
import com.github.kuzznya.titantest.exception.InternalCalculationException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.time.Instant;

public class JsCalculation implements Calculation {

    private final ScriptEngine engine;

    public JsCalculation(String code) throws FunctionEvaluationException {
        engine = new ScriptEngineManager().getEngineByName("JavaScript");

        try {
            engine.eval(code);
        } catch (ScriptException ex) {
            throw new FunctionEvaluationException(ex);
        }
    }

    @Override
    public CalculationResult calculate(int idx) throws FunctionExecutionException {
        try {
            Invocable invocable = (Invocable) engine;

            Instant start = Instant.now();
            Object result = invocable.invokeFunction("test", idx);
            Instant end = Instant.now();

            return new CalculationResult(idx, result, Duration.between(start, end));

        } catch (ScriptException ex) {
            if (ex.getMessage().contains(CalculationScriptPreprocessor.EXECUTION_TIMEOUT_ERROR_MESSAGE))
                throw new ExecutionTimeoutException(ex);

            throw new FunctionExecutionException(idx, ex);
        } catch (NoSuchMethodException ex) {
            throw new InternalCalculationException(ex);
        }
    }
}
