package com.bruno.project.resources;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.services.AuthorService;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorResource {

    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "Returns a page with all registered authors")
    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(authorService.findAll(pageable));
    }

    @ApiOperation(value = "Returns a page with authors searched by name, case insensitive")
    @GetMapping(value = "/name")
    public ResponseEntity<Page<AuthorDTO>> findByNameIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String name, Pageable pageable
    ){
        return ResponseEntity.ok(authorService.findByNameContainingIgnoreCase(name, pageable));
    }

    @ApiOperation(value = "Register a new author in the database")
    @ApiResponses(value = @ApiResponse(code = 400, message = "Author email already registered"))
    @PostMapping
    public ResponseEntity<AuthorDTO> save(@Valid @RequestBody AuthorDTO authorDTO){
        try {
            authorDTO = authorService.save(authorDTO);
        } catch (Exception e) {
            throw new AuthorEmailAlreadyRegisteredException(e.getMessage());
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorDTO)
                .toUri();
        return ResponseEntity.created(uri).body(authorDTO);
    }

    @ApiOperation(value = "Update author data")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Author email already registered"),
            @ApiResponse(code = 404, message = "Author not found in the database")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> updateById(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO){
        try {
            authorDTO = authorService.updateById(id, authorDTO);
        } catch (AuthorEmailAlreadyRegisteredException e) {
            throw new AuthorEmailAlreadyRegisteredException(e.getMessage());
        } catch (AuthorNotFoundException e) {
            throw new AuthorNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new AuthorNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok(authorDTO);
    }

    @ApiOperation(value = "Exclude an author")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "It is necessary to delete the book(s) before deleting an author"),
            @ApiResponse(code = 404, message = "Author not found in the database")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try {
            authorService.deleteById(id);
        } catch (Exception e) {
            throw new AuthorNotFoundException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
