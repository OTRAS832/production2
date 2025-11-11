package com.OTRAS.DemoProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.ExamSession;

@Repository
public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    Optional<ExamSession> findByExamIdAndOtrId(Long examId, String otrId);
    List<ExamSession> findByExamId(Long examId);
    List<ExamSession> findByOtrId(String otrId);
}
