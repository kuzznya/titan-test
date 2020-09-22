package com.github.kuzznya.titantest.exception;

public class CalculationException extends RuntimeException {

    public CalculationException() {
        super("Calculation exception");
    }

    public CalculationException(String message) {
        super("Calculation exception: " + message);
    }

    public CalculationException(String message, Throwable cause) {
        super("Calculation exception: " + message, cause);
    }
}
