package com.jobportal.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.CompanyStatus;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/reports") // 🔥 thêm /api cho đồng bộ frontend
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public Map<String, Object> getReport() {

        Map<String, Object> report = new HashMap<>();

        // ===== USER =====
        long totalUsers = userRepository.count();
        long totalEmployers = userRepository.countByRole("ROLE_EMPLOYER");

        // ===== COMPANY =====
        long totalCompanies = companyRepository.count();
        long pendingCompanies = companyRepository.countByStatus(CompanyStatus.PENDING);
        long approvedCompanies = companyRepository.countByStatus(CompanyStatus.APPROVED); // 🔥 thêm

        // ===== JOB =====
        long totalJobs = jobRepository.count();
        long pendingJobs = jobRepository.countByStatus(JobStatus.PENDING);
        long approvedJobs = jobRepository.countByStatus(JobStatus.APPROVED);

        // ===== PUT =====
        report.put("totalUsers", totalUsers);
        report.put("totalEmployers", totalEmployers);

        report.put("totalCompanies", totalCompanies);
        report.put("pendingCompanies", pendingCompanies);
        report.put("approvedCompanies", approvedCompanies);

        report.put("totalJobs", totalJobs);
        report.put("pendingJobs", pendingJobs);
        report.put("approvedJobs", approvedJobs);

        return report;
    }
}