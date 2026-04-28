package com.jobportal.backend.entity;

import jakarta.persistence.*;

@Entity
public class JobPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Basic / Pro / VIP
    private Long price;
    private Integer postLimit;  // số job được đăng


    // ===== getter setter =====
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public Integer getPostLimit() { return postLimit; }
    public void setPostLimit(Integer postLimit) { this.postLimit = postLimit; }
}