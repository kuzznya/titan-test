package com.github.kuzznya.titantest.service;

import com.github.kuzznya.titantest.exception.EvaluationException;
import com.github.kuzznya.titantest.exception.InternalEvaluationException;
import com.github.kuzznya.titantest.model.Calculation;
import com.github.kuzznya.titantest.properties.CalculationProperties;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.time.Instant;

@Service
public class JsCalculationService implements CalculationService {

    private ScriptEngine engine;

    private CalculationProperties properties;

    private Integer functionHashCode;

    public JsCalculationService(CalculationProperties properties) {
        engine = new ScriptEngineManager().getEngineByName("JavaScript");
        this.properties = properties;
    }

    private void evaluateFunction(String name, String code) throws ScriptException {
        if (functionHashCode != null && code.hashCode() == functionHashCode)
            return;
        engine.eval("function " + name + "(int executionIdx) {" + code + "}");
        functionHashCode = code.hashCode();.3    
    }

    public Calculation calculate(String code, int idx) {
        try {
            evaluateFunction("test", code);
            Invocable invocable = (Invocable) engine;

            Instant start = Instant.now();
            Object result = invocable.invokeFunction("test", idx);
            Instant end = Instant.now();

            return new Calculation(result, Duration.between(start, end));

        } catch (ScriptException ex) {
            throw new EvaluationException(ex);
        } catch (NoSuchMethodException ex) {
            throw new InternalEvaluationException(ex);
        }
    }
}
