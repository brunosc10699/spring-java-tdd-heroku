package com.bruno.project.dto;

import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ISBN(message = "What you entered doesn't look like an ISBN!")
    private String isbn;

    @NotEmpty(message = "You need to enter the book's title!")
    private String title;

    @NotEmpty(message = "How many pages does the book have?")
    private Integer printLength;

    @NotEmpty(message = "What is the main language of the book?")
    private String language;

    @PastOrPresent(message = "The publication date must not be in the future!")
    private LocalDate publicationDate;

    private String publisher;

    private String urlCover;

    private BookGenre bookGenre;

    @NotEmpty(message = "You must enter at least one author name!")
    private List<Author> authors = new ArrayList<>();

    public BookDTO(Book book){
        id = book.getId();
        isbn = book.getIsbn();
        title = book.getTitle();
        printLength = book.getPrintLength();
        language = book.getLanguage();
        publicationDate = book.getPublicationDate();
        publisher = book.getPublisher();
        urlCover = book.getUrlCover();
        bookGenre = book.getBookGenre();
        authors = book.getAuthors();
    }
}
