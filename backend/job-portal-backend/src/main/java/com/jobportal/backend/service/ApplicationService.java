package com.jobportal.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jobportal.backend.dto.ApplyJobRequest;
import com.jobportal.backend.entity.Application;
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

    // APPLY JOB
    public void applyJob(Long userId, ApplyJobRequest request) {

        // 1. check user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. check job
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 3. check CV
        CV cv = cvRepository.findById(request.getCvId())
                .orElseThrow(() -> new RuntimeException("CV not found"));

        // 4. check CV thuộc user
        if (!cv.getUser().getId().equals(userId)) {
            throw new RuntimeException("CV không thuộc user");
        }

        // 5. check apply trùng
        if (applicationRepository.existsByUserIdAndJobId(userId, request.getJobId())) {
            throw new RuntimeException("Bạn đã apply job này rồi");
        }

        // 6. tạo application
        Application app = new Application();

        // ===== version safe =====
        app.setEmail(user.getEmail());
        app.setUserId(user.getId());

        app.setJob(job);

        // dùng cả 2 (safe + nâng cấp)
        app.setCv(cv);
        app.setCvUrl(cv.getFileUrl());

        app.setMessage(request.getNote());
        app.setStatus("PENDING");
        app.setAppliedAt(LocalDateTime.now());

        applicationRepository.save(app);
    }

    // LẤY DANH SÁCH APPLY
    public List<Application> getMyApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
}