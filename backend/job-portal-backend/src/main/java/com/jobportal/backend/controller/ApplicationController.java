package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    // APPLY JOB
    @PostMapping("/{jobId}")
    public Application apply(@PathVariable Long jobId, @RequestBody Application app) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        app.setJob(job);
        app.setStatus("PENDING"); // mặc định

        return applicationRepository.save(app);
    }

    // XEM ỨNG VIÊN THEO JOB
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // SEARCH / FILTER CV
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(@PathVariable Long jobId,
                                    @RequestParam String email) {

        return applicationRepository.findByJobIdAndEmailContaining(jobId, email);
    }

    // UPDATE APPLICATION
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCvUrl(updated.getCvUrl());
        app.setEmail(updated.getEmail());

        return applicationRepository.save(app);
    }

    // CONTACT ỨNG VIÊN (ĐÚNG CHUẨN)
    @PutMapping("/{id}/contact")
    public Application contact(@PathVariable Long id,
                               @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("CONTACTED");
        app.setMessage(updated.getMessage());

        return applicationRepository.save(app);
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                   @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);

        return applicationRepository.save(app);
    }

    // DELETE APPLICATION
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}