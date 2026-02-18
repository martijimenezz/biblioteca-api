package com.biblioteca.service;

import com.biblioteca.model.Author;
import com.biblioteca.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AuthorService Unit Tests")
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Gabriel García Márquez");
        author.setCountry("Colombia");
        author.setBirthYear(1927);
    }

    @Test
    @DisplayName("Should retrieve all authors")
    void testGetAllAuthors() {
        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jorge Luis Borges");
        author2.setCountry("Argentina");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author, author2));

        List<Author> authors = authorService.getAllAuthors();

        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve author by ID")
    void testGetAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1L);

        assertTrue(result.isPresent());
        assertEquals("Gabriel García Márquez", result.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should create a new author")
    void testCreateAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        Author created = authorService.createAuthor(author);

        assertNotNull(created);
        assertEquals("Gabriel García Márquez", created.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("Should update an existing author")
    void testUpdateAuthor() {
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Gabriel García Márquez Updated");
        updatedAuthor.setCountry("Colombia");
        updatedAuthor.setBirthYear(1927);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.updateAuthor(1L, updatedAuthor);

        assertNotNull(result);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Should delete an author")
    void testDeleteAuthor() {
        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when author not found")
    void testUpdateAuthorNotFound() {
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authorService.updateAuthor(999L, author));
    }
}
