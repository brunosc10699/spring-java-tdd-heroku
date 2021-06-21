package com.bruno.project.resources;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.entities.Book;
import com.bruno.project.resource.AuthorResource;
import com.bruno.project.services.AuthorService;
import com.bruno.project.services.exceptions.AuthorEmailAlreadyRegisteredException;
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
import java.util.ArrayList;
import java.util.List;

import static com.bruno.project.utils.JsonConversionUtil.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthorResourceTest {

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

    private Page<AuthorDTO> page;

    private PageRequest pageRequest = PageRequest.of(0, 20);

    private static final String URL = "/api/v1/authors";

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorResource authorResource;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(authorResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Must return 200 Ok status when searching for all authors")
    void whenGETIsCalledToFindAllAuthorsThenReturnOkStatus() throws Exception {
        when(authorService.findAll(pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return 200 Ok status when searching for authors by name")
    void whenGETIsCalledToFindAuthorsByNameThenReturnOkStatus() throws Exception {
        when(authorService.findByNameIgnoreCase(givenAuthor.getName(), pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/name?text=" + givenAuthor.getName())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return 201 Created status")
    void whenPOSTIsCalledThenReturnOkStatus() throws Exception {
        when(authorService.save(givenAuthor)).thenReturn(givenAuthor);
        mockMvc.perform(post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(givenAuthor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(givenAuthor.getName())))
                .andExpect(jsonPath("$.email", is(givenAuthor.getEmail())))
                .andExpect(jsonPath("$.phone", is(givenAuthor.getPhone())))
                .andExpect(jsonPath("$.biography", is(givenAuthor.getBiography())))
                .andExpect(jsonPath("$.urlPicture", is(givenAuthor.getUrlPicture())));
    }

    @Test
    @DisplayName("Must throw an AuthorEmailAlreadyRegisteredException exception when a registered email is provided")
    void whenPOSTIsCalledWithARegisteredEmailThenReturnBadRequestStatus() throws Exception {
        doThrow(AuthorEmailAlreadyRegisteredException.class).when(authorService).save(givenAuthor);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(givenAuthor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Must return 200 Ok status when updating author data")
    void whenPUTIsCalledThenReturnOkStatus() throws Exception {
        when(authorService.updateById(givenAuthor)).thenReturn(givenAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(givenAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(givenAuthor.getName())))
                .andExpect(jsonPath("$.email", is(givenAuthor.getEmail())))
                .andExpect(jsonPath("$.phone", is(givenAuthor.getPhone())))
                .andExpect(jsonPath("$.biography", is(givenAuthor.getBiography())))
                .andExpect(jsonPath("$.urlPicture", is(givenAuthor.getUrlPicture())));
    }

    @Test
    @DisplayName("Must throw 400 BadRequest status when trying to update author data with an already registered email")
    void whenPUTIsCalledWithARegisteredEmailThenReturnBadRequestStatus() throws Exception {
        doThrow(AuthorEmailAlreadyRegisteredException.class).when(authorService).updateById(givenAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(givenAuthor)))
                .andExpect(status().isBadRequest());
    }
}
