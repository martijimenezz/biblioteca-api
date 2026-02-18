package com.biblioteca.controller;

import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@DisplayName("AuthorController Integration Tests")
@Transactional
class AuthorControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Author author;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        author = new Author();
        author.setName("Gabriel García Márquez");
        author.setCountry("Colombia");
        author.setBirthYear(1927);
        author = authorRepository.save(author);
    }

    @Test
    @DisplayName("Should GET all authors")
    void testGetAllAuthors() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", equalTo("Gabriel García Márquez")));
    }

    @Test
    @DisplayName("Should GET author by ID")
    void testGetAuthorById() throws Exception {
        mockMvc.perform(get("/api/authors/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Gabriel García Márquez")))
                .andExpect(jsonPath("$.country", equalTo("Colombia")));
    }

    @Test
    @DisplayName("Should POST create new author")
    void testCreateAuthor() throws Exception {
        Author newAuthor = new Author();
        newAuthor.setName("Jorge Luis Borges");
        newAuthor.setCountry("Argentina");
        newAuthor.setBirthYear(1899);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAuthor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Jorge Luis Borges")))
                .andExpect(jsonPath("$.country", equalTo("Argentina")));
    }

    @Test
    @DisplayName("Should PUT update author")
    void testUpdateAuthor() throws Exception {
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Gabriel García Márquez Updated");
        updatedAuthor.setCountry("Colombia");
        updatedAuthor.setBirthYear(1927);

        mockMvc.perform(put("/api/authors/{id}", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", containsString("Updated")));
    }

    @Test
    @DisplayName("Should DELETE author")
    void testDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/api/authors/{id}", author.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 for non-existent author")
    void testGetNonExistentAuthor() throws Exception {
        mockMvc.perform(get("/api/authors/999"))
                .andExpect(status().isNotFound());
    }
}
