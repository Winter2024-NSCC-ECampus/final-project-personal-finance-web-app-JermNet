package com.example.j2eefinalproject.service;

import com.example.j2eefinalproject.model.Investment;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.repository.InvestmentRepository;
import com.example.j2eefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private UserRepository userRepository;

    // Method that uses repository so that only service needs to be called instead of service and repository
    public List<Investment> findByUser(User user) {
        return investmentRepository.findByUser(user);
    }

    // Add item using principal and repositories to save for current user
    public void addInvestment(Investment investment, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        investment.setUser(user);
        investmentRepository.save(investment);
    }
}