package com.example.auctionhousesecurity.controller;

import com.example.auctionhousesecurity.dto.response.ExceptionResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(
        basePackageClasses = {
                AccountController.class,
                AuthenticationController.class
        }
)
public class ExceptionControllerAdvice {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyResultDataAccessException(
            EmptyResultDataAccessException exception
    ) {
         return ResponseEntity
                 .status(HttpStatus.NOT_FOUND)
                 .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errorMessages = new ArrayList<>();

        exception
                .getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    errorMessages.add(error.getDefaultMessage());
                });

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(errorMessages, LocalDateTime.now()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(
            RuntimeException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
    }
}
