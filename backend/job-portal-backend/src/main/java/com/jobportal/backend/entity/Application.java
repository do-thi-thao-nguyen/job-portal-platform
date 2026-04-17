package com.jobportal.backend.entity;

import jakarta.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // ứng viên

    private String cvUrl;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // Getter Setter
    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
}