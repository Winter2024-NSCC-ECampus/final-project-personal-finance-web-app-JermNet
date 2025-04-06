package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.AutomatedTransaction;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.AutomatedTransactionService;
import com.example.j2eefinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/automatedtransactions")
public class AutomatedTransactionController {
    @Autowired
    private AutomatedTransactionService automatedTransactionService;

    @Autowired
    private UserService userService;

    // Save the thing using the service and principal so the item is only attached to the currently logged in user
    @PostMapping("/save")
    public String save(@ModelAttribute AutomatedTransaction automatedTransaction, Principal principal) {
        automatedTransactionService.addAutomatedTransaction(automatedTransaction, principal);
        return "redirect:/automatedtransactions";
    }

    // Get things using principal so only the logged in user's items show up
    @GetMapping
    public String showPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        boolean userExists = user != null;
        List<AutomatedTransaction> automatedTransactions = automatedTransactionService.findByUser(user);
        model.addAttribute("automatedTransactions", automatedTransactions);
        model.addAttribute("userExists", userExists);
        return "automatedtransactions";
    }

    // Delete item for by id
    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Optional<AutomatedTransaction> optional = automatedTransactionService.findById(id);
        if (optional.isPresent() && optional.get().getUser().equals(user)) {
            automatedTransactionService.deleteById(id);
        }
        return "redirect:/automatedtransactions";
    }
}
