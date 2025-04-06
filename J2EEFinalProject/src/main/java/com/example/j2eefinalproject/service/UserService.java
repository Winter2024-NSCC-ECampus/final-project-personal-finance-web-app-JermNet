package com.example.j2eefinalproject.service;

import com.example.j2eefinalproject.model.User;
import com.example.j2eefinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method that uses repository so that only service needs to be called instead of service and repository
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method that uses repository so that only service needs to be called instead of service and repository
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Save user with password decoder
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}