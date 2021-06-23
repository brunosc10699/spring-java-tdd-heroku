package com.bruno.project.dto;

import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ISBN(message = "What you entered doesn't look like an ISBN!")
    @NotEmpty(message = "ISBN is required!")
    private String isbn;

    @NotEmpty(message = "You need to enter the book's title!")
    private String title;

    @NotNull(message = "How many pages does the book have?")
    private Integer printLength;

    @NotEmpty(message = "What is the main language of the book?")
    private String language;

    @NotEmpty(message = "The publication year is required!")
    private String publicationYear;

    private String publisher;

    private String urlCover;

    private String synopsis;

    private BookGenre bookGenre;

    @NotEmpty(message = "You must enter at least one author name!")
    private List<AuthorDTO> authors = new ArrayList<>();

    public BookDTO(Book book){
        id = book.getId();
        isbn = book.getIsbn();
        title = book.getTitle();
        printLength = book.getPrintLength();
        language = book.getLanguage();
        publicationYear = book.getPublicationYear();
        publisher = book.getPublisher();
        urlCover = book.getUrlCover();
        bookGenre = book.getBookGenre();
        authors = book.getAuthors().stream().map(AuthorDTO::new).collect(Collectors.toList());
    }
}
