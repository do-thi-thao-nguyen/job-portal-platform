package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByUser_IdAndJob_Id(Long userId, Long jobId);

    List<Application> findByUser_Id(Long userId);

    List<Application> findByJob_Id(Long jobId);

    List<Application> findByJob_IdAndEmailContaining(Long jobId, String email);
}