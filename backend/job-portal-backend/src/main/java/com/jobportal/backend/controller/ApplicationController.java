package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.dto.ApplyJobRequest;
import com.jobportal.backend.entity.Application;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ApplicationService applicationService;

    // ==============================
    // APPLY JOB (NEW - chuẩn)
    // ==============================
    @PostMapping
    public String applyJob(
            @RequestParam Long userId,
            @RequestBody ApplyJobRequest request
    ) {
        applicationService.applyJob(userId, request);
        return "Apply thành công";
    }

    // ==============================
    // XEM LIST APPLY (candidate)
    // ==============================
    @GetMapping("/my")
    public List<Application> getMyApplications(@RequestParam Long userId) {
        return applicationService.getMyApplications(userId);
    }

    // ==============================
    // EMPLOYER - XEM ỨNG VIÊN
    // ==============================
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // ==============================
    // SEARCH CV
    // ==============================
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(@PathVariable Long jobId,
                                   @RequestParam String email) {

        return applicationRepository.findByJobIdAndEmailContaining(jobId, email);
    }

    // ==============================
    // CONTACT ỨNG VIÊN
    // ==============================
    @PutMapping("/{id}/contact")
    public Application contact(@PathVariable Long id,
                               @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("CONTACTED");
        app.setMessage(updated.getMessage());

        return applicationRepository.save(app);
    }

    // ==============================
    // UPDATE STATUS
    // ==============================
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                   @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);

        return applicationRepository.save(app);
    }

    // ==============================
    // DELETE
    // ==============================
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}