package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.dto.ApplyJobRequest;
import com.jobportal.backend.entity.Application;
import com.jobportal.backend.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // ==============================
    // APPLY JOB
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
        return applicationService.getByJob(jobId);
    }

    // ==============================
    // SEARCH CV
    // ==============================
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(
            @PathVariable Long jobId,
            @RequestParam String email
    ) {
        return applicationService.search(jobId, email);
    }

    // ==============================
    // CONTACT ỨNG VIÊN
    // ==============================
    @PutMapping("/{id}/contact")
    public String contact(
            @PathVariable Long id,
            @RequestParam String message
    ) {
        applicationService.contact(id, message);
        return "Contact thành công";
    }

    // ==============================
    // UPDATE STATUS (APPROVE / REJECT)
    // ==============================
    @PutMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        applicationService.updateStatus(id, status);
        return "Update status thành công";
    }

    // ==============================
    // DELETE
    // ==============================
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.delete(id);
        return "Deleted successfully";
    }
}