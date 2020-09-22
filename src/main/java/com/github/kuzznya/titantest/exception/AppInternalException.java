package com.github.kuzznya.titantest.exception;

public class AppInternalException extends RuntimeException {
    public AppInternalException() {
        super("Internal error in application");
    }

    public AppInternalException(String message) {
        super(message);
    }

    public AppInternalException(Throwable cause) {
        super("Internal error in application", cause);
    }

    public AppInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
