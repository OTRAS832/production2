package com.OTRAS.DemoProject.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.ExamSet;

@Repository
public interface ExamSetRepository extends JpaRepository<ExamSet, Long> {
    List<ExamSet> findByExamId(Long examId);
    Optional<ExamSet> findByExamIdAndSetName(Long examId, String setName);
}
