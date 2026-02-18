package com.biblioteca.service;

import com.biblioteca.model.Loan;
import com.biblioteca.model.Book;
import com.biblioteca.model.User;
import com.biblioteca.model.Author;
import com.biblioteca.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("LoanService Unit Tests")
class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel García Márquez");

        book = new Book();
        book.setId(1L);
        book.setTitle("One Hundred Years of Solitude");
        book.setIsbn("978-0060883287");
        book.setAuthor(author);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Should retrieve all loans")
    void testGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan));
        List<Loan> loans = loanService.getAllLoans();
        assertEquals(1, loans.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve loan by ID")
    void testGetLoanById() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        Optional<Loan> result = loanService.getLoanById(1L);
        assertTrue(result.isPresent());
        assertEquals("ACTIVE", result.get().getStatus());
    }

    @Test
    @DisplayName("Should create a new loan")
    void testCreateLoan() {
        when(loanRepository.save(loan)).thenReturn(loan);
        Loan created = loanService.createLoan(loan);
        assertNotNull(created);
        assertEquals("ACTIVE", created.getStatus());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    @DisplayName("Should update an existing loan")
    void testUpdateLoan() {
        Loan updatedLoan = new Loan();
        updatedLoan.setBook(book);
        updatedLoan.setUser(user);
        updatedLoan.setLoanDate(LocalDate.now());
        updatedLoan.setDueDate(LocalDate.now().plusDays(14));
        updatedLoan.setStatus("RETURNED");

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.updateLoan(1L, updatedLoan);
        assertNotNull(result);
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should delete a loan")
    void testDeleteLoan() {
        loanService.deleteLoan(1L);
        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should retrieve loans by user ID")
    void testGetLoansByUserId() {
        when(loanRepository.findByUserId(1L)).thenReturn(Arrays.asList(loan));
        List<Loan> loans = loanService.getLoansByUserId(1L);
        assertEquals(1, loans.size());
        verify(loanRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Should throw exception when loan not found")
    void testUpdateLoanNotFound() {
        when(loanRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> loanService.updateLoan(999L, loan));
    }
}
