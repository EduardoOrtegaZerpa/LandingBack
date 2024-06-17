package com.eduortza.api.application.exception;

public class NotValidURLException extends RuntimeException{
    public NotValidURLException(String message) {
        super(message);
    }

    public NotValidURLException(String message, Throwable cause) {
        super(message, cause);
    }
}
