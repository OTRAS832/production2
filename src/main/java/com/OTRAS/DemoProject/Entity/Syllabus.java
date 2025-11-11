package com.OTRAS.DemoProject.Entity;
 
import jakarta.persistence.*;

import lombok.*;
 
@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class Syllabus {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    private String jobCategory;

    private String jobTitle;

    private String qualifications;

    private String syllabusFileName;

    private String syllabusFilePath;

}

 