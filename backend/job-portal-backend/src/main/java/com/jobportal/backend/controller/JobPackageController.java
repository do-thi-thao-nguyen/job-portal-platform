package com.jobportal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.JobPackage;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.JobPackageRepository;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class JobPackageController {

    @Autowired
    private JobPackageRepository packageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // lấy danh sách gói
    @GetMapping("/packages")
    public List<JobPackage> getPackages() {
        return packageRepository.findAll();
    }

    // mua gói
    @PostMapping("/buy/{companyId}/{packageId}")
    public Object buyPackage(@PathVariable Long companyId,
                            @PathVariable Long packageId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        JobPackage pack = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // gán gói
        company.setCurrentPackage(pack);

        // reset số lượt đăng
        company.setRemainingPosts(pack.getPostLimit());

        return companyRepository.save(company);
    }
}