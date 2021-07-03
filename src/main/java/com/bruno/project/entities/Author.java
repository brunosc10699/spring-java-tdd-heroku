package com.bruno.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_author")
@Data
@NoArgsConstructor
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

    @JsonIgnore
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Author(Long id, String name, LocalDate birthDate, String email, String phone, String biography, String urlPicture) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.biography = biography;
        this.urlPicture = urlPicture;
    }
}
