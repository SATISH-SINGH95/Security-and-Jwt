package com.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> methodResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> methodAuthenticationFailedException(AuthenticationFailedException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(response, ex.getStatus());
    }
}
