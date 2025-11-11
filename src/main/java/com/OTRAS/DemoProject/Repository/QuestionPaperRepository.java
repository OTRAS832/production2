package com.OTRAS.DemoProject.Repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 
import com.OTRAS.DemoProject.Entity.QuestionPaper;

import java.util.List;
 
@Repository

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long> {

    List<QuestionPaper> findByJobPostId(Long jobPostId);

}

 