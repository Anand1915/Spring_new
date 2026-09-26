package com.Day8.SpringSecurityApp.SpringSecurityApp.advices;

import com.Day8.SpringSecurityApp.SpringSecurityApp.exceptions.ResourseNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourseNotFound exception) {

        ApiError apiError =
                new ApiError(
                        exception.getMessage(),
                        HttpStatus.NOT_FOUND
                );

        return new ResponseEntity<>(
                apiError,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException exception) {

        ApiError apiError =
                new ApiError(
                        "Invalid email or password",
                        HttpStatus.UNAUTHORIZED
                );

        return new ResponseEntity<>(
                apiError,
                HttpStatus.UNAUTHORIZED
        );
    }
}