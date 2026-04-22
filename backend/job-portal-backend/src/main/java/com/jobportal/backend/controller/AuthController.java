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
    // REGISTER USER (ỨNG VIÊN)
    // =========================
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("PASS: " + user.getPassword());

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email & password required");
        }

        // 🔥 FIX: không cho trùng email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 🔥 FIX: luôn set role USER
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // =========================
    // REGISTER EMPLOYER + COMPANY
    // =========================
    @PostMapping("/register-employer")
    public String registerEmployer(@RequestBody User user,
                                   @RequestParam String companyName) {

        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email & password required");
        }

        if (companyName == null || companyName.isEmpty()) {
            throw new RuntimeException("Company name required");
        }

        // 🔥 check trùng email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 🔥 set role EMPLOYER
        user.setRole(Role.EMPLOYER);

        userRepository.save(user);

        // 🔥 tạo company
        Company company = new Company();
        company.setName(companyName);
        company.setEmployer(user);

        companyRepository.save(company);

        return "Register employer success";
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