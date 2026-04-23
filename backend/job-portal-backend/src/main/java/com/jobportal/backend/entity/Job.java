package com.jobportal.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private JobStatus status;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Double salary;

    @PrePersist
    public void prePersist() {
    this.createdAt = LocalDateTime.now();
}

    // ===== GETTER + SETTER =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public JobStatus getStatus() { return status; } 
    public void setStatus(JobStatus status) { this.status = status; } 

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}