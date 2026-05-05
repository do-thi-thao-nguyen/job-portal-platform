package com.jobportal.backend.service;

import com.jobportal.backend.entity.Company;
import com.jobportal.backend.entity.CompanyStatus;
import com.jobportal.backend.entity.Notification; // 🔥 thiếu cái này
import com.jobportal.backend.repository.CompanyRepository;
import com.jobportal.backend.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // ==============================
    // GET PENDING
    // ==============================
    public List<Company> getPendingCompanies() {
        return companyRepository.findByStatus(CompanyStatus.PENDING);
    }

    // ==============================
    // APPROVE COMPANY
    // ==============================
    public Company approveCompany(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setStatus(CompanyStatus.APPROVED);
        companyRepository.save(company);

        // SEND NOTIFICATION
        if (company.getEmployer() != null) {
            Notification n = new Notification();
            n.setTitle(" Company Approved");
            n.setContent("Công ty \"" + company.getName() + "\" đã được duyệt");
            n.setUser(company.getEmployer());

            notificationRepository.save(n);
        }

        return company;
    }

    // ==============================
    // REJECT COMPANY
    // ==============================
    public Company rejectCompany(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setStatus(CompanyStatus.REJECTED);
        companyRepository.save(company);

        // SEND NOTIFICATION
        if (company.getEmployer() != null) {
            Notification n = new Notification();
            n.setTitle("Company Rejected");
            n.setContent("Công ty \"" + company.getName() + "\" đã bị từ chối");
            n.setUser(company.getEmployer());

            notificationRepository.save(n);
        }

        return company;
    }
}