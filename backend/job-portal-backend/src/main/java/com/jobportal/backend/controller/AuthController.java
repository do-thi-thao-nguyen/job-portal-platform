package com.jobportal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

   @PostMapping("/login")
public String login(@RequestBody User user) {

    User existingUser = userRepository.findByEmail(user.getEmail());

    if (existingUser == null) {
        throw new RuntimeException("User not found");
    }

    if (!existingUser.getPassword().equals(user.getPassword())) {
        throw new RuntimeException("Wrong password");
    }

    // 🔥 TRẢ TOKEN
    return JwtUtil.generateToken(existingUser.getEmail());
}
}