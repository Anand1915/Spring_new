package com.Day8.SpringSecurityApp.SpringSecurityApp.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timestamp;

    private  String error;

    private HttpStatus statusCode;

    public ApiError(String error, HttpStatus statusCode) {
        this.error = error;
        this.statusCode = statusCode;
    }

    public ApiError() {
        this.timestamp =LocalDateTime.now();
    }
}
