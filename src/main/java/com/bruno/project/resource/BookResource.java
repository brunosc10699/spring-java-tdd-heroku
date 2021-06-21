package com.bruno.project.resource;

import com.bruno.project.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookResource {

    @Autowired
    private BookService bookService;
    
}
