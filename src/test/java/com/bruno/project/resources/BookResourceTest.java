package com.bruno.project.resources;

import com.bruno.project.dto.BookDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.enums.BookGenre;
import com.bruno.project.resource.BookResource;
import com.bruno.project.services.BookService;
import com.bruno.project.services.exceptions.BookAlreadyRegisteredException;
import com.bruno.project.services.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.bruno.project.utils.JsonConversionUtil.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookResourceTest {

    private Author author = new Author(
            1l,
            "Jo Nesbø",
            LocalDate.parse("1960-03-29"),
            "jonesbo@jonesbo.com",
            null,
            "Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).",
            null
    );

    private List<Author> authors = Arrays.asList(author);

    private Book givenBook = new Book(
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

    private BookDTO expectedBook = new BookDTO(givenBook);

    private static final String URL = "/api/v1/books";

    private PageRequest pageRequest = PageRequest.of(0, 20);

    private Page<BookDTO> page;

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookResource bookResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Should return 200 Ok status when searching all books")
    void whenGETIsCalledToFindAllBooksThenReturnOkStatus() throws Exception {
        when(bookService.findAll(pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 200 Ok status when searching by title")
    void whenGETIsCalledToFindBooksByTitleThenReturnOkStatus() throws Exception {
        when(bookService.findByTitleContainingIgnoreCase(givenBook.getTitle(), pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/title?text=" + givenBook.getTitle())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 200 Ok status when searching by language")
    void whenGETIsCalledToFindBooksByLanguageThenReturnOkStatus() throws Exception {
        when(bookService.findByLanguageContainingIgnoreCase(givenBook.getLanguage(), pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/language?text=" + givenBook.getLanguage())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 200 Ok status when searching by publisher")
    void whenGETIsCalledToFindBooksByPublisherThenReturnOkStatus() throws Exception {
        when(bookService.findByPublisherContainingIgnoreCase(givenBook.getPublisher(), pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/publisher?text=" + givenBook.getPublisher())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 201 created status")
    void whenPOSTIsCalledThenReturnCreatedStatus() throws Exception {
        when(bookService.save(expectedBook)).thenReturn(expectedBook);
        mockMvc.perform(post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(expectedBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(expectedBook.getTitle())))
                .andExpect(jsonPath("$.isbn", is(expectedBook.getIsbn())))
                .andExpect(jsonPath("$.printLength", is(expectedBook.getPrintLength())))
                .andExpect(jsonPath("$.language", is(expectedBook.getLanguage())))
                .andExpect(jsonPath("$.publicationYear", is(expectedBook.getPublicationYear())))
                .andExpect(jsonPath("$.publisher", is(expectedBook.getPublisher())))
                .andExpect(jsonPath("$.urlCover", is(expectedBook.getUrlCover())))
                .andExpect(jsonPath("$.bookGenre", is(expectedBook.getBookGenre().toString())));
    }

    @Test
    @DisplayName("Must throw a BookAlreadyRegisteredException exception when a registered ISBN is provided")
    void whenPOSTIsCalledWithARegisteredISBNThenReturnBadRequestStatus() throws Exception {
        when(bookService.save(expectedBook)).thenThrow(BookAlreadyRegisteredException.class);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Must return 200 Ok status when updating book data")
    void whenPUTIsCalledToUpdateBookDataThenReturnOkStatus() throws Exception {
        when(bookService.updateById(expectedBook)).thenReturn(expectedBook);
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(expectedBook.getTitle())))
                .andExpect(jsonPath("$.isbn", is(expectedBook.getIsbn())))
                .andExpect(jsonPath("$.printLength", is(expectedBook.getPrintLength())))
                .andExpect(jsonPath("$.language", is(expectedBook.getLanguage())))
                .andExpect(jsonPath("$.publicationYear", is(expectedBook.getPublicationYear())))
                .andExpect(jsonPath("$.publisher", is(expectedBook.getPublisher())))
                .andExpect(jsonPath("$.urlCover", is(expectedBook.getUrlCover())))
                .andExpect(jsonPath("$.bookGenre", is(expectedBook.getBookGenre().toString())));
    }

    @Test
    @DisplayName("Must throw a BookNotFoundException exception when trying to update a book with an unregistered id")
    void whenPUTIsCalledWithAnUnregisteredIdThenReturnNotFoundStatus() throws Exception {
        when(bookService.updateById(expectedBook)).thenThrow(BookNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBook)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Must throw a BookAlreadyRegisteredException exception " +
            "when trying to update a book with a registered ISBN")
    void whenPUTIsCalledWithARegisteredISBNThenReturnBadRequestStatus() throws Exception {
        when(bookService.updateById(expectedBook)).thenThrow(BookAlreadyRegisteredException.class);
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Must return 204 NoContent status when deleting a book")
    void whenDELETEIsCalledThenReturnNoContentStatus() throws Exception {
        doNothing().when(bookService).deleteById(expectedBook.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/" + expectedBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Must throw a BookNotFoundException exception when an unregistered id is provided")
    void whenDELETEIsCalledWithAnUnregisteredIdThenReturnNotFoundStatus() throws Exception {
        doThrow(BookNotFoundException.class).when(bookService).deleteById(expectedBook.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/" + expectedBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
