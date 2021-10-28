package com.bruno.project.exceptions;

import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
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

    @ExceptionHandler(AuthorEmailAlreadyRegisteredException.class)
    public ResponseEntity<StandardError> authorEmailAlreadyRegistered (
            HttpServletRequest request, AuthorEmailAlreadyRegisteredException exception
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

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<StandardError> authorNotFound(
            HttpServletRequest request, AuthorNotFoundException exception
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

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<StandardError> bookNotFound (
            HttpServletRequest request, BookNotFoundException exception
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

    @ExceptionHandler(BookAlreadyRegisteredException.class)
    public ResponseEntity<StandardError> bookAlreadyRegistered (
            HttpServletRequest request, BookAlreadyRegisteredException exception
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
