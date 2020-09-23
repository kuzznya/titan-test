package com.github.kuzznya.titantest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalCalculationException extends CalculationException {
    public InternalCalculationException() {
        super("Internal calculation error");
    }

    public InternalCalculationException(String message) {
        super("Internal calculation error: " + message);
    }

    public InternalCalculationException(Throwable cause) {
        super("Internal calculation error", cause);
    }

    public InternalCalculationException(String message, Throwable cause) {
        super("Internal calculation error: " + message, cause);
    }
}
