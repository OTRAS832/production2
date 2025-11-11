package com.OTRAS.DemoProject.Repository;
 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.Cutoff;
 
public interface CutoffRepository extends JpaRepository<Cutoff, Long> {
 
	boolean existsByJobCategoryAndJobTitleAndReleasedDate(String jobCategory, String jobTitle, String releasedDate);
 

}

 