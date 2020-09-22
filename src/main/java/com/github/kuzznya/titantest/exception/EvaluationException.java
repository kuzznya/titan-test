package com.github.kuzznya.titantest.exception;

public class EvaluationException extends CalculationException {

    public EvaluationException(String message) {
        super("Evaluation exception: " + message);
    }

    public EvaluationException(Throwable cause) {
        super("Evaluation exception", cause);
    }

    public EvaluationException(String message, Throwable cause) {
        super("Evaluation exception: " + message, cause);
    }
}
