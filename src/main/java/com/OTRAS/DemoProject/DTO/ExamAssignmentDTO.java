package com.OTRAS.DemoProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAssignmentDTO {

    private Long id;
    private String rollNumber;
    private Long jobPostId;
    private String setName;
    private Long questionPaperId;
    private boolean assigned;

    // Candidate info from PaymentSuccesfullData)
    private String otrId;
    private String paymentStatus;
    private String examRollNo;
    private String candidateName;
    private String dateOfBirth;
    private String gender;
}
