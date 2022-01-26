package com.cocus.triangleclassification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TriangleTypeException extends RuntimeException {

    public TriangleTypeException(String message) {
        super(message);
    }
}
