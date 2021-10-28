package com.bruno.project.dto;

import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@Builder
public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @EqualsAndHashCode.Exclude
    @ISBN(message = "What you entered doesn't look like an ISBN!", type = ISBN.Type.ANY)
    @NotEmpty(message = "ISBN is required!")
    private String isbn;

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "You need to enter the book's title!")
    private String title;

    @EqualsAndHashCode.Exclude
    @NotNull(message = "How many pages does the book have?")
    private Integer printLength;

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "What is the main language of the book?")
    private String language;

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "The publication year is required!")
    private String publicationYear;

    @EqualsAndHashCode.Exclude
    private String publisher;

    @EqualsAndHashCode.Exclude
    private String urlCover;

    @EqualsAndHashCode.Exclude
    private String synopsis;

    @EqualsAndHashCode.Exclude
    private BookGenre bookGenre;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<AuthorDTO> authors = new ArrayList<>();

    public static BookDTO toDTO(Book book){
        return BookDTO.builder()
        .id(book.getId())
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .printLength(book.getPrintLength())
        .language(book.getLanguage())
        .publicationYear(book.getPublicationYear())
        .publisher(book.getPublisher())
        .urlCover(book.getUrlCover())
        .bookGenre(book.getBookGenre())
        .authors(book.getAuthors().stream().map(AuthorDTO::toDTO).collect(Collectors.toList()))
        .build();
    }
}