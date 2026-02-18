package com.biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Loan Entity Tests")
class LoanTest {

    private Loan loan;
    private Book book;
    private Author author;
    private User user;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Test Author", "Country", 1900);
        book = new Book(1L, "Test Book", "ISBN123", author, 2000, "Description", 5, 5);
        user = new User(1L, "Test User", "test@example.com", "MEMBER001", "Test City", true);
    }

    private Loan createLoan(Book book, User user, LocalDate loanDate, LocalDate dueDate) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);
        loan.setStatus("ACTIVE");
        return loan;
    }

    @Test
    @DisplayName("Should create Loan with all fields")
    void testLoanConstructor() {
        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanDate.plusDays(14);
        LocalDate returnDate = loanDate.plusDays(12);
        
        loan = new Loan(1L, book, user, loanDate, dueDate, returnDate, "RETURNED");
        
        assertNotNull(loan);
        assertEquals(1L, loan.getId());
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(dueDate, loan.getDueDate());
        assertEquals(returnDate, loan.getReturnDate());
        assertEquals("RETURNED", loan.getStatus());
    }

    @Test
    @DisplayName("Should create Loan with default constructor")
    void testLoanDefaultConstructor() {
        loan = new Loan();
        
        assertNotNull(loan);
        assertNull(loan.getId());
        assertNull(loan.getBook());
        assertNull(loan.getUser());
        assertEquals("ACTIVE", loan.getStatus());
    }

    @Test
    @DisplayName("Should set and get loan book")
    void testSetAndGetBook() {
        LocalDate loanDate = LocalDate.now();
        loan = createLoan(book, user, loanDate, loanDate.plusDays(14));
        
        assertEquals(book, loan.getBook());
        
        Author newAuthor = new Author(2L, "New Author", "Country", 1950);
        Book newBook = new Book(2L, "New Book", "ISBN456", newAuthor, 2010, "New Desc", 3, 3);
        loan.setBook(newBook);
        assertEquals(newBook, loan.getBook());
    }

    @Test
    @DisplayName("Should set and get loan user")
    void testSetAndGetUser() {
        LocalDate loanDate = LocalDate.now();
        loan = createLoan(book, user, loanDate, loanDate.plusDays(14));
        
        assertEquals(user, loan.getUser());
        
        User newUser = new User(2L, "New User", "new@example.com", "MEMBER002", "New City", true);
        loan.setUser(newUser);
        assertEquals(newUser, loan.getUser());
    }

    @Test
    @DisplayName("Should manage loan dates")
    void testLoanDates() {
        LocalDate loanDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        
        loan = createLoan(book, user, loanDate, dueDate);
        
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(dueDate, loan.getDueDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    @DisplayName("Should set and get return date")
    void testReturnDate() {
        LocalDate loanDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 12);
        
        loan = createLoan(book, user, loanDate, dueDate);
        assertNull(loan.getReturnDate());
        
        loan.setReturnDate(returnDate);
        assertEquals(returnDate, loan.getReturnDate());
    }

    @Test
    @DisplayName("Should manage loan status")
    void testLoanStatus() {
        LocalDate loanDate = LocalDate.now();
        loan = createLoan(book, user, loanDate, loanDate.plusDays(14));
        
        assertEquals("ACTIVE", loan.getStatus());
        
        loan.setStatus("RETURNED");
        assertEquals("RETURNED", loan.getStatus());
        
        loan.setStatus("OVERDUE");
        assertEquals("OVERDUE", loan.getStatus());
    }

    @Test
    @DisplayName("Should detect overdue loan")
    void testOverdueDetection() {
        LocalDate loanDate = LocalDate.now().minusDays(20);
        LocalDate dueDate = LocalDate.now().minusDays(5);
        
        loan = createLoan(book, user, loanDate, dueDate);
        loan.setStatus("ACTIVE");
        
        assertTrue(LocalDate.now().isAfter(loan.getDueDate()));
    }

    @Test
    @DisplayName("Should check if loan is returned on time")
    void testOnTimeReturn() {
        LocalDate loanDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 12);
        
        loan = createLoan(book, user, loanDate, dueDate);
        loan.setReturnDate(returnDate);
        
        assertTrue(loan.getReturnDate().isBefore(loan.getDueDate()));
    }
}
