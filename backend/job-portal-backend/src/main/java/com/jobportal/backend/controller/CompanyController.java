package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    // =============================
    // CREATE COMPANY (1 USER = 1 COMPANY)
    // =============================
    @PostMapping
    public Company create(@RequestBody Company company) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // CHẶN TẠO NHIỀU COMPANY
        List<Company> existing = companyRepository.findByEmployer_Id(user.getId());

        if (!existing.isEmpty()) {
            throw new RuntimeException("Bạn đã có công ty rồi!");
        }

        company.setEmployer(user);
        company.setStatus(CompanyStatus.PENDING);

        return companyRepository.save(company);
    }

    // =============================
    // GET MY COMPANY
    // =============================
    @GetMapping("/my")
    public Company getMyCompany() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Company> companies = companyRepository.findByEmployer_Id(user.getId());

        if (companies.isEmpty()) {
            throw new RuntimeException("Bạn chưa có công ty");
        }

        if (companies.size() > 1) {
            System.out.println("⚠️ USER CÓ NHIỀU COMPANY");
        }

        return companies.get(0);
    }

    // =============================
    // UPDATE MY COMPANY
    // =============================
    @PutMapping("/my")
    public Company updateMyCompany(@RequestBody Company updatedCompany) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Company> companies = companyRepository.findByEmployer_Id(user.getId());

        if (companies.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        Company company = companies.get(0);

        company.setName(updatedCompany.getName());
        company.setDescription(updatedCompany.getDescription());
        company.setAddress(updatedCompany.getAddress());

        return companyRepository.save(company);
    }

    // =============================
    // DELETE COMPANY (optional)
    // =============================
    @DeleteMapping("/my")
    public String deleteMyCompany() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Company> companies = companyRepository.findByEmployer_Id(user.getId());

        if (companies.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        companyRepository.delete(companies.get(0));

        return "Deleted";
    }
}