package org.yapframework.exceptions;

/**
 * Thrown when a request model type is not configured.
 */
public class InvalidModelTypeException extends RuntimeException {
    public InvalidModelTypeException(String message) {
        super(message);
    }
}
