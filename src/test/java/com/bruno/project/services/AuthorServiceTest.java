package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private List<Book> list = new ArrayList<>();

    private Author expectedAuthor = new Author(
            1l,
            "Jo Nesbø",
            LocalDate.parse("1960-03-29"),
            "jonesbo@jonesbo.com",
            null,
            "Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).",
            null,
            list
    );

    private AuthorDTO givenAuthor = new AuthorDTO(expectedAuthor);

    private AuthorDTO authorDTO;

    private Page<Author> page = new PageImpl<Author>(Collections.singletonList(expectedAuthor));

    private Page<AuthorDTO> pageDTO;

    private PageRequest pageRequest = PageRequest.of(0, 1);

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("Should return a page of authors")
    void whenFindAllIsCalledThenReturnAPageWithAllAuthors() {
        when(authorRepository.findAll(pageRequest)).thenReturn(page);
        pageDTO = authorService.findAll(pageRequest);
        assertThat(pageDTO.getContent(), is(not(empty())));
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenAuthor)));
    }

    @Test
    @DisplayName("Should return an empty page of authors")
    void whenFindAllIsCalledThenReturnAnEmptyPage() {
        when(authorRepository.findAll(pageRequest)).thenReturn(Page.empty());
        pageDTO = authorService.findAll(pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should return a page of authors searched by name")
    void whenFindByNameIgnoreCaseIsCalledThenReturnAPageOfAuthors() {
        when(authorRepository.findByNameIgnoreCase("name", pageRequest)).thenReturn(page);
        pageDTO = authorService.findByNameIgnoreCase("name", pageRequest);
        assertThat(pageDTO.getContent(), is(not(empty())));
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenAuthor)));
    }

    @Test
    @DisplayName("Should return an empty page of authors")
    void whenFindByNameIgnoreCaseIsCalledThenReturnAnEmptyPage() {
        when(authorRepository.findByNameIgnoreCase("name", pageRequest)).thenReturn(Page.empty());
        pageDTO = authorService.findByNameIgnoreCase("name", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should create a new author")
    void whenSaveMethodIsCalledThenShouldCreateANewAuthor() {
        when(authorRepository.findByEmailIgnoreCase(expectedAuthor.getEmail())).thenReturn(Optional.empty());
        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);
        authorDTO = authorService.save(givenAuthor);
        assertThat(authorDTO.getId(), is(equalTo(expectedAuthor.getId())));
        assertThat(authorDTO.getName(), is(equalTo(expectedAuthor.getName())));
        assertThat(authorDTO.getBirthDate(), is(equalTo(expectedAuthor.getBirthDate())));
        assertThat(authorDTO.getEmail(), is(equalTo(expectedAuthor.getEmail())));
        assertThat(authorDTO.getPhone(), is(equalTo(expectedAuthor.getPhone())));
        assertThat(authorDTO.getBiography(), is(equalTo(expectedAuthor.getBiography())));
        assertThat(authorDTO.getUrlPicture(), is(equalTo(expectedAuthor.getUrlPicture())));
        assertThat(authorDTO.getBooks(), is(equalTo(expectedAuthor.getBooks())));
    }

    @Test
    @DisplayName("Should throw a AuthorEmailAlreadyRegisteredException exception " +
            "when trying to create an author with a registered email")
    void whenSaveMethodIsCalledWithARegisteredEmailThenThrowException() {
        when(authorRepository.findByEmailIgnoreCase(expectedAuthor.getEmail())).thenReturn(Optional.of(expectedAuthor));
        assertThrows(AuthorEmailAlreadyRegisteredException.class, () -> authorService.save(givenAuthor));
    }

    @Test
    @DisplayName("Should update author data by its id")
    void whenUpdateByIdMethodIsCalledThenReturnAnUpdatedAuthor() {
        when(authorRepository.findByEmailIgnoreCase(expectedAuthor.getEmail())).thenReturn(Optional.empty());
        when(authorRepository.getById(expectedAuthor.getId())).thenReturn(expectedAuthor);
        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);
        authorDTO = authorService.updateById(givenAuthor);
        assertThat(authorDTO.getId(), is(equalTo(expectedAuthor.getId())));
        assertThat(authorDTO.getName(), is(equalTo(expectedAuthor.getName())));
        assertThat(authorDTO.getBirthDate(), is(equalTo(expectedAuthor.getBirthDate())));
        assertThat(authorDTO.getEmail(), is(equalTo(expectedAuthor.getEmail())));
        assertThat(authorDTO.getPhone(), is(equalTo(expectedAuthor.getPhone())));
        assertThat(authorDTO.getBiography(), is(equalTo(expectedAuthor.getBiography())));
        assertThat(authorDTO.getUrlPicture(), is(equalTo(expectedAuthor.getUrlPicture())));
        assertThat(authorDTO.getBooks(), is(equalTo(expectedAuthor.getBooks())));
    }

    @Test
    @DisplayName("Should throw a AuthorEmailAlreadyRegisteredException exception " +
            "when trying to update an author with a new already registered email")
    void whenUpdateByIdMethodIsCalledWithANewRegisteredEmailThenThrowException() {
        when(authorRepository.findByEmailIgnoreCase(expectedAuthor.getEmail())).thenReturn(Optional.of(expectedAuthor));
        assertThrows(AuthorEmailAlreadyRegisteredException.class, () -> authorService.updateById(givenAuthor));
    }
}
