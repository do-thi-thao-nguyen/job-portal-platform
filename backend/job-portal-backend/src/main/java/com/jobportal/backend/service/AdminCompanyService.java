package com.jobportal.backend.service;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import com.jobportal.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // Lấy danh sách công ty chờ duyệt
    public List<Company> getPendingCompanies() {
        return companyRepository.findByStatus(CompanyStatus.PENDING);
    }

    // Approve company
    public Company approveCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setStatus(CompanyStatus.APPROVED);
        return companyRepository.save(company);
    }

    // Reject company
    public Company rejectCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setStatus(CompanyStatus.REJECTED);
        return companyRepository.save(company);
    }
}