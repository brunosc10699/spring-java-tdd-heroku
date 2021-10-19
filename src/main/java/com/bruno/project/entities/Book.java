package com.bruno.project.entities;

import com.bruno.project.enums.BookGenre;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false, unique = true)
    private String isbn;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private Integer printLength;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private String language;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private String publicationYear;

    @EqualsAndHashCode.Exclude
    private String publisher;

    @EqualsAndHashCode.Exclude
    private String urlCover;

    @EqualsAndHashCode.Exclude
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length = 1000)
    private String synopsis;

    @EqualsAndHashCode.Exclude
    private BookGenre bookGenre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private List<Author> authors = new ArrayList<>();

}
