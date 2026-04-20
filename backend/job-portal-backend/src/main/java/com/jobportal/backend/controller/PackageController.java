package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyPackage;
import com.jobportal.backend.repository.CompanyPackageRepository;
import com.jobportal.backend.repository.CompanyRepository;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private CompanyPackageRepository companyPackageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // =============================
    // CREATE PACKAGE (Admin tạo gói)
    // =============================
    @PostMapping
    public CompanyPackage create(@RequestBody CompanyPackage pkg) {
        pkg.setStatus("AVAILABLE");
        return companyPackageRepository.save(pkg);
    }

    // =============================
    // GET ALL PACKAGES
    // =============================
    @GetMapping
    public List<CompanyPackage> getAll() {
        return companyPackageRepository.findAll();
    }

    // =============================
    // GET PACKAGES BY COMPANY
    // =============================
    @GetMapping("/company/{companyId}")
    public List<CompanyPackage> getByCompany(@PathVariable Long companyId) {
        return companyPackageRepository.findByCompanyId(companyId);
    }

    // =============================
    // BUY PACKAGE
    // =============================
    @PostMapping("/buy")
    public CompanyPackage buy(@RequestBody CompanyPackage pkg) {

        Long companyId = pkg.getCompany().getId();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        pkg.setCompany(company);
        pkg.setStatus("ACTIVE");

        return companyPackageRepository.save(pkg);
    }
}