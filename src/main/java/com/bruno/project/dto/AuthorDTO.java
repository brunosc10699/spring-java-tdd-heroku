package com.bruno.project.dto;

import com.bruno.project.entities.Author;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@Builder
public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "We need you to enter the author's name!")
    private String name;

    @EqualsAndHashCode.Exclude
    @Past(message = "There's something wrong with the birth date you entered!")
    private LocalDate birthDate;

    @EqualsAndHashCode.Exclude
    @Email(message = "Enter the author's best email!")
    private String email;

    @EqualsAndHashCode.Exclude
    private String phone;

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "Write a little about the author!")
    private String biography;

    @EqualsAndHashCode.Exclude
    private String urlPicture;

    public static AuthorDTO toDTO(Author author){
        return AuthorDTO.builder()
            .id(author.getId())
            .name(author.getName())
            .birthDate(author.getBirthDate())
            .email(author.getEmail())
            .phone(author.getPhone())
            .biography(author.getBiography())
            .urlPicture(author.getUrlPicture())
            .build();
    }
}
