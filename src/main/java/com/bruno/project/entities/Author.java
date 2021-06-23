package com.bruno.project.entities;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate birthDate;

    @Column(nullable = false)
    private String email;

    private String phone;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 1000)
    private String biography;

    private String urlPicture;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author(AuthorDTO authorDTO) {
        id = authorDTO.getId();
        name = authorDTO.getName();
        birthDate = authorDTO.getBirthDate();
        email = authorDTO.getEmail();
        phone = authorDTO.getPhone();
        biography = authorDTO.getBiography();
        urlPicture = authorDTO.getUrlPicture();
        books = authorDTO.getBooks().stream().map(Book::new).collect(Collectors.toList());
    }
}
