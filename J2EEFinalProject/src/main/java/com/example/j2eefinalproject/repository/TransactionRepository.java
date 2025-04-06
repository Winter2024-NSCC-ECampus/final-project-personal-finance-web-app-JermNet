package com.example.j2eefinalproject.repository;

import com.example.j2eefinalproject.model.Transaction;
import com.example.j2eefinalproject.model.TransactionType;
import com.example.j2eefinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, TransactionType type);
}