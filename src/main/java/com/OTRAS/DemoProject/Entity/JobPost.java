package com.OTRAS.DemoProject.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobCategory;
    private String jobTitle;
    private int totalVacancy;
    @Lob
    @Column(columnDefinition = "TEXT" , length = 2000)
    private String description;

    private LocalDate postDate;
    private LocalDate lastDate;
    
    
    private String qualification;

    
    @OneToMany(mappedBy = "jobPost",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private List<VacancyDetails> VacancyDetails;
    
    private String gender;

    private double fee;

    private String importantDates;
    private String interviewDates;
    private String ageLimit;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String additionalDetails;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String examCenters;
    private boolean livePhotoRequired;
    private boolean signatureRequired;
    private boolean declarationRequired;

    @Column(name = "dynamic_fields", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)  
    private String dynamicFields;
    
    private String uploadFile;
    
//    @ElementCollection
//    private List<String> states;
    @ElementCollection
    @CollectionTable(name = "job_post_states", joinColumns = @JoinColumn(name = "job_post_id"))
    @Column(name = "state", columnDefinition = "TEXT")   
    private List<String> states;

    private String country;
}
