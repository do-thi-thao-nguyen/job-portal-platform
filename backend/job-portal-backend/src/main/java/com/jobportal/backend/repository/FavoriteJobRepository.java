package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.backend.entity.FavoriteJob;

public interface FavoriteJobRepository extends JpaRepository<FavoriteJob, Long> {

    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    List<FavoriteJob> findByUserId(Long userId);

    void deleteByUserIdAndJobId(Long userId, Long jobId);
}