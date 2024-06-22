package com.eduortza.api.adapter.exception;

public class UnsuscribeException extends RuntimeException{

        public UnsuscribeException(String message) {
            super(message);
        }

        public UnsuscribeException(String message, Throwable cause) {
            super(message, cause);
        }
}
