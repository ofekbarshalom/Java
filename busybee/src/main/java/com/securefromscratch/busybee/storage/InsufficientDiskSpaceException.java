package com.securefromscratch.busybee.storage;

public class InsufficientDiskSpaceException extends RuntimeException {
    public InsufficientDiskSpaceException(String message) {
        super(message);
    }

    public InsufficientDiskSpaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
