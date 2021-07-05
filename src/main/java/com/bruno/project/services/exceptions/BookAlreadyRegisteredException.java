package com.bruno.project.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAlreadyRegisteredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BookAlreadyRegisteredException(String message){
        super("The books ISBN " + message + " that you are trying to register, is already registered!");
    }
}
