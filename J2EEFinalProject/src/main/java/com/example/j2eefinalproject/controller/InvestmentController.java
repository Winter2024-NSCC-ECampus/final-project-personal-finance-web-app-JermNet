package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.Investment;
import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.InvestmentService;
import com.example.j2eefinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private UserService userService;

    // Save the thing using the service and principal so the item is only attached to the currently logged in user
    @PostMapping("/save")
    public String save(@ModelAttribute Investment investment, Principal principal) {
        investmentService.addInvestment(investment, principal);
        return "redirect:/investments";
    }

    // Get things using principal so only the logged in user's items show up
    @GetMapping
    public String showPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        boolean userExists = user != null;
        List<Investment> investments = investmentService.findByUser(user);
        model.addAttribute("investments", investments);
        model.addAttribute("userExists", userExists);
        return "investments";
    }
}