package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
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
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private Author author = new Author(
            1l,
            "Jo Nesbø",
            LocalDate.parse("1960-03-29"),
            "jonesbo@jonesbo.com",
            null,
            "Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).",
            null
    );

    private List<Author> authors = new ArrayList<>();

    private Book expectedBook = new Book(
            1L,
            "978-0099520320",
            "The Bat",
            432,
            "English",
            "1997",
            "Harvill Secker",
            null,
            null,
            BookGenre.toEnum(6)
    );

    private BookDTO givenBook = new BookDTO(expectedBook);

    private Page<Book> page = new PageImpl<Book>(Collections.singletonList(expectedBook));

    private Page<BookDTO> pageDTO;

    private PageRequest pageRequest = PageRequest.of(0, 1);

    private BookDTO bookDTO;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Should return a Page of Books")
    void whenFindAllIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findAll(pageRequest)).thenReturn(page);
        pageDTO = bookService.findAll(pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenBook)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books")
    void whenFindAllIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findAll(pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findAll(pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should return a Page of Books searched by title")
    void whenFindByTitleIgnoreCaseIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findByTitleContainingIgnoreCase("searchedText", pageRequest))
                .thenReturn(page);
        pageDTO = bookService.findByTitleContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenBook)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books when a language is entered")
    void whenFindByTitleIgnoreCaseIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findByLanguageContainingIgnoreCase("searchedText", pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findByLanguageContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should return a Page of Books searched by language")
    void whenFindByLanguageIgnoreCaseIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findByLanguageContainingIgnoreCase("searchedText", pageRequest))
                .thenReturn(page);
        pageDTO = bookService.findByLanguageContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenBook)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books when a language is entered")
    void whenFindByLanguageIgnoreCaseIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findByLanguageContainingIgnoreCase("searchedText", pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findByLanguageContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should return a Page of Books searched by publisher")
    void whenFindByPublisherIgnoreCaseIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findByPublisherContainingIgnoreCase("searchedText", pageRequest))
                .thenReturn(page);
        pageDTO = bookService.findByPublisherContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(givenBook)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books when a publisher is entered")
    void whenFindByPublisherIgnoreCaseIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findByPublisherContainingIgnoreCase("searchedText", pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findByPublisherContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should save a new Book")
    void whenANewBookIsGivenThenItShouldBeSaved() {
        when(bookRepository.findByIsbn(expectedBook.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(expectedBook)).thenReturn(expectedBook);
        bookDTO = bookService.save(givenBook);
        assertThat(bookDTO.getId(), is(equalTo(expectedBook.getId())));
        assertThat(bookDTO.getIsbn(), is(equalTo(expectedBook.getIsbn())));
        assertThat(bookDTO.getTitle(), is(equalTo(expectedBook.getTitle())));
        assertThat(bookDTO.getPrintLength(), is(equalTo(expectedBook.getPrintLength())));
        assertThat(bookDTO.getLanguage(), is(equalTo(expectedBook.getLanguage())));
        assertThat(bookDTO.getPublicationYear(), is(equalTo(expectedBook.getPublicationYear())));
        assertThat(bookDTO.getPublisher(), is(equalTo(expectedBook.getPublisher())));
        assertThat(bookDTO.getUrlCover(), is(equalTo(expectedBook.getUrlCover())));
        assertThat(bookDTO.getBookGenre(), is(equalTo(expectedBook.getBookGenre())));
        assertThat(bookDTO.getAuthors(), is(equalTo(expectedBook.getAuthors())));
    }

    @Test
    @DisplayName("Should throw a BookAlreadyRegisteredException exception")
    void whenISBNAlreadyRegisteredIsGivenToSaveANewBookThenThrowAnException() {
        when(bookRepository.findByIsbn(expectedBook.getIsbn())).thenReturn(Optional.of(expectedBook));
        assertThrows(BookAlreadyRegisteredException.class, () -> bookService.save(givenBook));
    }

    @Test
    @DisplayName("Should update data book by its id")
    void whenARegisteredIdIsGivenToUpdateDataBookThenItShouldBeUpdated() {
        when(bookRepository.getById(expectedBook.getId())).thenReturn(expectedBook);
        when(bookRepository.save(expectedBook)).thenReturn(expectedBook);
        bookDTO = bookService.updateById(givenBook);
        assertThat(bookDTO.getId(), is(equalTo(expectedBook.getId())));
        assertThat(bookDTO.getIsbn(), is(equalTo(expectedBook.getIsbn())));
        assertThat(bookDTO.getTitle(), is(equalTo(expectedBook.getTitle())));
        assertThat(bookDTO.getPrintLength(), is(equalTo(expectedBook.getPrintLength())));
        assertThat(bookDTO.getLanguage(), is(equalTo(expectedBook.getLanguage())));
        assertThat(bookDTO.getPublicationYear(), is(equalTo(expectedBook.getPublicationYear())));
        assertThat(bookDTO.getPublisher(), is(equalTo(expectedBook.getPublisher())));
        assertThat(bookDTO.getUrlCover(), is(equalTo(expectedBook.getUrlCover())));
        assertThat(bookDTO.getBookGenre(), is(equalTo(expectedBook.getBookGenre())));
        assertThat(bookDTO.getAuthors(), is(equalTo(expectedBook.getAuthors())));
    }

    @Test
    @DisplayName("Should throw a BookNotFoundException exception when an unregistered id is supplied")
    void whenAnUnregisteredIdIsGivenToUpdateDataBookThenThrowAnException() {
        when(bookRepository.getById(expectedBook.getId())).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.updateById(givenBook));
    }

    @Test
    @DisplayName("Should delete a book when a registered id is supplied")
    void whenARegisteredBookIdIsGivenThenABookShouldBeDeleted() {
        when(bookRepository.getById(expectedBook.getId())).thenReturn(expectedBook);
        doNothing().when(bookRepository).deleteById(expectedBook.getId());
        bookService.deleteById(givenBook.getId());
        verify(bookRepository, times(1)).getById(expectedBook.getId());
        verify(bookRepository, times(1)).deleteById(expectedBook.getId());
    }

    @Test
    @DisplayName("Should throw a BookNotFoundException exception when an unregistered id is supplied")
    void whenAnUnregisteredBookIdIsGivenThenThrowAnException() {
        when(bookRepository.getById(expectedBook.getId())).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(givenBook.getId()));
    }
}
