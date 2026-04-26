package com.jobportal.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jobportal.backend.dto.ApplyJobRequest;
import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.ApplicationStatus;
import com.jobportal.backend.entity.CV;
import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.ApplicationRepository;
import com.jobportal.backend.repository.CVRepository;
import com.jobportal.backend.repository.JobRepository;
import com.jobportal.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final CVRepository cvRepository;
    private final UserRepository userRepository;

    // ==============================
    // APPLY JOB
    // ==============================
    public void applyJob(Long userId, ApplyJobRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        CV cv = cvRepository.findById(request.getCvId())
                .orElseThrow(() -> new RuntimeException("CV not found"));

        if (!cv.getUser().getId().equals(userId)) {
            throw new RuntimeException("CV không thuộc user");
        }

        if (applicationRepository.existsByUserIdAndJobId(userId, request.getJobId())) {
            throw new RuntimeException("Bạn đã apply job này rồi");
        }

        Application app = new Application();

        app.setEmail(user.getEmail());
        app.setUserId(user.getId());
        app.setJob(job);
        app.setCv(cv);
        app.setCvUrl(cv.getFileUrl());
        app.setMessage(request.getNote());

        app.setStatus(ApplicationStatus.PENDING);
        app.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(app);
    }

    // ==============================
    // GET MY APPLICATIONS
    // ==============================
    public List<Application> getMyApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    // ==============================
    // EMPLOYER - SEARCH
    // ==============================
    public List<Application> search(Long jobId, String email) {
        return applicationRepository.findByJobIdAndEmailContaining(jobId, email);
    }

    // ==============================
    // EMPLOYER - GET BY JOB
    // ==============================
    public List<Application> getByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // ==============================
    // UPDATE STATUS (QUAN TRỌNG)
    // ==============================
    public void updateStatus(Long id, String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        try {
            ApplicationStatus newStatus = ApplicationStatus.valueOf(status);
            app.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status không hợp lệ");
        }

        applicationRepository.save(app);
    }

    // ==============================
    // CONTACT ỨNG VIÊN
    // ==============================
    public void contact(Long id, String message) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.CONTACTED);
        app.setMessage(message);

        applicationRepository.save(app);
    }

    // ==============================
    // DELETE
    // ==============================
    public void delete(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(app);
    }
}