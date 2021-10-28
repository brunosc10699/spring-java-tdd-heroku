package com.bruno.project.services;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import com.bruno.project.repositories.BookRepository;
import com.bruno.project.resources.BookResource;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import com.bruno.project.services.impl.BookServiceImpl;
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

    private Author author = Author.builder()
            .name("Jo Nesbø")
            .birthDate(LocalDate.parse("1960-03-29"))
            .email("jonesbo@jonesbo.com")
            .biography("Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).")
            .urlPicture("0284334234.jpg")
            .build();

    private List<Author> authors = new ArrayList<>();

    private Book book = Book.builder()
            .isbn("978-0099520320")
            .title("The Bat")
            .printLength(432)
            .language("English")
            .publicationYear("1997")
            .publisher("Harvill Secker")
            .urlCover("51264896706_e66beed079_n.jpg")
            .bookGenre(BookGenre.toEnum(6))
            .build();

    private BookDTO bookDTO = BookDTO.toDTO(book);

    private Page<Book> page = new PageImpl<Book>(Collections.singletonList(book));

    private Page<BookDTO> pageDTO;

    private PageRequest pageRequest = PageRequest.of(0, 1);

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @InjectMocks
    private BookResource bookResource;

    @Test
    @DisplayName("Should return a Page of Books")
    void whenFindAllIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findAll(pageRequest)).thenReturn(page);
        pageDTO = bookService.findAll(pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(bookDTO)));
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
        assertThat(pageDTO.getContent().get(0), is(equalTo(bookDTO)));
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
        assertThat(pageDTO.getContent().get(0), is(equalTo(bookDTO)));
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
        assertThat(pageDTO.getContent().get(0), is(equalTo(bookDTO)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books when a publisher is entered")
    void whenFindByPublisherIgnoreCaseIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findByPublisherContainingIgnoreCase("searchedText", pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findByPublisherContainingIgnoreCase("searchedText", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should return a Page of Books searched by author name")
    void whenFindBooksByAuthorNameIsCalledThenReturnAPageOfBooks() {
        when(bookRepository.findBooksByAuthorName("searchedText", pageRequest))
                .thenReturn(page);
        pageDTO = bookService.findBooksByAuthorName("searchedText", pageRequest);
        assertThat(pageDTO.getTotalPages(), is(equalTo(1)));
        assertThat(pageDTO.getSize(), is(equalTo(1)));
        assertThat(pageDTO.getTotalElements(), is(equalTo(1L)));
        assertThat(pageDTO.getContent().get(0), is(equalTo(bookDTO)));
    }

    @Test
    @DisplayName("Should return an empty Page of Books when an author name is entered")
    void whenFindBooksByAuthorNameIsCalledThenReturnAnEmptyPageOfBooks() {
        when(bookRepository.findBooksByAuthorName("searchedText", pageRequest)).thenReturn(Page.empty());
        pageDTO = bookService.findBooksByAuthorName("searchedText", pageRequest);
        assertThat(pageDTO.getContent(), is(empty()));
    }

    @Test
    @DisplayName("Should save a new Book")
    void whenANewBookIsGivenThenItShouldBeSaved() {
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        bookDTO = bookService.save(bookDTO);
        assertThat(bookDTO.getId(), is(equalTo(book.getId())));
        assertThat(bookDTO.getIsbn(), is(equalTo(book.getIsbn())));
        assertThat(bookDTO.getTitle(), is(equalTo(book.getTitle())));
        assertThat(bookDTO.getPrintLength(), is(equalTo(book.getPrintLength())));
        assertThat(bookDTO.getLanguage(), is(equalTo(book.getLanguage())));
        assertThat(bookDTO.getPublicationYear(), is(equalTo(book.getPublicationYear())));
        assertThat(bookDTO.getPublisher(), is(equalTo(book.getPublisher())));
        assertThat(bookDTO.getUrlCover(), is(equalTo(book.getUrlCover())));
        assertThat(bookDTO.getBookGenre(), is(equalTo(book.getBookGenre())));
        assertThat(bookDTO.getAuthors(), is(equalTo(book.getAuthors())));
    }

    @Test
    @DisplayName("Should throw a BookAlreadyRegisteredException exception")
    void whenISBNAlreadyRegisteredIsGivenToSaveANewBookThenThrowAnException() {
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));
        book.setId(1L);
        assertThrows(BookAlreadyRegisteredException.class, () -> bookService.save(bookDTO));
    }

    @Test
    @DisplayName("Should updateById data book by its id")
    void whenARegisteredIdIsGivenToUpdateByIdDataBookThenItShouldBeUpdateByIdd() {
        when(bookRepository.getById(book.getId())).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        bookDTO = bookService.updateById(bookDTO.getId(), bookDTO);
        assertThat(bookDTO.getId(), is(equalTo(book.getId())));
        assertThat(bookDTO.getIsbn(), is(equalTo(book.getIsbn())));
        assertThat(bookDTO.getTitle(), is(equalTo(book.getTitle())));
        assertThat(bookDTO.getPrintLength(), is(equalTo(book.getPrintLength())));
        assertThat(bookDTO.getLanguage(), is(equalTo(book.getLanguage())));
        assertThat(bookDTO.getPublicationYear(), is(equalTo(book.getPublicationYear())));
        assertThat(bookDTO.getPublisher(), is(equalTo(book.getPublisher())));
        assertThat(bookDTO.getUrlCover(), is(equalTo(book.getUrlCover())));
        assertThat(bookDTO.getBookGenre(), is(equalTo(book.getBookGenre())));
        assertThat(bookDTO.getAuthors(), is(equalTo(book.getAuthors())));
    }

    @Test
    @DisplayName("Should throw a BookNotFoundException exception when an unregistered id is supplied")
    void whenAnUnregisteredIdIsGivenToUpdateByIdBookDataThenThrowAnException() {
        when(bookRepository.getById(book.getId())).thenReturn(null);
        bookService.deleteById(bookDTO.getId());
        assertThrows(BookNotFoundException.class, () -> bookResource.updateById(bookDTO.getId(), bookDTO));
    }

    @Test
    @DisplayName("Should delete a book when a registered id is supplied")
    void whenARegisteredBookIdIsGivenThenABookShouldBeDeleted() {
        when(bookRepository.getById(book.getId())).thenReturn(book);
        doNothing().when(bookRepository).deleteById(book.getId());
        bookService.deleteById(bookDTO.getId());
        verify(bookRepository, times(1)).getById(book.getId());
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    @DisplayName("Should throw a BookNotFoundException exception when trying to delete a Book with an unregistered id")
    void whenAnUnregisteredBookIdIsGivenToDeleteABookThenThrowAnException() {
        when(bookRepository.getById(book.getId())).thenReturn(null);
        bookService.deleteById(bookDTO.getId());
        assertThrows(BookNotFoundException.class, () -> bookResource.deleteById(bookDTO.getId()));
    }
}
