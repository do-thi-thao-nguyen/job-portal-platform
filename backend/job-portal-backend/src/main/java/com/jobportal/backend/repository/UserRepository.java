package com.jobportal.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import com.jobportal.backend.entity.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    long count();
    long countByRole(Role role);
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}