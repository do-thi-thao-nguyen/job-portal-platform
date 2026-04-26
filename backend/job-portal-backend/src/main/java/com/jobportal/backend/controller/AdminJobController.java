package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/jobs")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminJobController {

    private final JobRepository jobRepository;

    // ==============================
    // GET JOB PENDING
    // ==============================
    @GetMapping("/pending")
    public List<Job> getPending() {
        return jobRepository.findByStatus(JobStatus.PENDING);
    }

    // ==============================
    // APPROVE JOB
    // ==============================
    @PutMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus(JobStatus.APPROVED);
        jobRepository.save(job);

        return "Job approved";
    }

    // ==============================
    // REJECT JOB
    // ==============================
    @PutMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus(JobStatus.REJECTED);
        jobRepository.save(job);

        return "Job rejected";
    }
}