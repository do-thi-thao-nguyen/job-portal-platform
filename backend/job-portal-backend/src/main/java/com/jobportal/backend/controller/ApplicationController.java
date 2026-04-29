package com.jobportal.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.ApplicationStatus;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    //  APPLY JOB
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

    if (file != null && !file.isEmpty()) {
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

    Path uploadDir = Paths.get("uploads");

    if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
    }

    // tạo path file
    Path filePath = uploadDir.resolve(fileName);

    // ghi file
    Files.write(filePath, file.getBytes());

    app.setCvUrl(fileName);
    }
    return applicationRepository.save(app);
    }

    // XEM ỨNG VIÊN THEO JOB (FIX CHUẨN)
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {

        System.out.println("GET APPLICATIONS FOR JOB: " + jobId);

        return applicationRepository.findByJob_Id(jobId);
    }

    //  SEARCH / FILTER (FIX CHUẨN)
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(@PathVariable Long jobId,
                                    @RequestParam String email) {

    return applicationRepository.findByJob_IdAndUser_EmailContaining(jobId, email);
    }

    // NEW API CHO USER
    @GetMapping("/my")
    public List<Application> getMyApplications(@RequestParam String email) {

        System.out.println("GET MY APPLICATIONS: " + email); // 🔍 debug

        return applicationRepository.findByUser_Email(email);
    }
    //  LẤY APPLICATION THEO USER
    @GetMapping("/user")
    public List<Application> getByUser(@RequestParam String email) {
        return applicationRepository.findByUser_Email(email);
    }

    //  UPDATE APPLICATION
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCvUrl(updated.getCvUrl());

        return applicationRepository.save(app);
    }

    //  CONTACT ỨNG VIÊN
    @PutMapping("/{id}/contact")
    public Application contact(@PathVariable Long id,
                               @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.CONTACTED);
        app.setMessage(updated.getMessage());

        return applicationRepository.save(app);
    }

    //  UPDATE STATUS
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                   @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));

        return applicationRepository.save(app);
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}