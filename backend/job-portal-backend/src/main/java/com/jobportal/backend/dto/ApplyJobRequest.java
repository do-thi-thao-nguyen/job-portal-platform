package com.jobportal.backend.dto;

public class ApplyJobRequest {

    private Long jobId;
    private Long cvId;
    private String note;

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public Long getCvId() { return cvId; }
    public void setCvId(Long cvId) { this.cvId = cvId; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}