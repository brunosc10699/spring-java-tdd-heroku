package com.bruno.project.resource;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
        return ResponseEntity.ok(authorService.findByNameContainingIgnoreCase(name, pageable));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> save(@RequestBody AuthorDTO authorDTO){
        authorDTO = authorService.save(authorDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(authorDTO)
                .toUri();
        return ResponseEntity.created(uri).body(authorDTO);
    }

    @PutMapping
    public ResponseEntity<AuthorDTO> updateById(@RequestBody AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.updateById(authorDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
