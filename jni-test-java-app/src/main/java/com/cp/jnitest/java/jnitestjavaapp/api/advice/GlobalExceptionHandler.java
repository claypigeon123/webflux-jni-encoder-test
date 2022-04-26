package com.cp.jnitest.java.jnitestjavaapp.api.advice;

import com.cp.jnitest.java.jnitestjavaapp.model.exception.InvalidPartInRequestException;
import com.cp.jnitest.java.jnitestjavaapp.model.exception.NoEncoderImplementationFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPartInRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Void> handleNoEncoderImplException(InvalidPartInRequestException e) {
        return Mono.empty();
    }

    @ExceptionHandler(NoEncoderImplementationFoundException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono<Void> handleNoEncoderImplException(NoEncoderImplementationFoundException e) {
        return Mono.empty();
    }
}
