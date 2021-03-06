package com.bruno.project.resources;

import com.bruno.project.dto.AuthorDTO;
import com.bruno.project.entities.Author;
import com.bruno.project.services.exceptions.ExistingResourceException;
import com.bruno.project.services.exceptions.ResourceNotFoundException;
import com.bruno.project.services.impl.AuthorServiceImpl;
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

import static com.bruno.project.utils.JsonConversionUtil.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthorResourceTest {

    private Author expectedAuthor = Author.builder()
            .id(1L)
            .name("Jo Nesbø")
            .birthDate(LocalDate.parse("1960-03-29"))
            .email("jonesbo@jonesbo.com")
            .biography("Jo Nesbø (Norwegian: born 29 March 1960) is a Norwegian writer, musician, economist, and former soccer player and reporter. More than 3 million copies of his novels had been sold in Norway as of March 2014; his work has been translated into over 50 languages, and by 2021 had sold some 50 million copies worldwide. Known primarily for his crime novels featuring Inspector Harry Hole, Nesbø is also the main vocalist and songwriter for the Norwegian rock band Di Derre. In 2007 he released his first children's book, Doktor Proktors Prompepulver (English translation: Doctor Proctor's Fart Powder). The 2011 film Headhunters is based on Nesbø's novel Hodejegerne (The Headhunters).")
            .urlPicture("0284334234.jpg")
            .build();

    private AuthorDTO givenAuthor = AuthorDTO.toDTO(expectedAuthor);

    private AuthorDTO authorDTO;

    private Page<AuthorDTO> page;

    private PageRequest pageRequest = PageRequest.of(0, 20);

    private static final String URN = "/api/v1/authors/";

    private MockMvc mockMvc;

    @Mock
    private AuthorServiceImpl authorService;

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
    @DisplayName("(1) Must return 200 Ok status when searching for all authors")
    void whenGETIsCalledToFindAllAuthorsThenReturnOkStatus() throws Exception {
        when(authorService.findAll(pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("(2) Must return 200 Ok status when searching for authors by name")
    void whenGETIsCalledToFindAuthorsByNameThenReturnOkStatus() throws Exception {
        when(authorService.findByNameContainingIgnoreCase(givenAuthor.getName(), pageRequest)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URN + "name?text=" + givenAuthor.getName())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("(3) Must return 201 Created status")
    void whenPOSTIsCalledThenReturnOkStatus() throws Exception {
        when(authorService.save(givenAuthor)).thenReturn(givenAuthor);
        mockMvc.perform(post(URN)
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
    @DisplayName("(4) Must throw 400 BadRequest status when a registered email is provided")
    void whenPOSTIsCalledWithARegisteredEmailThenReturnBadRequestStatus() throws Exception {
        doThrow(ExistingResourceException.class).when(authorService).save(givenAuthor);
        mockMvc.perform(post(URN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(givenAuthor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("(5) Must return 200 Ok status when updating author data")
    void whenPUTIsCalledThenReturnOkStatus() throws Exception {
        when(authorService.updateById(givenAuthor.getId(), givenAuthor)).thenReturn(givenAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put(URN + givenAuthor.getId())
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
    @DisplayName("(6) Must throw 400 BadRequest status when trying to updateById author data with an already registered email")
    void whenPUTIsCalledWithARegisteredEmailThenReturnBadRequestStatus() throws Exception {
        doThrow(ExistingResourceException.class).when(authorService).updateById(givenAuthor.getId(), givenAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put(URN + givenAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(givenAuthor)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("(7) Must throw 404 NotFound status when trying to updateById author data with an unregistered id")
    void whenPUTIsCalledWithAnUnregisteredIdThenReturnNotFoundStatus() throws Exception {
        doThrow(ResourceNotFoundException.class).when(authorService).updateById(givenAuthor.getId(), givenAuthor);
        mockMvc.perform(MockMvcRequestBuilders.put(URN + givenAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(givenAuthor)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("(8) Must return 204 NoContent status when trying to delete an author")
    void whenDELETEIsCalledThenReturnNoContentStatus() throws Exception {
        doNothing().when(authorService).deleteById(givenAuthor.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(URN + givenAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("(9) Must return 404 NotFound status when author id is not registered")
    void whenDELETEIsCalledWithAnUnregisteredIdThenReturnNotFoundStatus() throws Exception {
        doThrow(ResourceNotFoundException.class).when(authorService).deleteById(givenAuthor.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete(URN + givenAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
