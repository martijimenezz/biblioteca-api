package com.biblioteca.controller;

import com.biblioteca.model.Loan;
import com.biblioteca.model.Book;
import com.biblioteca.model.User;
import com.biblioteca.model.Author;
import com.biblioteca.repository.LoanRepository;
import com.biblioteca.repository.BookRepository;
import com.biblioteca.repository.UserRepository;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("LoanController Integration Tests")
@Transactional
class LoanControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Loan loan;
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = new Author();
        author.setName("Gabriel García Márquez");
        author =  authorRepository.save(author);

        book = new Book();
        book.setTitle("One Hundred Years of Solitude");
        book.setIsbn("978-0060883287");
        book.setAuthor(author);
        book.setCopies(5);
        book.setAvailableCopies(3);
        book = bookRepository.save(book);

        user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setMemberId("M001");
        user = userRepository.save(user);

        loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus("ACTIVE");
        loan = loanRepository.save(loan);
    }

    @Test
    @DisplayName("Should GET all loans")
    void testGetAllLoans() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].status", equalTo("ACTIVE")));
    }

    @Test
    @DisplayName("Should GET loan by ID")
    void testGetLoanById() throws Exception {
        mockMvc.perform(get("/api/loans/{id}", loan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo("ACTIVE")));
    }

    @Test
    @DisplayName("Should GET loans by user ID")
    void testGetLoansByUserId() throws Exception {
        mockMvc.perform(get("/api/loans/user/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Should POST create new loan")
    void testCreateLoan() throws Exception {
        Loan newLoan = new Loan();
        newLoan.setBook(book);
        newLoan.setUser(user);
        newLoan.setLoanDate(LocalDate.now());
        newLoan.setDueDate(LocalDate.now().plusDays(14));
        newLoan.setStatus("ACTIVE");

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newLoan)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", equalTo("ACTIVE")));
    }

    @Test
    @DisplayName("Should PUT update loan")
    void testUpdateLoan() throws Exception {
        Loan updatedLoan = new Loan();
        updatedLoan.setBook(book);
        updatedLoan.setUser(user);
        updatedLoan.setLoanDate(LocalDate.now());
        updatedLoan.setDueDate(LocalDate.now().plusDays(14));
        updatedLoan.setReturnDate(LocalDate.now());
        updatedLoan.setStatus("RETURNED");

        mockMvc.perform(put("/api/loans/{id}", loan.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedLoan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo("RETURNED")));
    }

    @Test
    @DisplayName("Should DELETE loan")
    void testDeleteLoan() throws Exception {
        mockMvc.perform(delete("/api/loans/{id}", loan.getId()))
                .andExpect(status().isNoContent());
    }
}
