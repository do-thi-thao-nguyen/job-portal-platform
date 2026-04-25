package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // 🔥 THÊM
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.repository.JobRepository;

@RestController
@RequestMapping("/admin/jobs")
@PreAuthorize("hasRole('ADMIN')") // CHẶN TOÀN BỘ CONTROLLER
public class AdminJobController {

    @Autowired
    private JobRepository jobRepository;

    // GET PENDING
    @GetMapping("/pending")
    public List<Job> getPending() {
        return jobRepository.findByStatus(JobStatus.PENDING);
    }

    // APPROVE
    @PutMapping("/{id}/approve")
    public Job approve(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus(JobStatus.APPROVED);

        return jobRepository.save(job);
    }

    // REJECT
    @PutMapping("/{id}/reject")
    public Job reject(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus(JobStatus.REJECTED);

        return jobRepository.save(job);
    }
}