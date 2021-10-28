package com.bruno.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExistingResourceException(String message) {
        super(message);
    }
}
