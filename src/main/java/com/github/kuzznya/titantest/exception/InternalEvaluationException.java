package com.github.kuzznya.titantest.exception;

public class InternalEvaluationException extends CalculationException {
    public InternalEvaluationException() {
        super("Internal error occurred while trying to evaluate function");
    }

    public InternalEvaluationException(Throwable cause) {
        super("Internal error occurred while trying to evaluate function", cause);
    }
}
