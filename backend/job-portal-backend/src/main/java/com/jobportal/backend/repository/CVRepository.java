package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.CV;

public interface CVRepository extends JpaRepository<CV, Long> {

    List<CV> findByUserId(Long userId);
}