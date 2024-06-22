package com.eduortza.api.adapter.exception;

public class SuscribeException extends RuntimeException{
    public SuscribeException(String message) {
        super(message);
    }

    public SuscribeException(String message, Throwable cause) {
        super(message, cause);
    }
}
