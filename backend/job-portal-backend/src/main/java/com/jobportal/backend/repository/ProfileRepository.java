package com.jobportal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.backend.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long userId);
}