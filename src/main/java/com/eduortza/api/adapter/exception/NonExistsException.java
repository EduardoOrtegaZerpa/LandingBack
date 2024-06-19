package com.eduortza.api.adapter.exception;

public class NonExistsException extends RuntimeException{

        public NonExistsException(String message) {
            super(message);
        }

        public NonExistsException(String message, Throwable cause) {
            super(message, cause);
        }
}
