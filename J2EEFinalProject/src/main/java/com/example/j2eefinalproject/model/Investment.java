package com.example.j2eefinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// User lombok to make things cleaner
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private InvestmentCategory category;

    private double originalAmount;
    private double currentValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}