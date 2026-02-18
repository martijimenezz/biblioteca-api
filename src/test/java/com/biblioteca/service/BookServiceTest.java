package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.model.Author;
import com.biblioteca.repository.BookRepository;
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

@DisplayName("BookService Unit Tests")
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Gabriel García Márquez");

        book = new Book();
        book.setId(1L);
        book.setTitle("One Hundred Years of Solitude");
        book.setIsbn("978-0060883287");
        book.setAuthor(author);
        book.setPublicationYear(1967);
        book.setCopies(5);
        book.setAvailableCopies(3);
    }

    @Test
    @DisplayName("Should retrieve all books")
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve book by ID")
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("One Hundred Years of Solitude", result.get().getTitle());
    }

    @Test
    @DisplayName("Should create a new book")
    void testCreateBook() {
        when(bookRepository.save(book)).thenReturn(book);
        Book created = bookService.createBook(book);
        assertNotNull(created);
        assertEquals("978-0060883287", created.getIsbn());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Should update an existing book")
    void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setIsbn("978-0060883287");
        updatedBook.setAuthor(author);
        updatedBook.setPublicationYear(1967);
        updatedBook.setCopies(5);
        updatedBook.setAvailableCopies(3);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.updateBook(1L, updatedBook);
        assertNotNull(result);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete a book")
    void testDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should retrieve available books")
    void testGetAvailableBooks() {
        when(bookRepository.findByAvailableCopiesGreaterThan(0))
                .thenReturn(Arrays.asList(book));
        List<Book> available = bookService.getAvailableBooks();
        assertEquals(1, available.size());
        assertTrue(available.get(0).getAvailableCopies() > 0);
    }

    @Test
    @DisplayName("Should throw exception when book not found")
    void testUpdateBookNotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookService.updateBook(999L, book));
    }
}
