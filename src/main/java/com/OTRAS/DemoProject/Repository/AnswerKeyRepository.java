package com.OTRAS.DemoProject.Repository;
 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.AnswerKey;
 
public interface AnswerKeyRepository extends JpaRepository<AnswerKey, Long> {

	boolean existsByJobCategoryAndJobTitle(String jobCategory, String jobTitle);

}

 