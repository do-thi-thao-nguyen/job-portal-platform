package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.entity.Category;

import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.repository.CategoryRepository;

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

    // 🔥 CREATE (EMPLOYER)
    @PostMapping
    public Job createJob(@RequestBody Job job) {

        // 1. Lấy user từ token
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check company
        Company company = companyRepository.findById(job.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 3. Check quyền
        if (!company.getEmployer().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this company");
        }

        // 4. Check category
        Category category = categoryRepository.findById(job.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 5. Set data
        job.setCompany(company);
        job.setCategory(category);
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    // 🔥 GET ALL (chỉ lấy APPROVED)
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findByStatus(JobStatus.APPROVED);
    }

    // 🔥 GET BY ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    // 🔥 UPDATE (EMPLOYER)
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        // 1. Lấy job
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 2. Lấy user
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Check company
        Company company = companyRepository.findById(updatedJob.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 4. Check quyền
        if (!company.getEmployer().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this company");
        }

        // 5. Check category
        Category category = categoryRepository.findById(updatedJob.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 6. Update field
        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setSalary(updatedJob.getSalary());
        job.setCompany(company);
        job.setCategory(category);

        // 🔥 reset trạng thái
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    // 🔥 DELETE (EMPLOYER)
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        jobRepository.delete(job);

        return "Deleted job with id " + id;
    }
}