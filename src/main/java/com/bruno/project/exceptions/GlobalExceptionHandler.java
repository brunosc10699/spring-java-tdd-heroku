package com.bruno.project.exceptions;

import com.bruno.project.services.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<StandardError> existingResource(
            HttpServletRequest request, ExistingResourceException exception
    ) {
        int status = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .message(exception.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
            HttpServletRequest request, ResourceNotFoundException exception
    ) {
        int status = HttpStatus.NOT_FOUND.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .message(exception.getMessage())
                .error("Not Found")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception) {
        int status = HttpStatus.BAD_REQUEST.value();
        ValidationError error = ValidationError.builder()
                .timestamp(Instant.now())
                .status(status)
                .message(exception.getMessage())
                .error("Bad Request")
                .build();
        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            error.addError(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
