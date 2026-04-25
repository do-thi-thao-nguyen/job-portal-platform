package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // dùng cho employer
    List<Application> findByJobId(Long jobId);

    List<Application> findByJobIdAndEmailContaining(Long jobId, String email);

    // dùng cho candidate
    List<Application> findByUserId(Long userId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);

}