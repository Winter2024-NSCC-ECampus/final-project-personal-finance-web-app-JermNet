package com.example.j2eefinalproject.service;

import com.example.j2eefinalproject.model.Budget;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.repository.BudgetRepository;
import com.example.j2eefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    // Method that uses repository so that only service needs to be called instead of service and repository
    public List<Budget> findByUser(User user) {
        return budgetRepository.findByUser(user);
    }

    // Method that uses repository so that only service needs to be called instead of service and repository
    public Optional<Budget> findById(Long id) {
        return budgetRepository.findById(id);
    }

    // Method that uses repository so that only service needs to be called instead of service and repository
    public void save(Budget budget) {
        budgetRepository.save(budget);
    }

    // Add item using principal and repositories to save for current user
    public void addBudget(Budget budget, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        budget.setUser(user);
        budgetRepository.save(budget);
    }

    // Delete budget by id and user
    public void deleteBudgetByIdAndUser(Long id, String username) {
        Budget budget = budgetRepository.findById(id).orElse(null);
        if (budget != null && budget.getUser().getUsername().equals(username)) {
            budgetRepository.delete(budget);
        }
    }
}