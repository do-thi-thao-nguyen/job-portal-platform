package com.jobportal.backend.controller;

import com.jobportal.backend.entity.ApplyJob;
import com.jobportal.backend.repository.ApplyJobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyJobController {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // 🟢 APPLY JOB
    @PostMapping
    public ApplyJob apply(@RequestParam Long jobId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        ApplyJob applyJob = new ApplyJob();
        applyJob.setUsername(username);
        applyJob.setJobId(jobId);

        return applyJobRepository.save(applyJob);
    }

    // 🔍 XEM JOB ĐÃ APPLY
    @GetMapping("/my")
    public List<ApplyJob> myJobs() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return applyJobRepository.findByUsername(username);
    }
}