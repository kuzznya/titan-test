package com.github.kuzznya.titantest.exception;

public class ExecutionTimeoutException extends CalculationException {

    public ExecutionTimeoutException() {
        super("Execution timeout error");
    }

    public ExecutionTimeoutException(Throwable cause) {
        super("Execution timeout error", cause);
    }
}
