package com.jobportal.backend.controller;

import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.entity.Role;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.entity.CompanyStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
        report.put("totalEmployers", userRepository.countByRole(Role.EMPLOYER));

        // COMPANY
        report.put("totalCompanies", companyRepository.count());
        report.put("pendingCompanies", companyRepository.countByStatus(CompanyStatus.PENDING));

        // JOB
        report.put("totalJobs", jobRepository.count());
        report.put("pendingJobs", jobRepository.countByStatus(JobStatus.PENDING));
        report.put("approvedJobs", jobRepository.countByStatus(JobStatus.APPROVED));

        return report;
    }
    @GetMapping("/today")
    public Map<String, Object> reportToday() {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        long usersToday = userRepository.countByCreatedAtBetween(start, end);
        long jobsToday = jobRepository.countByCreatedAtBetween(start, end);
        long companiesToday = companyRepository.countByCreatedAtBetween(start, end);

        return Map.of(
                "usersToday", usersToday,
                "jobsToday", jobsToday,
                "companiesToday", companiesToday
        );
    }
        
}