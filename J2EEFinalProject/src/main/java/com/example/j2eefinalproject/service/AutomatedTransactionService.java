package com.example.j2eefinalproject.service;

import com.example.j2eefinalproject.model.AutomatedTransaction;
import com.example.j2eefinalproject.model.Transaction;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.repository.AutomatedTransactionRepository;
import com.example.j2eefinalproject.repository.TransactionRepository;
import com.example.j2eefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AutomatedTransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AutomatedTransactionRepository automatedTransactionRepository;

    // Method that uses repository so that only service needs to be called instead of service and repository
    public List<AutomatedTransaction> findByUser(User user) {
        return automatedTransactionRepository.findByUser(user);
    }

    // Method that uses repository so that only service needs to be called instead of service and repository
    public Optional<AutomatedTransaction> findById(Long id) {
        return automatedTransactionRepository.findById(id);
    }

    // Method that uses repository so that only service needs to be called instead of service and repository
    public void deleteById(Long id) {
        automatedTransactionRepository.deleteById(id);
    }

    // Add item using principal and repositories to save for current user
    public void addAutomatedTransaction(AutomatedTransaction automatedTransaction, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        automatedTransaction.setUser(user);
        automatedTransactionRepository.save(automatedTransaction);
    }

    // Schedule using a cron job, never actually checked since the act of checking would either be done too many times or not enough
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkRecurringTransactions() {
        List<AutomatedTransaction> automatedTransactions = automatedTransactionRepository.findAll();
        LocalDate today = LocalDate.now();

        // Helper methods to create a standard transaction from the automated one
        for (AutomatedTransaction automatedTransaction : automatedTransactions) {
            if (shouldTrigger(automatedTransaction, today)) {
                createTransactionFromAutomation(automatedTransaction);
            }
        }
    }

    // Check if the cron job should trigger, using next due date method to update the cron job should it trigger
    private boolean shouldTrigger(AutomatedTransaction automatedTransaction, LocalDate today) {
        LocalDate nextDueDate = getNextDueDate(automatedTransaction, today);
        return nextDueDate.equals(today);
    }

    // Get the next due date by updating the cron job based on frequency
    private LocalDate getNextDueDate(AutomatedTransaction automatedTransaction, LocalDate today) {
        return switch (automatedTransaction.getFrequency()) {
            case YEARLY ->
                    automatedTransaction.getStartDate().plusYears(today.getYear() - automatedTransaction.getStartDate().getYear());
            case MONTHLY ->
                    automatedTransaction.getStartDate().plusMonths(today.getMonthValue() - automatedTransaction.getStartDate().getMonthValue());
            case BIWEEKLY ->
                    automatedTransaction.getStartDate().plusWeeks(2 * (today.getDayOfYear() - automatedTransaction.getStartDate().getDayOfYear()) / 14);
            case WEEKLY ->
                    automatedTransaction.getStartDate().plusWeeks(today.getDayOfYear() - automatedTransaction.getStartDate().getDayOfYear() / 7);
        };
    }

    // Create a new transaction based on the automated transaction details
    private void createTransactionFromAutomation(AutomatedTransaction automation) {
        Transaction newTransaction = new Transaction();
        newTransaction.setCategory(automation.getCategory());
        newTransaction.setAmount(automation.getAmount());
        newTransaction.setType(automation.getType());
        newTransaction.setUser(automation.getUser());
        transactionRepository.save(newTransaction);
    }
}