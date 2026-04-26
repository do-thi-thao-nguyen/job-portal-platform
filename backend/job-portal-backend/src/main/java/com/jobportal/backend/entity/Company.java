package com.jobportal.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String address;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employer;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
    this.createdAt = LocalDateTime.now();
}

    // ===== Getter & Setter =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public User getEmployer() {
        return employer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(CompanyStatus status) {
        this.status = status;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}