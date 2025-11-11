package com.OTRAS.DemoProject.Repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OTRAS.DemoProject.Entity.Candidate;
 
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	 boolean existsByEmail(String email);
	 boolean existsByMobile(String mobile);
	 Optional<Candidate> findByEmail(String email);
	 
	 @Query("SELECT s.fcmDeviceToken FROM Candidate s WHERE s.fcmDeviceToken IS NOT NULL")
	 List<String> getAllDeviceTokens();

}
 