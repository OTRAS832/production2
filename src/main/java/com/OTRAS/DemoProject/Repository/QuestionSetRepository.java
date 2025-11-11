package com.OTRAS.DemoProject.Repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.QuestionSet;
 
@Repository

public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {

    List<QuestionSet> findByQuestionPaperId(Long paperId);

    @Query("SELECT qs FROM QuestionSet qs WHERE qs.questionPaper.jobPost.id = :jobPostId")
    List<QuestionSet> findByJobPostId(Long jobPostId);

    QuestionSet findByQuestionPaperIdAndSetName(Long questionPaperId, String setName);

 
}

 