package com.jobportal.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs")

@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private Double salaryMin;
    private Double salaryMax;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    // ===== RELATION =====

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // ===== CUSTOM METHOD =====

    // 👉 Tính lương trung bình (tránh lỗi getSalary)
    public Double getSalary() {
        if (salaryMin != null && salaryMax != null) {
            return (salaryMin + salaryMax) / 2;
        }
        return salaryMin != null ? salaryMin : salaryMax;
    }
}