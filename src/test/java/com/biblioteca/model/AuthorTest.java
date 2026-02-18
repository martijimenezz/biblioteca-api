package com.biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Author Entity Tests")
class AuthorTest {

    private Author author;

    private Author createAuthor(String name, String country, Integer birthYear) {
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        author.setBirthYear(birthYear);
        return author;
    }

    @Test
    @DisplayName("Should create Author with all fields")
    void testAuthorConstructor() {
        author = new Author(1L, "Gabriel García Márquez", "Colombia", 1927);
        
        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("Gabriel García Márquez", author.getName());
        assertEquals("Colombia", author.getCountry());
        assertEquals(1927, author.getBirthYear());
    }

    @Test
    @DisplayName("Should create Author with default constructor")
    void testAuthorDefaultConstructor() {
        author = new Author();
        
        assertNotNull(author);
        assertNull(author.getId());
        assertNull(author.getName());
        assertNull(author.getCountry());
        assertNull(author.getBirthYear());
    }

    @Test
    @DisplayName("Should set and get author name")
    void testSetAndGetName() {
        author = createAuthor("Mario Vargas Llosa", "Peru", 1936);
        
        assertEquals("Mario Vargas Llosa", author.getName());
        
        author.setName("Isabel Allende");
        assertEquals("Isabel Allende", author.getName());
    }

    @Test
    @DisplayName("Should set and get author country")
    void testSetAndGetCountry() {
        author = createAuthor("Jorge Luis Borges", "Argentina", 1899);
        
        assertEquals("Argentina", author.getCountry());
        
        author.setCountry("Chile");
        assertEquals("Chile", author.getCountry());
    }

    @Test
    @DisplayName("Should set and get birth year")
    void testSetAndGetBirthYear() {
        author = createAuthor("Pablo Neruda", "Chile", 1904);
        
        assertEquals(1904, author.getBirthYear());
        
        author.setBirthYear(1950);
        assertEquals(1950, author.getBirthYear());
    }

    @Test
    @DisplayName("Should validate author with minimum required fields")
    void testAuthorWithMinimumFields() {
        author = createAuthor("Anonymous", null, null);
        
        assertNotNull(author.getName());
        assertNull(author.getCountry());
        assertNull(author.getBirthYear());
    }
}
