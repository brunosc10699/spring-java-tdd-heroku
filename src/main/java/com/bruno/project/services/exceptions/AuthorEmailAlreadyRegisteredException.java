package com.bruno.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorEmailAlreadyRegisteredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AuthorEmailAlreadyRegisteredException(String email){
        super("The email " + email + " you supplied is already registered! Try with another one.");
    }
}
