package com.jobportal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.backend.entity.FavoriteJob;

@Repository
public interface FavoriteJobRepository extends JpaRepository<FavoriteJob, Long> {

    List<FavoriteJob> findByUserId(Long userId);
}