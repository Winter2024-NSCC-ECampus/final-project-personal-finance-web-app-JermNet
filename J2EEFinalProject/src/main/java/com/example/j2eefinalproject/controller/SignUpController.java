package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    @Autowired
    private UserService userService;

    // Show signup page
    @GetMapping("/signup")
    public String showPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Make a new page only if the user doesn't exist
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username is already taken");
            return "signup";
        }
        userService.save(user);
        return "redirect:/login";
    }
}