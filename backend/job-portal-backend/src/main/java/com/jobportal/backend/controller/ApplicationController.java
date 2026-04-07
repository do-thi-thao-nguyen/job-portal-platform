package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Applicant;
import com.jobportal.backend.entity.Application;
import com.jobportal.backend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    // apply job
    @PostMapping
    public Application apply(@RequestBody Application app) {
        return applicationRepository.save(app);
    }

    // xem job đã apply
    @GetMapping("/{email}")
    public List<Application> getByEmail(@PathVariable String email) {
        return applicationRepository.findByEmail(email);
    }
     // Tạo applicant
    @PostMapping
    public Applicant create(@RequestBody Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    // Lấy danh sách applicant
    @GetMapping
    public List<Applicant> getAll() {
        return applicantRepository.findAll();
    }
}