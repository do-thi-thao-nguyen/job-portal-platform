package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.Application;
import com.jobportal.backend.entity.ApplicationStatus;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // check đã apply chưa
    boolean existsByUser_IdAndJob_Id(Long userId, Long jobId);

    // lấy theo user
    List<Application> findByUser_Id(Long userId);

    List<Application> findByUser_Email(String email);

    // lấy theo job
    List<Application> findByJob_Id(Long jobId);

    // ===== SEARCH =====

    // search email
    List<Application> findByJob_IdAndUser_EmailContainingIgnoreCase(Long jobId, String email);

    // filter status
    List<Application> findByJob_IdAndStatus(Long jobId, ApplicationStatus status);

    // search email + status
    List<Application> findByJob_IdAndUser_EmailContainingIgnoreCaseAndStatus(
            Long jobId,
            String email,
            ApplicationStatus status
    );
}