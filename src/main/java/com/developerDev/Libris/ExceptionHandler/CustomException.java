package com.developerDev.Libris.ExceptionHandler;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    public CustomException(String message,HttpStatus status){
        super(message);
        this.httpStatus = status;
    }
}
