package com.github.kuzznya.titantest.exception;

import lombok.Getter;

public class CalculationException extends RuntimeException {
    public CalculationException() {
        super("Calculation error");
    }

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(Throwable cause) {
        super("Calculation error", cause);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
