package com.jobportal.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.ApplyJob;

public interface ApplyJobRepository extends JpaRepository<ApplyJob, Long> {

    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    List<ApplyJob> findByUserId(Long userId);

    Optional<ApplyJob> findByUserIdAndJobId(Long userId, Long jobId);
}