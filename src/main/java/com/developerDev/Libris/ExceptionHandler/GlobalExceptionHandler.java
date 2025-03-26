package com.developerDev.Libris.ExceptionHandler;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialException(BadCredentialsException ex){
        ErrorResponse errorResponse = new ErrorResponse("Credential Missing", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationException(BadCredentialsException ex){
        ErrorResponse errorResponse = new ErrorResponse("Credential Missing", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> jwtTokenException(JwtException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.getHttpStatus().value(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,ex.getHttpStatus());
    }
}
