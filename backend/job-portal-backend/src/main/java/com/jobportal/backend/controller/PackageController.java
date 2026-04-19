package com.jobportal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.backend.entity.Package;
import com.jobportal.backend.repository.PackageRepository;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;

    // tạo package
    @PostMapping
    public Package create(@RequestBody Package pkg) {
        return packageRepository.save(pkg);
    }

    // list package
    @GetMapping
    public List<Package> getAll() {
        return packageRepository.findAll();
    }

    // mua package (fake)
    @PostMapping("/buy")
    public String buy(@RequestParam Long packageId,
                      @RequestParam Long companyId) {

        return "Company " + companyId + " bought package " + packageId;
    }
}