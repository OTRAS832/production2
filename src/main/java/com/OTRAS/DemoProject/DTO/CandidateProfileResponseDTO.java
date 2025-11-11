package com.OTRAS.DemoProject.DTO;
 
import lombok.Data;

import java.time.LocalDate;

import java.util.List;
 
@Data

public class CandidateProfileResponseDTO {
 
    private Long id;

    private String otrasId;

    private Long candidateId;
 
    private String candidateName;

    private LocalDate dateOfBirth;

    private String gender;

    private String nationality;
 
    private String permanentState;

    private String permanentDistrict;

    private String permanentAddress;

    private String permanentPincode;
 
    private String currentState;

    private String currentDistrict;

    private String currentAddress;

    private String currentPincode;
 
    // Matriculation (10th)

    private String matriculationEducationBoard;

    private String matriculationRollNumber;

    private Integer matriculationYearOfPassing;

    private String matriculationSchoolName;

    private Double matriculationPercentage;

    private String matriculationCgpa;
 
    // Secondary (12th)

    private String secondaryEducationBoard;

    private String secondaryRollNumber;

    private Integer secondaryYearOfPassing;

    private String secondaryCollegeName;

    private Double secondaryPercentage;

    private String secondaryCgpa;
 
    // Graduation

    private String graduationEducationBoard;

    private String graduationRollNumber;

    private Integer graduationYearOfPassing;

    private String graduationCollegeName;

    private Double graduationPercentage;

    private String graduationCgpa;
 
    private List<String> uploadedDocuments;
    
    
	private String livePhoto;
	private String signatureFile;
	private List<String> centers;

}

 