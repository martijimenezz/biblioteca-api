package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.model.Author;
import com.biblioteca.repository.BookRepository;
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
@DisplayName("BookController Integration Tests")
@Transactional
class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = new Author();
        author.setName("Gabriel García Márquez");
        author.setCountry("Colombia");
        author = authorRepository.save(author);

        book = new Book();
        book.setTitle("One Hundred Years of Solitude");
        book.setIsbn("978-0060883287");
        book.setAuthor(author);
        book.setPublicationYear(1967);
        book.setCopies(5);
        book.setAvailableCopies(3);
        book = bookRepository.save(book);
    }

    @Test
    @DisplayName("Should GET all books")
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", equalTo("One Hundred Years of Solitude")));
    }

    @Test
    @DisplayName("Should GET book by ID")
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("One Hundred Years of Solitude")))
                .andExpect(jsonPath("$.isbn", equalTo("978-0060883287")));
    }

    @Test
    @DisplayName("Should POST create new book")
    void testCreateBook() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("Love in the Time of Cholera");
        newBook.setIsbn("978-0571209263");
        newBook.setAuthor(author);
        newBook.setPublicationYear(1985);
        newBook.setCopies(3);
        newBook.setAvailableCopies(2);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", equalTo("Love in the Time of Cholera")));
    }

    @Test
    @DisplayName("Should PUT update book")
    void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setTitle("One Hundred Years of Solitude - Updated");
        updatedBook.setIsbn("978-0060883287");
        updatedBook.setAuthor(author);
        updatedBook.setPublicationYear(1967);
        updatedBook.setCopies(5);
        updatedBook.setAvailableCopies(3);

        mockMvc.perform(put("/api/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", containsString("Updated")));
    }

    @Test
    @DisplayName("Should GET available books")
    void testGetAvailableBooks() throws Exception {
        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Should DELETE book")
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", book.getId()))
                .andExpect(status().isNoContent());
    }
}
