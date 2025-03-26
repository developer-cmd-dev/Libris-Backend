package com.developerDev.Libris.ExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtException extends RuntimeException{
    private HttpStatus status;
    public JwtException(String message,HttpStatus status){
        super(message);
        this.status=status;
    }


}
