package com.eduortza.api.adapter.exception;

public class TrajectoryException extends RuntimeException{

    public TrajectoryException(String message) {
        super(message);
    }

    public TrajectoryException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
