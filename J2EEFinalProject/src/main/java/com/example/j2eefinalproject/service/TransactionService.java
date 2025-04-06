package com.example.j2eefinalproject.service;

import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.j2eefinalproject.repository.TransactionRepository;
import com.example.j2eefinalproject.model.Transaction;
import java.security.Principal;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserRepository userRepository;

    // Method that uses repository so that only service needs to be called instead of service and repository
    public List<Transaction> findByUser(User user) {
        return repository.findByUser(user);
    }

    // Add item using principal and repositories to save for current user
    public void addTransaction(Transaction transaction, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        transaction.setUser(user);
        repository.save(transaction);
    }
}