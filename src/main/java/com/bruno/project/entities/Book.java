package com.bruno.project.entities;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.enums.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer printLength;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String publicationYear;

    private String publisher;

    private String urlCover;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length = 1000)
    private String synopsis;

    private BookGenre bookGenre;

    @ManyToMany
    @JoinTable(name = "tb_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Column(nullable = false)
    private List<Author> authors = new ArrayList<>();

    public Book(BookDTO bookDTO) {
        id = bookDTO.getId();
        isbn = bookDTO.getIsbn();
        title = bookDTO.getTitle();
        printLength = bookDTO.getPrintLength();
        language = bookDTO.getLanguage();
        publicationYear = bookDTO.getPublicationYear();
        publisher = bookDTO.getPublisher();
        urlCover = bookDTO.getUrlCover();
        bookGenre = bookDTO.getBookGenre();
        authors = bookDTO.getAuthors().stream().map(Author::new).collect(Collectors.toList());
    }
}
