package com.jobportal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    // ✅ APPLY JOB
    @PostMapping("/{jobId}")
    public Application apply(@PathVariable Long jobId, @RequestBody Application app) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        app.setJob(job);

        return applicationRepository.save(app);
    }

    // ✅ XEM ỨNG VIÊN THEO JOB
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // ✅ UPDATE APPLICATION
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCvUrl(updated.getCvUrl());
        app.setEmail(updated.getEmail());

        return applicationRepository.save(app);
    }

    // ✅ DELETE APPLICATION
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}