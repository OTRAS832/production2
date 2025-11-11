package com.OTRAS.DemoProject.Repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.DTO.JobCategoryDTO;
import com.OTRAS.DemoProject.Entity.JobPost;
 
@Repository

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
 
	@Query("""
		    SELECT DISTINCT j 
		    FROM JobPost j 
		    LEFT JOIN j.states s 
		    WHERE (:state IS NULL OR LOWER(CAST(s AS string)) = LOWER(:state)) 
		    AND (:country IS NULL OR LOWER(j.country) = LOWER(:country)) 
		    AND (:jobTitle IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :jobTitle, '%'))) 
		    AND (:jobCategory IS NULL OR LOWER(j.jobCategory) LIKE LOWER(CONCAT('%', :jobCategory, '%')))
		    AND (:qualification IS NULL OR LOWER(j.qualification) LIKE LOWER(CONCAT('%', :qualification, '%')))
		""")
		List<JobPost> searchJobs(
		    @Param("state") String state,
		    @Param("country") String country,
		    @Param("jobTitle") String jobTitle,
		    @Param("jobCategory") String jobCategory,
		    @Param("qualification") String qualification
		);
	@Query("SELECT new com.OTRAS.DemoProject.DTO.JobCategoryDTO(j.id, j.jobCategory) FROM JobPost j")
    List<JobCategoryDTO> findAllJobCategoriesWithIds();



 
}

 