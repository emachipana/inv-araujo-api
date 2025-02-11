package com.inversionesaraujo.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.inversionesaraujo.api.business.payload.MessageResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = "Error de integridad de datos.";

        if (ex.getMessage().contains("Duplicate entry")) {
            errorMessage = "El registro ya existe en la base de datos.";
        } else if (ex.getMessage().contains("foreign key constraint fails")) {
            errorMessage = "No se puede eliminar o modificar porque está siendo referenciado en otra tabla.";
        }

        return ResponseEntity.status(400).body(MessageResponse
            .builder()
            .message(errorMessage)
            .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(400).body(MessageResponse
            .builder()
            .data(errors)
            .message("Errores de validación")
            .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<MessageResponse> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(MessageResponse
            .builder()
            .message(ex.getReason())
            .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(500).body(MessageResponse
            .builder()
            .message("Error interno del servidor: " + ex.getMessage())
            .build());
    }
}
