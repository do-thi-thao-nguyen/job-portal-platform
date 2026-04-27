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

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyRepository
                .findByEmployer_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("You must create company first"));

        Category category = categoryRepository.findById(job.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        job.setCompany(company);
        job.setCategory(category);
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
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

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyRepository.findById(updatedJob.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!company.getEmployer().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this company");
        }

        Category category = categoryRepository.findById(updatedJob.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ UPDATE FIELD (FIX CHUẨN)
        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setSalaryMin(updatedJob.getSalaryMin());
        job.setSalaryMax(updatedJob.getSalaryMax());
        job.setLocation(updatedJob.getLocation());
        job.setJobType(updatedJob.getJobType());

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

    // 🔥 GET MY JOBS
    @GetMapping("/my")
    public List<Job> getMyJobs() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobRepository.findByCompanyEmployer(user);
    }
}