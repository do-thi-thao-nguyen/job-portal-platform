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
    @GetMapping("/my")
    public Company getMyCompany() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return companyRepository
                .findByEmployer_Id(user.getId())
                .orElse(null);
    }
    @PutMapping("/my")
    public Company updateMyCompany(@RequestBody Company updatedCompany) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyRepository
                .findByEmployer_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // update fields
        company.setName(updatedCompany.getName());
        company.setDescription(updatedCompany.getDescription());
        company.setAddress(updatedCompany.getAddress());

        return companyRepository.save(company);
    }
}