package com.bruno.project.dto;

import com.bruno.project.entities.Author;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "We need you to enter the author's name!")
    private String name;

    @Past(message = "There's something wrong with the birth date you entered!")
    private LocalDate birthDate;

    @Email(message = "Enter the author's best email!")
    private String email;

    private String phone;

    @NotEmpty(message = "Write a little about the author!")
    private String biography;

    private String urlPicture;

    private List<BookDTO> books = new ArrayList<>();

    public AuthorDTO(Long id, String name, LocalDate birthDate, String email, String phone, String biography, String urlPicture) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.biography = biography;
        this.urlPicture = urlPicture;
    }

    public AuthorDTO(Author author){
        id = author.getId();
        name = author.getName();
        birthDate = author.getBirthDate();
        email = author.getEmail();
        phone = author.getPhone();
        biography = author.getBiography();
        urlPicture = author.getUrlPicture();
    }

    @JsonIgnore
    public List<BookDTO> getBooks() {
        return books;
    }
}
