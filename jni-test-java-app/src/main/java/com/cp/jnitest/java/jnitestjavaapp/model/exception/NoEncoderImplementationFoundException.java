package com.cp.jnitest.java.jnitestjavaapp.model.exception;

public class NoEncoderImplementationFoundException extends RuntimeException {
    public NoEncoderImplementationFoundException() {
        super("No encoder found for the indicated type");
    }
}
