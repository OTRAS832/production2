package com.OTRAS.DemoProject.DTO;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdmitCardDTO {
    private String examRollNo;
    private String candidateName;
    private String fatherName;
    private String gender;
    private LocalDate dateOfBirth;
    private String examCenter;
    private String collegeName;
    private String universityName;
    private String jobPostName;  
    private String vacancyName;   
    private String otrasId;
}

