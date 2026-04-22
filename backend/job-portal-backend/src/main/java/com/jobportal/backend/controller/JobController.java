package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.entity.User;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

        // 2. Check company tồn tại
        Company company = companyRepository.findById(job.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 3. 🔥 CHECK QUYỀN (QUAN TRỌNG NHẤT)
        if (!company.getEmployer().getId().equals(user.getId())) {
            throw new RuntimeException("You are not owner of this company");
        }

        // 4. Set lại data chuẩn
        job.setCompany(company);
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    // 🔥 GET ALL (chỉ nên lấy APPROVED)
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

    // 🔥 UPDATE
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setCompany(updatedJob.getCompany());
        job.setSalary(updatedJob.getSalary());

        // 🔥 UPDATE → quay lại PENDING (để admin duyệt lại)
        job.setStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    // 🔥 DELETE
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        jobRepository.delete(job);

        return "Deleted job with id " + id;
    }
}