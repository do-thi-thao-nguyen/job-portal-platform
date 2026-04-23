package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Role;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.jobportal.backend.repository.CompanyRepository;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // 🔥 1. GET ALL EMPLOYERS
    @GetMapping("/employers")
    public List<User> getEmployers() {
        return userRepository.findByRole(Role.EMPLOYER);
    }

    // 🔥 2. GET BY ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔥 3. UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(updatedUser.getEmail());

        // 🔥 KHÔNG cho đổi role lung tung
        // user.setRole(updatedUser.getRole()); ❌

        return userRepository.save(user);
    }

    // 🔥 4. DELETE
   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        // 1. Check user tồn tại
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "message", "User not found",
                            "status", 404
                    )
            );
        }

        // 2. Check có company không
        boolean hasCompany = companyRepository.existsByEmployer_Id(id);

        if (hasCompany) {
            return ResponseEntity.status(400).body(
                    Map.of(
                            "message", "Cannot delete user because they own a company",
                            "status", 400
                    )
            );
        }

        // 3. Xoá user
        userRepository.deleteById(id);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User deleted successfully",
                        "status", 200
                )
        );
    }
}