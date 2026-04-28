package com.jobportal.backend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne
    @JoinColumn(name = "current_package_id") 
    private JobPackage currentPackage;

    private Integer remainingPosts;

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

    public JobPackage getCurrentPackage() {
        return currentPackage;
    }

    public void setCurrentPackage(JobPackage currentPackage) {
        this.currentPackage = currentPackage;
    }

    public Integer getRemainingPosts() {
        return remainingPosts;
    }

    public void setRemainingPosts(Integer remainingPosts) {
        this.remainingPosts = remainingPosts;
    }
}