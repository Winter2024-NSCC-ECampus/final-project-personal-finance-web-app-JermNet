package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.Budget;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.BudgetService;
import com.example.j2eefinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    // Save the thing using the service and principal so the item is only attached to the currently logged in user
    @PostMapping("/save")
    public String save(@ModelAttribute("budget") Budget budget, Principal principal) {
        budgetService.addBudget(budget, principal);
        return "redirect:/budget";
    }

    // Get things using principal so only the logged in user's items show up
    @GetMapping
    public String showPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        boolean userExists = user != null;
        List<Budget> budgets = budgetService.findByUser(user);
        model.addAttribute("budgets", budgets);
        model.addAttribute("userExists", userExists);
        return "budget";
    }

    // Delete item for by id
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        budgetService.deleteBudgetByIdAndUser(id, principal.getName());
        return "redirect:/budget";
    }

    // Increase the amount spent for the budget
    @PostMapping("/increaseSpent/{id}")
    public String increaseSpent(@PathVariable Long id, @RequestParam double amountSpent) {
        Budget budget = budgetService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid budget"));
        budget.setAmountSpent(budget.getAmountSpent() + amountSpent);
        budgetService.save(budget);
        return "redirect:/budget";
    }
}