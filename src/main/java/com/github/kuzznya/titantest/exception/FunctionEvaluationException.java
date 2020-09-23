package com.github.kuzznya.titantest.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunctionEvaluationException extends CalculationException {
    @Getter @Setter
    private int functionId;

    public FunctionEvaluationException() {
        super("Function cannot be evaluated");
    }

    public FunctionEvaluationException(int functionId) {
        super("Function " + functionId + " cannot be evaluated");
        this.functionId = functionId;
    }

    public FunctionEvaluationException(int functionId, String reason) {
        super("Function " + functionId + " cannot be evaluated: " + reason);
        this.functionId = functionId;
    }

    public FunctionEvaluationException(Throwable cause) {
        super("Function cannot be evaluated", cause);
    }

    public FunctionEvaluationException(int functionId, Throwable cause) {
        super("Function " + functionId + " cannot be evaluated", cause);
        this.functionId = functionId;
    }
}
