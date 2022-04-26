package com.cp.jnitest.java.jnitestjavaapp.model.exception;

public class InvalidPartInRequestException extends RuntimeException {
    public InvalidPartInRequestException() {
        super("Invalid part in request");
    }
}
