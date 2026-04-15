package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping
    public Company create(@RequestBody Company company) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        company.setOwnerEmail(email);

        return companyRepository.save(company);
    }
}