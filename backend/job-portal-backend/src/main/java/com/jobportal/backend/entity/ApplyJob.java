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
import lombok.Data;

@Data
@Entity
@Table(name = "apply_jobs")
public class ApplyJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== USER =====
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ===== JOB =====
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // ===== STATUS =====
    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    // ===== TIME =====
    private LocalDateTime appliedAt;

    // ===== AUTO SET =====
    @PrePersist
    public void prePersist() {
        this.appliedAt = LocalDateTime.now();
        this.status = ApplyStatus.APPLIED;
    }
}