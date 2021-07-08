package com.bruno.project.services;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.repositories.AuthorRepository;
import com.bruno.project.resources.AuthorResource;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
import com.bruno.project.services.exceptions.AuthorNotFoundException;
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
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private Author author = new Author(
            null,
            "Jo Nesbø",
            LocalDate.parse("1960-03-29"),
            "jonesbo@jonesbo.com",
            null,
            "Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).",
            "51265117593_c76eb4ccb8_n.jpg"
    );

    private AuthorDTO authorDTO = new AuthorDTO(author);

    private Page<Author> page = new PageImpl<Author>(Collections.singletonList(author));

    private Page<AuthorDTO> pageDTO;

    private PageRequest pageRequest = PageRequest.of(0, 1);

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @InjectMocks
    private AuthorResource authorResource;

    @Test
    @DisplayName("Should return a page of authors")
    void whenFindAllIsCalledThenReturnAPageWithAllAuthors() {
        when(authorRepository.findAll(pageRequest)).thenReturn(page);
        pageDTO = authorService.findAll(pageRequest);
        assertThat(pageDTO.getContent(), is(not(empty())));
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(authorDTO)));
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
        when(authorRepository.findByNameContainingIgnoreCase("name", pageRequest)).thenReturn(page);
        pageDTO = authorService.findByNameContainingIgnoreCase("name", pageRequest);
        assertThat(pageDTO.getContent(), is(not(empty())));
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(authorDTO)));
    }

    @Test
    @DisplayName("Should return an empty page of authors")
    void whenFindByNameIgnoreCaseIsCalledThenReturnAnEmptyPage() {
        when(authorRepository.findByNameContainingIgnoreCase("name", pageRequest)).thenReturn(Page.empty());
        pageDTO = authorService.findByNameContainingIgnoreCase("name", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should create a new author")
    void whenSaveMethodIsCalledThenShouldCreateANewAuthor() {
        when(authorRepository.findByEmailIgnoreCase(author.getEmail())).thenReturn(Optional.empty());
        when(authorRepository.save(author)).thenReturn(author);
        authorDTO = authorService.save(authorDTO);
        assertThat(authorDTO.getId(), is(equalTo(author.getId())));
        assertThat(authorDTO.getName(), is(equalTo(author.getName())));
        assertThat(authorDTO.getBirthDate(), is(equalTo(author.getBirthDate())));
        assertThat(authorDTO.getEmail(), is(equalTo(author.getEmail())));
        assertThat(authorDTO.getPhone(), is(equalTo(author.getPhone())));
        assertThat(authorDTO.getBiography(), is(equalTo(author.getBiography())));
        assertThat(authorDTO.getUrlPicture(), is(equalTo(author.getUrlPicture())));
    }

    @Test
    @DisplayName("Should throw an AuthorEmailAlreadyRegisteredException exception " +
            "when trying to create an author with a registered email")
    void whenSaveMethodIsCalledWithARegisteredEmailThenThrowException() {
        when(authorRepository.findByEmailIgnoreCase(author.getEmail())).thenReturn(Optional.of(author));
        author.setId(1L);
        assertThrows(AuthorEmailAlreadyRegisteredException.class, () -> authorService.save(authorDTO));
    }

    @Test
    @DisplayName("Should updateById author data by its id")
    void whenUpdateByIdMethodIsCalledThenReturnAnUpdatedAuthor() {
        when(authorRepository.findByEmailIgnoreCase(author.getEmail())).thenReturn(Optional.empty());
        when(authorRepository.getById(author.getId())).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        authorDTO = authorService.updateById(authorDTO.getId(), authorDTO);
        assertThat(authorDTO.getId(), is(equalTo(author.getId())));
        assertThat(authorDTO.getName(), is(equalTo(author.getName())));
        assertThat(authorDTO.getBirthDate(), is(equalTo(author.getBirthDate())));
        assertThat(authorDTO.getEmail(), is(equalTo(author.getEmail())));
        assertThat(authorDTO.getPhone(), is(equalTo(author.getPhone())));
        assertThat(authorDTO.getBiography(), is(equalTo(author.getBiography())));
        assertThat(authorDTO.getUrlPicture(), is(equalTo(author.getUrlPicture())));
        assertThat(authorDTO.getBooks(), is(equalTo(author.getBooks())));
    }

    @Test
    @DisplayName("Should throw an AuthorEmailAlreadyRegisteredException exception " +
            "when trying to updateById an author with a new already registered email")
    void whenUpdateByIdMethodIsCalledWithANewRegisteredEmailThenThrowException() {
        when(authorRepository.findByEmailIgnoreCase(author.getEmail())).thenReturn(Optional.of(author));
        author.setId(1L);
        assertThrows(AuthorEmailAlreadyRegisteredException.class, () -> authorService.updateById(authorDTO.getId(), authorDTO));
    }

    @Test
    @DisplayName("Should delete an author by its id")
    void whenDeleteByIdMethodIsCalledWithARegisteredIdThenDeleteAnAuthor() {
        when(authorRepository.getById(author.getId())).thenReturn(author);
        doNothing().when(authorRepository).deleteById(author.getId());
        authorService.deleteById(authorDTO.getId());
        verify(authorRepository, times(1)).getById(author.getId());
        verify(authorRepository, times(1)).deleteById(author.getId());
    }

    @Test
    @DisplayName("Should throw an AuthorNotFoundException exception when an unregistered id is supplied " +
            "to delete an author")
    void whenAnUnregisteredIdIsGivenToDeleteAnAuthorThenThrowException() {
        when(authorRepository.getById(author.getId())).thenReturn(null);
        authorService.deleteById(authorDTO.getId());
        assertThrows(AuthorNotFoundException.class, () -> authorResource.deleteById(authorDTO.getId()));
    }
}
