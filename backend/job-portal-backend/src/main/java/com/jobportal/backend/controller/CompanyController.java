package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔥 CREATE COMPANY (USER)
    @PostMapping
    public Company create(@RequestBody Company company) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        company.setEmployer(user);
        company.setStatus(CompanyStatus.PENDING);

        return companyRepository.save(company);
    }
}