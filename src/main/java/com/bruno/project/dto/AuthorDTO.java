package com.bruno.project.dto;

import com.bruno.project.entities.Author;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @Builder.Default
    private List<BookDTO> books = new ArrayList<>();

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
