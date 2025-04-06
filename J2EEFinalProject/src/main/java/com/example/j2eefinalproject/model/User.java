package com.example.j2eefinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

// User lombok to make things cleaner
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private List<Investment> investments;

    @OneToMany(mappedBy = "user")
    private List<AutomatedTransaction> automatedTransactions;

    @OneToMany(mappedBy = "user")
    private List<Budget> budgets;
}