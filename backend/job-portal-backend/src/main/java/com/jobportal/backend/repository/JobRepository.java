package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobportal.backend.entity.Job;
import com.jobportal.backend.entity.JobStatus;

public interface JobRepository extends JpaRepository<Job, Long> {

    // USER
    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    // ADMIN
    List<Job> findByStatus(JobStatus status);
    long countByStatus(JobStatus status);

    // SEARCH
    @Query("""
    SELECT j FROM Job j
    WHERE j.status = 'APPROVED'
    AND (:keyword IS NULL OR j.title LIKE %:keyword%)
    AND (:location IS NULL OR j.location LIKE %:location%)
    AND (:minSalary IS NULL OR j.salaryMin >= :minSalary)
    """)
    List<Job> search(String keyword, String location, Double minSalary);
}