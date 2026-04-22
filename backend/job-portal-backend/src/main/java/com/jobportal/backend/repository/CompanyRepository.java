package com.jobportal.backend.repository;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByStatus(CompanyStatus status);

}