package com.jobportal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.backend.entity.CompanyPackage;
import java.util.List;

public interface CompanyPackageRepository extends JpaRepository<CompanyPackage, Long> {
    List<CompanyPackage> findByCompanyId(Long companyId);
}