package com.biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Entity Tests")
class BookTest {

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Gabriel García Márquez", "Colombia", 1927);
    }

    private Book createBook(String title, String isbn, Author author, Integer publicationYear) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
        book.setCopies(5);
        book.setAvailableCopies(5);
        return book;
    }

    @Test
    @DisplayName("Should create Book with all fields")
    void testBookConstructor() {
        book = new Book(1L, "One Hundred Years of Solitude", "978-0-06-088328-7", 
                        author, 1967, "A classic novel", 3, 2);
        
        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("One Hundred Years of Solitude", book.getTitle());
        assertEquals("978-0-06-088328-7", book.getIsbn());
        assertEquals(author, book.getAuthor());
        assertEquals(1967, book.getPublicationYear());
        assertEquals(3, book.getCopies());
        assertEquals(2, book.getAvailableCopies());
    }

    @Test
    @DisplayName("Should create Book with default constructor")
    void testBookDefaultConstructor() {
        book = new Book();
        
        assertNotNull(book);
        assertNull(book.getId());
        assertNull(book.getTitle());
        assertNull(book.getIsbn());
        assertNull(book.getAuthor());
        assertEquals(1, book.getCopies());
        assertEquals(1, book.getAvailableCopies());
    }

    @Test
    @DisplayName("Should set and get book title")
    void testSetAndGetTitle() {
        book = createBook("Love in the Time of Cholera", "978-0-07-100305-5", author, 1985);
        
        assertEquals("Love in the Time of Cholera", book.getTitle());
        
        book.setTitle("Chronicle of a Death Foretold");
        assertEquals("Chronicle of a Death Foretold", book.getTitle());
    }

    @Test
    @DisplayName("Should set and get book ISBN")
    void testSetAndGetIsbn() {
        book = createBook("The Autumn of the Patriarch", "978-0-06-091960-2", author, 1975);
        
        assertEquals("978-0-06-091960-2", book.getIsbn());
        
        book.setIsbn("978-0-06-088329-4");
        assertEquals("978-0-06-088329-4", book.getIsbn());
    }

    @Test
    @DisplayName("Should set and get book author")
    void testSetAndGetAuthor() {
        book = createBook("Title", "ISBN", author, 2000);
        
        assertEquals(author, book.getAuthor());
        
        Author newAuthor = new Author(2L, "Julio Cortázar", "Argentina", 1914);
        book.setAuthor(newAuthor);
        assertEquals(newAuthor, book.getAuthor());
    }

    @Test
    @DisplayName("Should manage book copies and available copies")
    void testCopiesManagement() {
        book = createBook("Book Title", "ISBN123", author, 2020);
        
        assertEquals(5, book.getCopies());
        assertEquals(5, book.getAvailableCopies());
        
        book.setAvailableCopies(3);
        assertEquals(3, book.getAvailableCopies());
        
        book.setCopies(10);
        assertEquals(10, book.getCopies());
    }

    @Test
    @DisplayName("Should set optional description")
    void testOptionalDescription() {
        book = createBook("Book", "ISBN", author, 1990);
        
        assertNull(book.getDescription());
        
        book.setDescription("A masterpiece of modern literature");
        assertEquals("A masterpiece of modern literature", book.getDescription());
    }
}
