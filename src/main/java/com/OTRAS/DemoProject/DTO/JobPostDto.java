package com.OTRAS.DemoProject.DTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostDto {
    private Long id;
    private String jobCategory;
    private String jobTitle;
    private int totalVacancy;
    private String description;

    private LocalDate postDate;
    private LocalDate lastDate;

    private String qualification;
    private String religionName;  
    private String gender;

    private double fee;

    private String importantDates;
    private String interviewDates;
    private String ageLimit;

    private List<VacancyDetailsDto> vacancyDetails; 
    private String additionalDetails;
    private  String examCenters;

    private boolean livePhotoRequired;
    private boolean signatureRequired;
    private boolean declarationRequired;

    private Map<String, Object> dynamicFields;
    private String uploadFile;
    
    private List<String> states;
    private String country;

}


