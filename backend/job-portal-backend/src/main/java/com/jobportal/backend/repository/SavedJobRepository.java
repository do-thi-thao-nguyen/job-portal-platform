package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.SavedJob;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByUser_Email(String email);

    boolean existsByUser_EmailAndJob_Id(String email, Long jobId);

    void deleteByUser_EmailAndJob_Id(String email, Long jobId);
}
