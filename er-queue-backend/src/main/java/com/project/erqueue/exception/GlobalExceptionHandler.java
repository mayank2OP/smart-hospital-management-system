package com.project.erqueue.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException e) {

        Map<String, String> error = new HashMap<>();

        error.put("error", e.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        errors.put(
                                err.getField(),
                                err.getDefaultMessage()
                        ));

        return ResponseEntity.badRequest().body(errors);
    }
}