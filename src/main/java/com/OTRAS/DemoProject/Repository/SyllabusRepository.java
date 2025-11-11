package com.OTRAS.DemoProject.Repository;
 
import java.util.Optional;
 
//import com.otras.demoproject.entity.Syllabus;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.Entity.Syllabus;
 
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {

	 Optional<Syllabus> findByJobCategoryAndJobTitle(String jobCategory, String jobTitle);

}

 