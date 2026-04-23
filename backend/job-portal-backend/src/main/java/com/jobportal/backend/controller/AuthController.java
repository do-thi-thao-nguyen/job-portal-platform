package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Role;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.entity.Company;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // =========================
    // REGISTER (AUTO USER / EMPLOYER)
    // =========================
    @PostMapping("/register")
    public User register(@RequestBody User user,
                        @RequestParam(required = false) String companyName) {

        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("PASS: " + user.getPassword());
        System.out.println("COMPANY: " + companyName);

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email & password required");
        }

        // check trùng email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 🔥 EMPLOYER
        if (companyName != null && !companyName.isBlank()) {

            user.setRole(Role.EMPLOYER);
            userRepository.save(user);

            Company company = new Company();
            company.setName(companyName);
            company.setEmployer(user);

            companyRepository.save(company);

        } else {
            // 🔥 USER
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        return user;
    }
        // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (existingUser.getPassword() == null) {
            throw new RuntimeException("User has no password");
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return JwtUtil.generateToken(
                existingUser.getEmail(),
                existingUser.getRole().name()
        );
    }
}