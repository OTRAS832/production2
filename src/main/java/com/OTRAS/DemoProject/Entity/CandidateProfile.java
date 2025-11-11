package com.OTRAS.DemoProject.Entity;

import java.time.LocalDate;

import java.util.List;

import jakarta.persistence.Column;

import jakarta.persistence.ElementCollection;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;

import jakarta.persistence.Table;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity

@Table(name = "candidate_profiles")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class CandidateProfile {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToOne

    @JoinColumn(name = "candidate_id", nullable = false, unique = true)

    private Candidate candidate;

    @Column(name = "otras_id", length = 30, unique = true, nullable = true)

	 private String otrasId;

    private String registrationStatus; 

    private String aadharCardNumber;

    private String identificationCard;        

    private String identificationCardNumber;

    private String candidateName;            

    private boolean nameChanged;            
    
    private String changedName;               

    private String gender;

    private LocalDate dateOfBirth;

    private String fathersName;

    private String mothersName;



    private String matriculationEducationBoard;

    private String matriculationRollNumber;

    private Integer matriculationYearOfPassing;

    private String matriculationSchoolName;

    private Double matriculationPercentage;   

    private String matriculationCgpa;        


    private String secondaryEducationBoard;

    private String secondaryRollNumber;

    private Integer secondaryYearOfPassing;

    private String secondaryCollegeName;

    private Double secondaryPercentage;

    private String secondaryCgpa;


    private String diplomaEducationBoard;

    private String diplomaRollNumber;

    private Integer diplomaYearOfPassing;

    private String diplomaCollegeName;

    private Double diplomaPercentage;

    private String diplomaCgpa;


    private String graduationEducationBoard;

    private String graduationRollNumber;

    private Integer graduationYearOfPassing;

    private String graduationCollegeName;

    private Double graduationPercentage;

    private String graduationCgpa;


    private String postGraduationEducationBoard;

    private String postGraduationRollNumber;

    private Integer postGraduationYearOfPassing;

    private String postGraduationCollegeName;

    private Double postGraduationPercentage;

    private String postGraduationCgpa;


    private String phdEducationBoard;

    private String phdRollNumber;

    private Integer phdYearOfPassing;

    private String phdCollegeName;

    private Double phdPercentage;

    private String phdCgpa;


    private boolean casteCertificateIssued;   

    private String casteCertificateNumber;

    private String nationality;               

    private String visibleIdentificationMarks; 

    private String typeOfDisability;          

    private String disabilityCertificateNumber;


    private String permanentAddress;

    private String permanentState;

    private String permanentDistrict;

    private String permanentPincode;


    private boolean currentAddressSameAsPermanent;

    private String currentAddress;

    private String currentState;

    private String currentDistrict;

    private String currentPincode;
 
    

    @ElementCollection

    private List<String> uploadedDocuments;

    private Boolean digilockerVerified;

}

 