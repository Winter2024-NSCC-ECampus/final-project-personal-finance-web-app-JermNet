package com.example.j2eefinalproject.repository;

import com.example.j2eefinalproject.model.Budget;
import com.example.j2eefinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);
}
