package com.jobportal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.backend.entity.JobPackage;

public interface JobPackageRepository extends JpaRepository<JobPackage, Long> {
}