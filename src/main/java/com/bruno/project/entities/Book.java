package com.bruno.project.entities;

import com.bruno.project.enums.BookGenre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_book")
@Data
@NoArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Column(nullable = false)
    private List<Author> authors = new ArrayList<>();

    public Book(Long id, String isbn, String title, Integer printLength, String language, String publicationYear, String publisher, String urlCover, String synopsis, BookGenre bookGenre) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.printLength = printLength;
        this.language = language;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.urlCover = urlCover;
        this.synopsis = synopsis;
        this.bookGenre = bookGenre;
    }
}
