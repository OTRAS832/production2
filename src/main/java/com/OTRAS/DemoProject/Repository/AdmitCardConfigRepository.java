package com.OTRAS.DemoProject.Repository;

import com.OTRAS.DemoProject.Entity.AdmitCardConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmitCardConfigRepository extends JpaRepository<AdmitCardConfig, Long> {
    Optional<AdmitCardConfig> findByJobPostId(Long jobPostId);
}