package com.eduortza.api.adapter.exception;

public class BlogPostException extends RuntimeException{
    public BlogPostException(String message) {
        super(message);
    }

    public BlogPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
