package com.bruno.project.resource;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @GetMapping(value = "/title")
    public ResponseEntity<Page<BookDTO>> findByTitleIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String title, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByTitleIgnoreCase(title, pageable));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<Page<BookDTO>> findByLanguageIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String language, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByLanguageIgnoreCase(language, pageable));
    }
}
