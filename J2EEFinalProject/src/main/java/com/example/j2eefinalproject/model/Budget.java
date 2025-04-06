package com.example.j2eefinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// User lombok to make things cleaner
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Category category;
    private double amount;
    private double amountSpent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public double getRemainingAmount() {
        return amount - amountSpent;
    }
}