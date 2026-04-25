package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Category;
import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.CategoryRepository;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ================= CREATE =================
    @PostMapping
    public Job createJob(@RequestBody Job job) {

        // ===== DEBUG =====
        System.out.println("Incoming job: " + job);

        // ===== VALIDATE INPUT =====
        if (job.getCompany() == null || job.getCompany().getId() == null) {
            throw new RuntimeException("Company ID is required");
        }

        if (job.getCategory() == null || job.getCategory().getId() == null) {
            throw new RuntimeException("Category ID is required");
        }

        // ===== GET USER =====
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        System.out.println("User email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ===== GET COMPANY =====
        Company company = companyRepository.findById(job.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // ===== FIX AN TOÀN (KHÔNG PHÁ CODE BẠN 1) =====
        if (company.getEmployer() == null) {
            throw new RuntimeException("Company has no owner (user_id is null in DB)");
        }

        if (!company.getEmployer().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this company");
        }

        // ===== GET CATEGORY =====
        Category category = categoryRepository.findById(job.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ===== SET DATA =====
        job.setCompany(company);
        job.setCategory(category);
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    // ================= GET ALL =================
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findByStatus(JobStatus.APPROVED);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        jobRepository.delete(job);

        return "Deleted job with id " + id;
    }
}