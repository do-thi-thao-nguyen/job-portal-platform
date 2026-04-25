package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.ApplyJob;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.ApplyJobRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/apply")
public class ApplyJobController {

    private final ApplyJobRepository applyRepo;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;

    public ApplyJobController(ApplyJobRepository a, UserRepository u, JobRepository j) {
        this.applyRepo = a;
        this.userRepo = u;
        this.jobRepo = j;
    }

    // APPLY JOB
    @PostMapping("/{jobId}")
    public ApplyJob apply(@PathVariable Long jobId, Authentication auth) {

        User user = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (applyRepo.existsByUserIdAndJobId(user.getId(), jobId)) {
            throw new RuntimeException("Already applied");
        }

        ApplyJob apply = new ApplyJob();
        apply.setUser(user);
        apply.setJob(job);

        return applyRepo.save(apply);
    }

    // XEM JOB ĐÃ APPLY
    @GetMapping("/my")
    public List<ApplyJob> myJobs(Authentication auth) {

        User user = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applyRepo.findByUserId(user.getId());
    }
}