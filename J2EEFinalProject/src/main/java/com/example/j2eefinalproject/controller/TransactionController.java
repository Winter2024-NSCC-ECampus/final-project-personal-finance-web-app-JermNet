package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.Transaction;
import com.example.j2eefinalproject.model.TransactionType;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.TransactionService;
import com.example.j2eefinalproject.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    // Save the thing using the service and principal so the item is only attached to the currently logged in user
    @PostMapping("/save")
    public String save(@ModelAttribute Transaction transaction, Principal principal) {
        transactionService.addTransaction(transaction, principal);
        return "redirect:/transactions";
    }

    // Get all of the transactions, but categorise them and have an equal amount (that's what the .add(0.0)s are for) so they can easily be graphed later
    @GetMapping()
    public String showPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        boolean userExists = user != null;
        model.addAttribute("userExists", userExists);

        List<Transaction> transactions = transactionService.findByUser(user);
        List<Double> incomeAmounts = new ArrayList<>();
        List<Double> expenseAmounts = new ArrayList<>();
        List<String> transactionDates = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionDates.add(transaction.getDate().toString());
            if (transaction.getType() == TransactionType.INCOME) {
                incomeAmounts.add(transaction.getAmount());
                expenseAmounts.add(0.0);
            } else if (transaction.getType() == TransactionType.EXPENSE) {
                expenseAmounts.add(transaction.getAmount());
                incomeAmounts.add(0.0);
            }
        }
        model.addAttribute("transactions", transactions);

        // This just puts these in JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            model.addAttribute("incomeAmounts", objectMapper.writeValueAsString(incomeAmounts));
            model.addAttribute("expenseAmounts", objectMapper.writeValueAsString(expenseAmounts));
            model.addAttribute("transactionDates", objectMapper.writeValueAsString(transactionDates));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "transactions";
    }
}