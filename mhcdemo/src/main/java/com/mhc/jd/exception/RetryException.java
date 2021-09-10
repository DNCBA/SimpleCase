package com.mhc.jd.exception;

public class RetryException extends RuntimeException {

    public RetryException(String message) {
        super(message);
    }
}
