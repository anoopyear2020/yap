package org.yapframework.exceptions;

/**
 * Thrown when attempting to save an out-of-date model based on the value of the version column.
 */
public class OptimisticLockingException extends RuntimeException {
    public OptimisticLockingException() {
    }

    public OptimisticLockingException(String s) {
        super(s);
    }

    public OptimisticLockingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public OptimisticLockingException(Throwable throwable) {
        super(throwable);
    }
}
