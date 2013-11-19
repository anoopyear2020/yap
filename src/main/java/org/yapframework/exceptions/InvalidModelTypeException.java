package org.yapframework.exceptions;

/**
 * Thrown when a request model type is not configured.
 */
public class InvalidModelTypeException extends RuntimeException {
    public InvalidModelTypeException() {
    }

    public InvalidModelTypeException(String s) {
        super(s);
    }

    public InvalidModelTypeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidModelTypeException(Throwable throwable) {
        super(throwable);
    }
}
