package com.bruno.project.resources;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.services.BookService;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<Page<BookDTO>> findByTitleContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String title, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByTitleContainingIgnoreCase(title, pageable));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<Page<BookDTO>> findByLanguageContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String language, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByLanguageContainingIgnoreCase(language, pageable));
    }

    @GetMapping(value = "/publisher")
    public ResponseEntity<Page<BookDTO>> findByPublisherContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String publisher, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByPublisherContainingIgnoreCase(publisher, pageable));
    }

    @GetMapping(value = "/author")
    public ResponseEntity<Page<BookDTO>> findBooksByAuthorName(
            @RequestParam(value = "text", defaultValue = "") String author, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findBooksByAuthorName(author, pageable));
    }

    @PostMapping
    public ResponseEntity<BookDTO> save(@Valid @RequestBody BookDTO bookDTO){
        bookDTO = bookService.save(bookDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(bookDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> updateById(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){
        try {
            bookDTO = bookService.updateById(id, bookDTO);
        } catch (BookAlreadyRegisteredException e) {
            throw new BookAlreadyRegisteredException(e.getMessage());
        } catch (Exception e) {
            throw new BookNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try {
            bookService.deleteById(id);
        } catch (Exception e) {
            throw new BookNotFoundException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
