package com.OTRAS.DemoProject.Repository;
 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.Result;
 
public interface ResultRepository extends JpaRepository<Result, Long> {

//	boolean existsByJobCategoryIgnoreCaseAndJobTitleIgnoreCase(String jobCategory, String jobTitle);
 
	boolean existsByJobCategoryAndJobTitle(String jobCategory, String jobTitle);

}

 