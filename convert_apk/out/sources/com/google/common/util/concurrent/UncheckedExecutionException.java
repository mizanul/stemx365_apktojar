package com.google.common.util.concurrent;

public class UncheckedExecutionException extends RuntimeException {
    private static final long serialVersionUID = 0;

    protected UncheckedExecutionException() {
    }

    protected UncheckedExecutionException(String message) {
        super(message);
    }

    public UncheckedExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedExecutionException(Throwable cause) {
        super(cause);
    }
}
