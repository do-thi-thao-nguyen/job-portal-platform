package com.jobportal.backend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== GIỮ NGUYÊN =====
    private String email;
    private String cvUrl;
    private String status;
    private String message;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CV cv;

    private Long userId;   // link tới User

    private LocalDateTime appliedAt;

    // ===== AUTO TIME =====
    @PrePersist
    public void prePersist() {
        this.appliedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // ===== getter setter =====

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getAppliedAt() { return appliedAt; }

    public void setAppliedAt(LocalDateTime appliedAt) {this.appliedAt = appliedAt;}

    public CV getCv() { return cv; } 
    public void setCv(CV cv) { this.cv = cv; }
}