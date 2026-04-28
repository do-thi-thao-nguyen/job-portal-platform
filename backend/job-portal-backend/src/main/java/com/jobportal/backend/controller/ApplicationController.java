package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.ApplicationStatus;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.service.EmailService;


@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JobRepository jobRepository;

    // 🔥 APPLY JOB
    @PostMapping("/{jobId}")
    public Application apply(
            @PathVariable Long jobId,
            @RequestBody Application req
    ) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getStatus() == null || !job.getStatus().name().equals("APPROVED")) {
            throw new RuntimeException("Job not approved");
        }

        Application app = new Application();
        app.setJob(job);
        app.setEmail(req.getEmail());
        app.setCvUrl(req.getCvUrl());
        app.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(app);
    }

    // 🔥 XEM ỨNG VIÊN THEO JOB (FIX CHUẨN)
    @GetMapping("/job/{jobId}")
    public List<Application> getByJob(@PathVariable Long jobId) {

        System.out.println("GET APPLICATIONS FOR JOB: " + jobId);

        return applicationRepository.findByJob_Id(jobId);
    }

    // 🔥 SEARCH / FILTER (FIX CHUẨN)
    @GetMapping("/job/{jobId}/search")
    public List<Application> search(
            @PathVariable Long jobId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status
    ) {

        // 👉 không nhập gì → trả hết
        if ((email == null || email.isBlank()) && (status == null || status.isBlank())) {
            return applicationRepository.findByJob_Id(jobId);
        }

        // 👉 filter status
        if (status != null && !status.isBlank()) {
            ApplicationStatus st = ApplicationStatus.valueOf(status.toUpperCase());

            if (email != null && !email.isBlank()) {
                return applicationRepository
                        .findByJob_IdAndEmailContainingIgnoreCaseAndStatus(jobId, email, st);
            }

            return applicationRepository.findByJob_IdAndStatus(jobId, st);
        }

        // 👉 chỉ search email
        return applicationRepository
                .findByJob_IdAndEmailContainingIgnoreCase(jobId, email);
    }

    // 🔥 UPDATE APPLICATION
    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCvUrl(updated.getCvUrl());
        app.setEmail(updated.getEmail());

        return applicationRepository.save(app);
    }

    // 🔥 CONTACT ỨNG VIÊN
    @PutMapping("/{id}/contact")
    public Application contact(@PathVariable Long id,
                            @RequestBody Application updated) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.CONTACTED);
        app.setMessage(updated.getMessage());

        // 🔥 HTML TEMPLATE (KHÔNG + chuỗi nữa)
        String html = """
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="UTF-8">
    </head>
    <body style="margin:0;padding:0;font-family:Arial;background:#f4f6f8;">

    <div style="max-width:600px;margin:auto;background:white;border-radius:10px;overflow:hidden;">

        <!-- HEADER -->
        <div style="background:linear-gradient(90deg,#667eea,#764ba2);padding:20px;color:white;text-align:center;">
        <h2>Job Portal</h2>
        <p style="margin:0;">Your Career Partner</p>
        </div>

        <!-- BODY -->
        <div style="padding:30px;">
        
        <h3>Dear %s,</h3>

        <p>
            We are pleased to inform you that your application for the position 
            <b>%s</b>
            at 
            <b>%s</b>
            has been 
            <b style="color:green;">reviewed successfully</b>.
        </p>

        <p>
            The employer is interested in your profile and would like to proceed further.
        </p>

        <div style="text-align:center;margin:30px 0;">
            <a href="http://localhost:3000"
            style="background:#667eea;color:white;padding:12px 25px;
                    text-decoration:none;border-radius:5px;font-weight:bold;">
            View Application
            </a>
        </div>

        <p>If you have any questions, feel free to contact us.</p>

        <br/>

        <p>Best regards,</p>
        <b>%s</b>
        </div>

    </div>

    </body>
    </html>
    """.formatted(
                app.getEmail(),                       // %s 1
                app.getJob().getTitle(),             // %s 2
                app.getJob().getCompany().getName(), // %s 3
                app.getJob().getCompany().getName()  // %s 4
        );

        // 🔥 SEND MAIL
        emailService.sendEmail(
                app.getEmail(),
                "🎉 Job Application Result",
                html
        );

        return applicationRepository.save(app);
    }
    // 🔥 DELETE
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);

        return "Deleted successfully";
    }
}