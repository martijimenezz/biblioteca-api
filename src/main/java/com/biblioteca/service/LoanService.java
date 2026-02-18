package com.biblioteca.service;

import com.biblioteca.model.Loan;
import com.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, Loan loan) {
        return loanRepository.findById(id).map(existingLoan -> {
            existingLoan.setBook(loan.getBook());
            existingLoan.setUser(loan.getUser());
            existingLoan.setLoanDate(loan.getLoanDate());
            existingLoan.setDueDate(loan.getDueDate());
            existingLoan.setReturnDate(loan.getReturnDate());
            existingLoan.setStatus(loan.getStatus());
            return loanRepository.save(existingLoan);
        }).orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }
}
