package com.github.kuzznya.titantest.model;

import com.github.kuzznya.titantest.exception.EvaluationException;
import com.github.kuzznya.titantest.exception.InternalEvaluationException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.time.Instant;

public class JsCalculation implements Calculation {

    private final ScriptEngine engine;

    private Integer functionHashCode;

    public JsCalculation(String code) {
        engine = new ScriptEngineManager().getEngineByName("JavaScript");

        try {
            engine.eval("function test(int executionIdx) {" + code + "}");
        } catch (ScriptException ex) {
            throw new EvaluationException(ex);
        }
    }

    @Override
    public CalculationResult calculate(int idx) {
        try {
            Invocable invocable = (Invocable) engine;

            Instant start = Instant.now();
            Object result = invocable.invokeFunction("test", idx);
            Instant end = Instant.now();

            return new CalculationResult(result, Duration.between(start, end));

        } catch (ScriptException ex) {
            throw new EvaluationException(ex);
        } catch (NoSuchMethodException ex) {
            throw new InternalEvaluationException(ex);
        }
    }
}
