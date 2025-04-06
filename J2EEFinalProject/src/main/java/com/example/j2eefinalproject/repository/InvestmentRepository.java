package com.example.j2eefinalproject.repository;

import com.example.j2eefinalproject.model.Investment;
import com.example.j2eefinalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(User user);
}