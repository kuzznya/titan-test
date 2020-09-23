package com.github.kuzznya.titantest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunctionEvaluationException extends CalculationException {

    public FunctionEvaluationException() {
        super("Function cannot be evaluated");
    }

    public FunctionEvaluationException(String reason) {
        super("Function cannot be evaluated: " + reason);
    }

    public FunctionEvaluationException(Throwable cause) {
        super("Function cannot be evaluated", cause);
    }
}
