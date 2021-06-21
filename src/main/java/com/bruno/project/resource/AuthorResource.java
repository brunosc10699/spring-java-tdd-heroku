package com.bruno.project.resource;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorResource {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(authorService.findAll(pageable));
    }

    @GetMapping(value = "/name")
    public ResponseEntity<Page<AuthorDTO>> findByNameIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String name, Pageable pageable
    ){
        return ResponseEntity.ok(authorService.findByNameIgnoreCase(name, pageable));
    }
}
