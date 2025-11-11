package com.OTRAS.DemoProject.Repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.OTRAS.DemoProject.Entity.Candidate;
import com.OTRAS.DemoProject.Entity.CandidateProfile;
 
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {



	//Optional<Candidate> findByCandidate(Candidate candidate);
	
	
	boolean existsByCandidate_Id(Long candidateId);
	 
    Optional<CandidateProfile> findByCandidate(Candidate candidate);
 
    @Query("SELECT cp FROM CandidateProfile cp WHERE cp.candidate.id = :candidateId")
    CandidateProfile findByCandidateId(@Param("candidateId") Long candidateId);
 
    CandidateProfile findByOtrasId(String otrasId);
 
    boolean existsByCandidate(Candidate candidate);
 
    boolean existsByOtrasId(String otrasId);
 
 

}
 