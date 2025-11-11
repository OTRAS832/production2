package com.OTRAS.DemoProject.Repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 
import com.OTRAS.DemoProject.Entity.ExamAssignment;
import com.OTRAS.DemoProject.Entity.PaymentSuccesfullData;

@Repository

public interface ExamAssignmentRepository extends JpaRepository<ExamAssignment, Long> {

	List<ExamAssignment> findByJobPostId(Long jobPostId);

	Optional< ExamAssignment> findByRollNumber(String examRollNo);

	boolean existsByPaymentSuccesfullData(PaymentSuccesfullData student);

	List<ExamAssignment> findByPaymentSuccesfullData(PaymentSuccesfullData student);

    List<ExamAssignment> findAllByRollNumber(String examRollNo);

    List<ExamAssignment> findByPaymentSuccesfullDataIn(List<PaymentSuccesfullData> payments);

}

 