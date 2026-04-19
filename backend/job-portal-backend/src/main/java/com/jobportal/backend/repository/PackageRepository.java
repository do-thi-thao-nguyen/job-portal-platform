package com.jobportal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.Package;

public interface PackageRepository extends JpaRepository<Package, Long> {
}