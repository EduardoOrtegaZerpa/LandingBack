package com.eduortza.api.adapter.exception;

public class JwtAuthorizationException extends RuntimeException{
    public JwtAuthorizationException(String message) {
        super(message);
    }

    public JwtAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
