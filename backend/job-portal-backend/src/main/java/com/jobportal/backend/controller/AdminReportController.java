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
@RequestMapping("/admin/reports")
@PreAuthorize("hasRole('ADMIN')")
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

        // USER
        report.put("totalUsers", userRepository.count());
        report.put("totalEmployers", userRepository.countByRole("EMPLOYER")); // 🔥 FIX

        // COMPANY
        report.put("totalCompanies", companyRepository.count());
        report.put("pendingCompanies", companyRepository.countByStatus(CompanyStatus.PENDING));

        // JOB
        report.put("totalJobs", jobRepository.count());
        report.put("pendingJobs", jobRepository.countByStatus(JobStatus.PENDING));
        report.put("approvedJobs", jobRepository.countByStatus(JobStatus.APPROVED));

        return report;
    }
}