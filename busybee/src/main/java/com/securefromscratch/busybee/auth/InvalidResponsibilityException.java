package com.securefromscratch.busybee.auth;

/**
 * Exception thrown when responsibility/username validation fails during task creation.
 */
public class InvalidResponsibilityException extends IllegalArgumentException {
    public InvalidResponsibilityException(String message) {
        super(message);
    }

    public InvalidResponsibilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
