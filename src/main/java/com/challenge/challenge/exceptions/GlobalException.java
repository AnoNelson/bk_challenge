package com.challenge.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalException extends RuntimeException {
    public GlobalException(String message) {
        super(message);
    }
}