package com.jobportal.backend.repository;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.backend.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(JobStatus status);
    long count();
    long countByStatus(JobStatus status);
    long countByCategory_Id(Long categoryId);
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Job> findByCompanyEmployer(User employer);
}