package com.bruno.project.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_author")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private String name;

    @EqualsAndHashCode.Exclude
    private LocalDate birthDate;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private String email;

    @EqualsAndHashCode.Exclude
    private String phone;

    @EqualsAndHashCode.Exclude
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 1000)
    private String biography;

    @EqualsAndHashCode.Exclude
    private String urlPicture;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Book> books = new ArrayList<>();
}
