package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import com.jobportal.backend.entity.Notification;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/jobs")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminJobController {

    private final JobRepository jobRepository;
    private final NotificationRepository notificationRepository;

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

        // 🔥 SEND NOTIFICATION
        Notification n = new Notification();
        n.setTitle("Job Approved");
        n.setContent("Job \"" + job.getTitle() + "\" đã được duyệt");
        n.setUser(job.getCompany().getEmployer());

        notificationRepository.save(n);

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

        // 🔥 SEND NOTIFICATION
        Notification n = new Notification();
        n.setTitle("Job Rejected");
        n.setContent("Job \"" + job.getTitle() + "\" đã bị từ chối");
        n.setUser(job.getCompany().getEmployer());

        notificationRepository.save(n);

        return "Job rejected";
    }
}