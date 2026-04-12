package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.ApplyJob;
import com.jobportal.backend.repository.ApplyJobRepository;

@RestController
@RequestMapping("/apply")
public class ApplyJobController {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // 🟢 APPLY JOB
    @PostMapping
    public ApplyJob apply(@RequestParam Long jobId) {

        String username = getCurrentUsername();

        // CHỐNG APPLY TRÙNG
        if (applyJobRepository.existsByUsernameAndJobId(username, jobId)) {
            throw new RuntimeException("You already applied this job");
        }

        ApplyJob applyJob = new ApplyJob();
        applyJob.setUsername(username);
        applyJob.setJobId(jobId);

        return applyJobRepository.save(applyJob);
    }

    // 🔍 XEM JOB ĐÃ APPLY
    @GetMapping("/my")
    public List<ApplyJob> myJobs() {

        String username = getCurrentUsername();

        return applyJobRepository.findByUsername(username);
    }

    // 🔐 LẤY USERNAME ĐANG LOGIN
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            throw new RuntimeException("User not authenticated");
        }

        return authentication.getName();
    }
}