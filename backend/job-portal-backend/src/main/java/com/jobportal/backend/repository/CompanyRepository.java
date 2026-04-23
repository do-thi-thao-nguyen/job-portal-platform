package com.jobportal.backend.repository;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByStatus(CompanyStatus status);
    boolean existsByEmployer_Id(Long userId);
    long countByStatus(CompanyStatus status); 
    long count();
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}