package com.example.j2eefinalproject.repository;

import com.example.j2eefinalproject.model.AutomatedTransaction;
import com.example.j2eefinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutomatedTransactionRepository extends JpaRepository<AutomatedTransaction, Long> {
    List<AutomatedTransaction> findByUser(User user);
}
