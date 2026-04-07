package com.jobportal.backend.repository;

import com.jobportal.backend.entity.ApplyJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyJobRepository extends JpaRepository<ApplyJob, Long> {

    List<ApplyJob> findByUsername(String username);
}