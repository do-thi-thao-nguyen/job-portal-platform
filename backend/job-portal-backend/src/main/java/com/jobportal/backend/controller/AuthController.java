package com.jobportal.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Object register(@RequestBody Map<String, Object> request) {

        String email = (String) request.get("email");
        String password = (String) request.get("password");
        String companyName = (String) request.get("companyName"); // 👈 chỉ là string

        if (email == null || email.isBlank()) {
            return Map.of("error", "Email is required");
        }

        if (password == null || password.isBlank()) {
            return Map.of("error", "Password is required");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return Map.of("error", "Email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // PHÂN ROLE
        if (companyName != null && !companyName.isBlank()) {
            user.setRole("ROLE_EMPLOYER");
        } else {
            user.setRole("ROLE_USER");
        }

        User savedUser = userRepository.save(user);

        return Map.of(
                "id", savedUser.getId(),
                "email", savedUser.getEmail(),
                "role", savedUser.getRole()
        );
    }

    // ================= LOGIN =================
    @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody User user) {

        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(401)
            .body(Map.of("error","User not found"));
        }

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
             return ResponseEntity.status(401)
                    .body(Map.of("error", "Wrong password"));
        }

        String token = JwtUtil.generateToken(
                existingUser.getEmail(),
                existingUser.getRole()
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", existingUser.getEmail(),
                "role", existingUser.getRole()
        ));
    }

    // ================= TEST =================
    @GetMapping("/test/encode")
    public String encode() {
        return passwordEncoder.encode("123456");
    }
}