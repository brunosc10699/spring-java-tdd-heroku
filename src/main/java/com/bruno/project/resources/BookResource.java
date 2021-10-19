package com.bruno.project.resources;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.services.BookService;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;

    @ApiOperation(value = "Returns a page with all registered books and their authors")
    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @ApiOperation(value = "Returns a page of books searched by title, case insensitive")
    @GetMapping(value = "/title")
    public ResponseEntity<Page<BookDTO>> findByTitleContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String title, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByTitleContainingIgnoreCase(title, pageable));
    }

    @ApiOperation(value = "Returns a page of books searched by language, case insensitive")
    @GetMapping(value = "/language")
    public ResponseEntity<Page<BookDTO>> findByLanguageContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String language, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByLanguageContainingIgnoreCase(language, pageable));
    }

    @ApiOperation(value = "Returns a page of books searched by publisher, case insensitive")
    @GetMapping(value = "/publisher")
    public ResponseEntity<Page<BookDTO>> findByPublisherContainingIgnoreCase(
            @RequestParam(value = "text", defaultValue = "") String publisher, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findByPublisherContainingIgnoreCase(publisher, pageable));
    }

    @ApiOperation(value = "Returns a page of books searched by author name, case sensitive")
    @GetMapping(value = "/author")
    public ResponseEntity<Page<BookDTO>> findBooksByAuthorName(
            @RequestParam(value = "text", defaultValue = "") String author, Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findBooksByAuthorName(author, pageable));
    }

    @ApiOperation(value = "Register a new book in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "ISBN already registered in database"),
            @ApiResponse(code = 404, message = "One or more authors not found in the database")
    })
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

    @ApiOperation(value = "Update book data")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "ISBN already registered in database"),
            @ApiResponse(code = 404, message = "Book or its author not found in database")
    })
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

    @ApiOperation(value = "Exclude a book")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Book not found in the database")
    })
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
