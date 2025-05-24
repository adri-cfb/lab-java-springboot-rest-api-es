package com.ironhackproject.labspringbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    //errores de validacion
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //falta API-KEY
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleMissingApiKey(RuntimeException ex) {
        if (ex.getMessage().contains("API-Key")) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // por ejemplo, producto no encontrado
    }

    //rango de precios invalido
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidPriceRange(IllegalArgumentException ex) {
        return new ResponseEntity<>("Rango de precios inválido: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}