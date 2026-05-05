package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.SavedJob;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.SavedJobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/saved-jobs")
public class SavedJobController {

    @Autowired
    private SavedJobRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobRepository jobRepo;

    // ================= SAVE =================
    @PostMapping("/{jobId}")
    public String save(@PathVariable Long jobId, @RequestParam String email) {

        // FIX: tránh email null
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email is required");
        }

        // chống lưu trùng
        if (repo.existsByUser_EmailAndJob_Id(email, jobId)) {
            return "Already saved";
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        SavedJob s = new SavedJob();
        s.setUser(user);
        s.setJob(job);

        repo.save(s);

        return "Saved";
    }

    // ================= UNSAVE =================
    @DeleteMapping("/{jobId}")
    @Transactional
    public String unsave(@PathVariable Long jobId, @RequestParam String email) {

        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email is required");
        }

        repo.deleteByUser_EmailAndJob_Id(email, jobId);

        return "Removed";
    }

    // ================= GET =================
    @GetMapping
    public List<SavedJob> get(@RequestParam String email) {

        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email is required");
        }

        return repo.findByUser_Email(email);
    }
}