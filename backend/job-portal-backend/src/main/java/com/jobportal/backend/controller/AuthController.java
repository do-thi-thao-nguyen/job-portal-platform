package com.jobportal.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    @PostMapping("/register")
    public Object register(@RequestBody User user) {

        // check email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return Map.of("error", "Email is required");
        }

        // check password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Map.of("error", "Password is required");
        }

        // check email tồn tại
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Map.of("error", "Email already exists");
        }

        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set role
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return Map.of(
                "id", savedUser.getId(),
                "email", savedUser.getEmail(),
                "role", savedUser.getRole()
        );
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public Object login(@RequestBody User user) {

        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElse(null);

        if (existingUser == null) {
            return Map.of("error", "User not found");
        }

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return Map.of("error", "Wrong password");
        }

        //JWT TOKEN
        String token = JwtUtil.generateToken(existingUser.getEmail());

        return Map.of(
                "token", token,
                "email", existingUser.getEmail(),
                "role", existingUser.getRole()
        );
    }
    @GetMapping("/test/encode")
    public String encode() {
        return passwordEncoder.encode("123456");
}
}