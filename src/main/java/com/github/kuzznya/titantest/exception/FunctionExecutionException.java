package com.github.kuzznya.titantest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunctionExecutionException extends CalculationException {
    @Getter
    private int executionId;

    public FunctionExecutionException() {
        super("Error occurred while trying to execute the function");
    }

    public FunctionExecutionException(int executionId) {
        super("Error occurred while trying to execute the function on execution " + executionId);
        this.executionId = executionId;
    }

    public FunctionExecutionException(Throwable cause) {
        super("Error occurred while trying to execute the function", cause);
    }

    public FunctionExecutionException(int executionId, Throwable cause) {
        super("Error occurred while trying to execute the function on execution " + executionId, cause);
        this.executionId = executionId;
    }
}
