package com.example.j2eefinalproject.controller;

import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    // Get index page
    @GetMapping
    public String showPage(Model model, Principal principal) {
        boolean userExists = principal != null;
        model.addAttribute("userExists", userExists);
        return "index";
    }
}