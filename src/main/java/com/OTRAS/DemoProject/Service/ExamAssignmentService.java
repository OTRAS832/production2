package com.OTRAS.DemoProject.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.OTRAS.DemoProject.DTO.ExamAssignmentDTO;
import com.OTRAS.DemoProject.Entity.*;
import com.OTRAS.DemoProject.Repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamAssignmentService {

    private final PaymentSuccesfullDataRepository paymentRepo;
    private final QuestionPaperRepository questionPaperRepo;
    private final ExamAssignmentRepository examAssignmentRepo;
    private final QuestionSetRepository questionSetRepo;
    private final ExamResultRepository examResultRepo;

//    @Transactional
//    public String assignSetsToCandidates(Long jobPostId) {
//        List<PaymentSuccesfullData> students = paymentRepo.findStudentsByJobPostId(jobPostId);
//        List<QuestionSet> sets = questionSetRepo.findByJobPostId(jobPostId);
//
//        if (students.isEmpty()) return "No students found for JobPost ID: " + jobPostId;
//        if (sets.isEmpty()) return " No question sets found for JobPost ID: " + jobPostId;
//
//        int index = 0;
//        for (PaymentSuccesfullData student : students) {
//            QuestionSet selectedSet = sets.get(index % sets.size());
//
//            ExamAssignment assignment = ExamAssignment.builder()
//                    .rollNumber(student.getExamRollNo())
//                    .jobPostId(jobPostId)
//                    .setName(selectedSet.getSetName())
//                    .questionPaperId(selectedSet.getQuestionPaper().getId())
//                    .assigned(true)
//                    .examStatus("Assigned") 
//                    .paymentSuccesfullData(student)
//                    .build();
//
//            examAssignmentRepo.save(assignment);
//            index++;
//        }
//
//        return "Assigned " + sets.size() + " sets to " + students.size() + " students successfully.";
//    }

    @Transactional
    public String assignSetsToCandidates(Long jobPostId) {
        List<PaymentSuccesfullData> students = paymentRepo.findAllByJobPostIdAndPaymentStatus(jobPostId, "SUCCESS");
        List<QuestionSet> sets = questionSetRepo.findByJobPostId(jobPostId);

        if (students.isEmpty()) return "No students found for JobPost ID: " + jobPostId;
        if (sets.isEmpty()) return "No question sets found for JobPost ID: " + jobPostId;

        int index = 0;
        int assignedCount = 0;

        for (PaymentSuccesfullData student : students) {

            // ✅ Skip if this student's roll number already exists
            List<ExamAssignment> existingAssignments = examAssignmentRepo.findAllByRollNumber(student.getExamRollNo());
            if (existingAssignments != null && !existingAssignments.isEmpty()) {
                continue; // Skip this student
            }

            QuestionSet selectedSet = sets.get(index % sets.size());

            ExamAssignment assignment = ExamAssignment.builder()
                    .rollNumber(student.getExamRollNo())
                    .jobPostId(jobPostId)
                    .setName(selectedSet.getSetName())
                    .questionPaperId(selectedSet.getQuestionPaper().getId())
                    .assigned(true)
                    .examStatus("Assigned")
                    .paymentSuccesfullData(student)
                    .build();

            examAssignmentRepo.save(assignment);
            assignedCount++;
            index++;
        }

        return "Assigned sets to " + assignedCount + " new students successfully.";
    }




    
    @Transactional(readOnly = true)
    public List<ExamAssignmentDTO> getAssignmentsByJobPostId(Long jobPostId) {
        return examAssignmentRepo.findByJobPostId(jobPostId)
                .stream()
                .map(assignment -> {
                    PaymentSuccesfullData payment = assignment.getPaymentSuccesfullData();
                    CandidateProfile profile = payment.getCandidateProfile();
                    Candidate candidate = (profile != null) ? profile.getCandidate() : null;

                    return new ExamAssignmentDTO(
                            assignment.getId(),
                            assignment.getRollNumber(),
                            assignment.getJobPostId(),
                            assignment.getSetName(),
                            assignment.getQuestionPaperId(),
                            assignment.isAssigned(),
                            payment.getOtrId(),
                            payment.getPaymentStatus(),
                            payment.getExamRollNo(),
                            candidate != null ? candidate.getFullName() : null,
                            candidate != null ? candidate.getDateOfBirth().toString() : null,
                            candidate != null ? candidate.getGender() : null
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, Object> conductExam(String candidateName, String examRollNo) {

        // ✅ Step 1: Validate that an assignment exists for this roll number
        ExamAssignment assignment = examAssignmentRepo.findAllByRollNumber(examRollNo)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("❌ No assignment found for the given roll number."));

        // ✅ Step 2: Fetch payment and candidate profile
        PaymentSuccesfullData payment = assignment.getPaymentSuccesfullData();
        if (payment == null) {
            throw new IllegalStateException("❌ Payment record is missing for this assignment.");
        }

        CandidateProfile profile = payment.getCandidateProfile();
        if (profile == null) {
            throw new IllegalStateException("❌ Candidate profile not found for this payment record.");
        }

        // ✅ Step 3: Validate candidate name (check candidateProfile first, then candidate if needed)
        String dbName = profile.getCandidateName() != null
                ? profile.getCandidateName()
                : (profile.getCandidate() != null ? profile.getCandidate().getFullName() : null);

        if (dbName == null || !dbName.equalsIgnoreCase(candidateName)) {
            throw new IllegalArgumentException("❌ Candidate name does not match our records.");
        }

        // ✅ Step 4: Check if candidate already completed the exam
        ExamResult existingResult = examResultRepo.findByExamRollNo(examRollNo);
        if (existingResult != null) {
            Map<String, Object> restricted = new LinkedHashMap<>();
            restricted.put("examRollNo", examRollNo);
            restricted.put("candidateName", existingResult.getCandidateName());
            restricted.put("message", "⚠️ You have already completed the exam. Multiple attempts are not allowed.");
            restricted.put("totalQuestions", existingResult.getTotalQuestions());
            restricted.put("correctAnswers", existingResult.getCorrectAnswers());
            restricted.put("wrongAnswers", existingResult.getWrongAnswers());
            restricted.put("percentage", existingResult.getPercentage() + "%");
            return restricted;
        }

        // ✅ Step 5: Load question paper and question set
        QuestionPaper questionPaper = questionPaperRepo.findById(assignment.getQuestionPaperId())
                .orElseThrow(() -> new IllegalArgumentException("❌ Question paper not found for this assignment."));

        QuestionSet questionSet = questionSetRepo.findByQuestionPaperIdAndSetName(
                questionPaper.getId(),
                assignment.getSetName()
        );

        if (questionSet == null || questionSet.getQuestions() == null || questionSet.getQuestions().isEmpty()) {
            throw new IllegalStateException("❌ No questions available for this exam set.");
        }

        // ✅ Step 6: Mark exam as started (if not already submitted)
        if (!"Submitted".equalsIgnoreCase(assignment.getExamStatus())) {
            assignment.setExamStatus("Started");
            examAssignmentRepo.save(assignment);
        }

        // ✅ Step 7: Build exam response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("candidateName", dbName);
        response.put("examRollNo", examRollNo);
        response.put("setName", questionSet.getSetName());
        response.put("paperName", questionPaper.getPaperName());
        response.put("paperId", questionPaper.getId());

        // ✅ Step 8: Add questions
        List<Map<String, Object>> questionList = questionSet.getQuestions().stream().map(q -> {
            Map<String, Object> questionMap = new LinkedHashMap<>();
            questionMap.put("id", q.getId());
            questionMap.put("questionText", q.getQuestionText());
            questionMap.put("optionA", q.getOptionA());
            questionMap.put("optionB", q.getOptionB());
            questionMap.put("optionC", q.getOptionC());
            questionMap.put("optionD", q.getOptionD());
            return questionMap;
        }).collect(Collectors.toList());

        response.put("questions", questionList);
        response.put("message", "✅ You can now start your exam.");

        return response;
    }


    @Transactional
    public Map<String, Object> submitExam(String examRollNo, Map<String, String> submittedAnswers) {
        ExamResult existingResult = examResultRepo.findByExamRollNo(examRollNo);
        if (existingResult != null) {
            Map<String, Object> duplicateResponse = new LinkedHashMap<>();
            duplicateResponse.put("examRollNo", examRollNo);
            duplicateResponse.put("candidateName", existingResult.getCandidateName());
            duplicateResponse.put("message", " Exam already submitted. Duplicate submission not allowed.");
            duplicateResponse.put("totalQuestions", existingResult.getTotalQuestions());
            duplicateResponse.put("correctAnswers", existingResult.getCorrectAnswers());
            duplicateResponse.put("wrongAnswers", existingResult.getWrongAnswers());
            duplicateResponse.put("percentage", existingResult.getPercentage() + "%");
            return duplicateResponse;
        }

        Optional<ExamAssignment> optionalAssignment = examAssignmentRepo.findByRollNumber(examRollNo);
        if (optionalAssignment.isEmpty()) return Map.of("error", "Invalid exam roll number");

        ExamAssignment assignment = optionalAssignment.get();
        QuestionPaper questionPaper = questionPaperRepo.findById(assignment.getQuestionPaperId()).orElse(null);
        if (questionPaper == null) return Map.of("error", "Question paper not found for this assignment");

        QuestionSet questionSet = questionSetRepo.findByQuestionPaperIdAndSetName(
                questionPaper.getId(),
                assignment.getSetName()
        );
        if (questionSet == null || questionSet.getQuestions() == null)
            return Map.of("error", "No questions found for assigned set");

        // Calculate result
        int total = questionSet.getQuestions().size();
        int correct = 0;
        for (Question q : questionSet.getQuestions()) {
            String selected = submittedAnswers.get(String.valueOf(q.getId()));
            if (selected != null && selected.equalsIgnoreCase(q.getCorrectAnswer())) {
                correct++;
            }
        }

        int wrong = total - correct;
        double percentage = ((double) correct / total) * 100;

        ExamResult result = ExamResult.builder()
                .examRollNo(examRollNo)
                .candidateName(assignment.getPaymentSuccesfullData().getCandidateProfile().getCandidate().getFullName())
                .setName(assignment.getSetName())
                .questionPaperId(questionPaper.getId())
                .totalQuestions(total)
                .correctAnswers(correct)
                .wrongAnswers(wrong)
                .percentage(percentage)
                .examAssignment(assignment)
                .build();
        examResultRepo.save(result);

        assignment.setExamStatus("Submitted");
        examAssignmentRepo.save(assignment);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("examRollNo", examRollNo);
        response.put("candidateName", result.getCandidateName());
        response.put("setName", result.getSetName());
        response.put("totalQuestions", total);
        response.put("correctAnswers", correct);
        response.put("wrongAnswers", wrong);
        response.put("percentage", percentage + "%");
        response.put("message", "Exam submitted successfully");
        return response;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDashboardData() {
        List<ExamAssignment> assignments = examAssignmentRepo.findAll();

        return assignments.stream().map(a -> {
            CandidateProfile profile = a.getPaymentSuccesfullData().getCandidateProfile();
            String candidateName = profile != null ? profile.getCandidate().getFullName() : "N/A";

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("candidateName", candidateName);
            row.put("examRollNo", a.getRollNumber());
            row.put("setName", a.getSetName());
            row.put("jobPostId", a.getJobPostId());
            row.put("examStatus", a.getExamStatus());
            return row;
        }).collect(Collectors.toList());
    }

@Transactional(readOnly = true)
public Map<String, Object> getExamStatusSummary() {
    Map<String, Object> summary = new LinkedHashMap<>();

    List<ExamAssignment> all = examAssignmentRepo.findAll();

    long assignedCount = all.stream().filter(a -> "Assigned".equalsIgnoreCase(a.getExamStatus())).count();
    long startedCount = all.stream().filter(a -> "Started".equalsIgnoreCase(a.getExamStatus())).count();
    long submittedCount = all.stream().filter(a -> "Submitted".equalsIgnoreCase(a.getExamStatus())).count();
    long paymentCount = all.stream().filter(a -> "SUCCESS".equalsIgnoreCase(a.getExamStatus())).count();

    summary.put("Payment Successful", paymentCount);
    summary.put("Assigned", assignedCount);
    summary.put("Started", startedCount);
    summary.put("Submitted", submittedCount);
    summary.put("Total Students", all.size());

    return summary;
}

@Transactional(readOnly = true)
public List<Map<String, Object>> getStudentsByStatus(String status) {
    List<ExamAssignment> list;

    if (status == null || status.isEmpty() || "All".equalsIgnoreCase(status)) {
        list = examAssignmentRepo.findAll();
    } else {
        list = examAssignmentRepo.findAll().stream()
                .filter(a -> status.equalsIgnoreCase(a.getExamStatus()))
                .collect(Collectors.toList());
    }

    return list.stream().map(a -> {
        CandidateProfile profile = a.getPaymentSuccesfullData().getCandidateProfile();
        String candidateName = profile != null ? profile.getCandidate().getFullName() : "N/A";

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("candidateName", candidateName);
        row.put("examRollNo", a.getRollNumber());
        row.put("setName", a.getSetName());
        row.put("jobPostId", a.getJobPostId());
        row.put("examStatus", a.getExamStatus());
        row.put("paymentStatus", a.getPaymentSuccesfullData().getPaymentStatus());
        return row;
    }).collect(Collectors.toList());
}

@Transactional(readOnly = true)
public Map<String, Object> getFullDashboard() {
    Map<String, Object> dashboard = new LinkedHashMap<>();
    dashboard.put("summary", getExamStatusSummary());
    dashboard.put("students", getStudentsByStatus("All"));
    return dashboard;
}



@Transactional
public String assignSetsSafely(Long jobPostId) {
    List<PaymentSuccesfullData> students =
            paymentRepo.findAllByJobPostIdAndPaymentStatus(jobPostId, "SUCCESS");
    List<QuestionSet> sets = questionSetRepo.findByJobPostId(jobPostId);

    if (students.isEmpty()) {
        return "❌ No successful payments found for JobPost ID: " + jobPostId;
    }

    if (sets.isEmpty()) {
        return "❌ No question sets found for JobPost ID: " + jobPostId;
    }

    int index = 0;
    int assignedCount = 0;
    int skippedCount = 0;

    for (PaymentSuccesfullData student : students) {
        String rollNo = student.getExamRollNo();

        // ✅ Step 1: Skip if roll number already exists
        List<ExamAssignment> existingByRoll = examAssignmentRepo.findAllByRollNumber(rollNo);
        if (!existingByRoll.isEmpty()) {
            skippedCount++;
            continue; // already assigned
        }

        // ✅ Step 2: Skip if already assigned via same payment record
        List<ExamAssignment> existingByPayment =
                examAssignmentRepo.findByPaymentSuccesfullData(student);
        if (!existingByPayment.isEmpty()) {
            skippedCount++;
            continue;
        }

        // ✅ Step 3: Assign next question set in round-robin order
        QuestionSet selectedSet = sets.get(index % sets.size());

        ExamAssignment newAssignment = ExamAssignment.builder()
                .rollNumber(rollNo)
                .jobPostId(jobPostId)
                .setName(selectedSet.getSetName())
                .questionPaperId(selectedSet.getQuestionPaper().getId())
                .assigned(true)
                .examStatus("Assigned")
                .paymentSuccesfullData(student)
                .build();

        examAssignmentRepo.save(newAssignment);
        assignedCount++;
        index++;
    }

    return String.format("✅ %d students assigned successfully, %d skipped (already assigned).",
            assignedCount, skippedCount);
}

@Transactional(readOnly = true)
public Map<String, Object> getResultByRollNo(String examRollNo) {
    ExamResult result = examResultRepo.findByExamRollNo(examRollNo);

    if (result == null) {
        return Map.of("error", "❌ No result found for the given roll number: " + examRollNo);
    }

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("examRollNo", result.getExamRollNo());
    response.put("candidateName", result.getCandidateName());
    response.put("setName", result.getSetName());
    response.put("questionPaperId", result.getQuestionPaperId());
    response.put("totalQuestions", result.getTotalQuestions());
    response.put("correctAnswers", result.getCorrectAnswers());
    response.put("wrongAnswers", result.getWrongAnswers());
    response.put("percentage", result.getPercentage() + "%");
    response.put("status", "✅ Result found");

    return response;
}





@Transactional(readOnly = true)
public List<Map<String, Object>> getResultsByCandidateId(Long candidateId) {
    // Step 1: Find all payments by candidate ID
    List<PaymentSuccesfullData> payments = paymentRepo.findByCandidateProfile_Candidate_Id(candidateId);
    if (payments.isEmpty()) {
        throw new IllegalArgumentException("❌ No payment records found for this candidate.");
    }

    // Step 2: Find all exam assignments linked to these payments
    List<ExamAssignment> assignments = examAssignmentRepo.findByPaymentSuccesfullDataIn(payments);
    if (assignments.isEmpty()) {
        throw new IllegalArgumentException("❌ No exam assignments found for this candidate.");
    }

    // Step 3: Build result list
    List<Map<String, Object>> results = new ArrayList<>();

    for (ExamAssignment assignment : assignments) {
        String rollNo = assignment.getRollNumber();
        ExamResult result = examResultRepo.findByExamRollNo(rollNo);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("examRollNo", rollNo);
        data.put("candidateName", assignment.getPaymentSuccesfullData().getCandidateProfile().getCandidateName());
        data.put("setName", assignment.getSetName());
        data.put("questionPaperId", assignment.getQuestionPaperId());

        if (result != null) {
            data.put("totalQuestions", result.getTotalQuestions());
            data.put("correctAnswers", result.getCorrectAnswers());
            data.put("wrongAnswers", result.getWrongAnswers());
            data.put("percentage", result.getPercentage() + "%");
            data.put("status", "✅ Result found");
        } else {
            data.put("totalQuestions", "-");
            data.put("correctAnswers", "-");
            data.put("wrongAnswers", "-");
            data.put("percentage", "-");
            data.put("status", "❌ Result not yet published");
        }

        results.add(data);
    }

    return results;
}


}
