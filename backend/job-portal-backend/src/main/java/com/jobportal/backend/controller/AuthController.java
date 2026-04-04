package com.jobportal.backend.controller;

import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return JwtUtil.generateToken(existingUser.getUsername());
    }
}