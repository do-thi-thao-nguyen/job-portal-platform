package com.jobportal.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.entity.*;
import com.jobportal.backend.repository.*;
import com.jobportal.backend.service.EmailService;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // ================= APPLY JOB =================
    @PostMapping(value = "/{jobId}", consumes = "multipart/form-data")
    public Application apply(
            @PathVariable Long jobId,
            @RequestParam("email") String email,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setStatus(ApplicationStatus.PENDING);

        // upload CV
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            app.setCvUrl(fileName);
        }

        return applicationRepository.save(app);
    }

    // ================= GET BY JOB =================
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {
        return applicationRepository.findByJob_Id(jobId);
    }

    // ================= SEARCH (EMAIL + STATUS) =================
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(
            @PathVariable Long jobId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status
    ) {

        if ((email == null || email.isBlank()) &&
            (status == null || status.isBlank())) {

            return applicationRepository.findByJob_Id(jobId);
        }

        if (status != null && !status.isBlank()) {
            ApplicationStatus st = ApplicationStatus.valueOf(status.toUpperCase());

            if (email != null && !email.isBlank()) {
                return applicationRepository
                        .findByJob_IdAndUser_EmailContainingIgnoreCaseAndStatus(jobId, email, st);
            }

            return applicationRepository.findByJob_IdAndStatus(jobId, st);
        }

        return applicationRepository
                .findByJob_IdAndUser_EmailContainingIgnoreCase(jobId, email);
    }

    // ================= GET MY APPLICATION =================
    @GetMapping("/my")
    public List<Application> getMyApplications(@RequestParam String email) {
        return applicationRepository.findByUser_Email(email);
    }

    // ================= UPDATE CV =================
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCvUrl(updated.getCvUrl());

        return applicationRepository.save(app);
    }

    // ================= CONTACT + SEND EMAIL =================
    @PutMapping("/{id}/contact")
    public Application contact(@PathVariable Long id,
                              @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.CONTACTED);
        app.setMessage(updated.getMessage());

        String html = """
        <h3>Dear %s,</h3>
        <p>Your application for <b>%s</b> has been reviewed.</p>
        <p>Company: <b>%s</b></p>
        """.formatted(
                app.getUser().getEmail(),
                app.getJob().getTitle(),
                app.getJob().getCompany().getName()
        );

        emailService.sendEmail(
                app.getUser().getEmail(),
                "Application Update",
                html
        );

        return applicationRepository.save(app);
    }

    // ================= UPDATE STATUS =================
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                   @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));

        return applicationRepository.save(app);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}