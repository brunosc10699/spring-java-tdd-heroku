package com.bruno.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthorNotFoundException(Long id){
        super("The author with id " + id + " was not found!");
    }

    public AuthorNotFoundException(String message){
        super(message);
    }
}
