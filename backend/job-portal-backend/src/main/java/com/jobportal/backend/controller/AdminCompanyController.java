package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.service.AdminCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/companies")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminCompanyController {

    @Autowired
    private AdminCompanyService adminCompanyService;

    // 1. Lấy danh sách pending
    @GetMapping("/pending")
    public List<Company> getPendingCompanies() {
        return adminCompanyService.getPendingCompanies();
    }

    // 2. Approve
    @PutMapping("/{id}/approve")
    public Company approveCompany(@PathVariable Long id) {
        return adminCompanyService.approveCompany(id);
    }

    // 3. Reject
    @PutMapping("/{id}/reject")
    public Company rejectCompany(@PathVariable Long id) {
        return adminCompanyService.rejectCompany(id);
    }
}