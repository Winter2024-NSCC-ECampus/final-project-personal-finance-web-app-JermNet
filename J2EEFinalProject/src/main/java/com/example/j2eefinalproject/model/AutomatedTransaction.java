package com.example.j2eefinalproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// User lombok to make things cleaner
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutomatedTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private Category category;
    private double amount;
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private RecurrenceFrequency frequency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}