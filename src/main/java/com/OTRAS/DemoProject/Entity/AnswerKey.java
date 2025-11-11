package com.OTRAS.DemoProject.Entity;
 
import jakarta.persistence.*;

import lombok.*;
 
@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class AnswerKey {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    private String jobCategory;

    private String jobTitle;

    @Column(length = 1000)

    private String description;

    private String qualifications;

    private String websiteUrl;
 
    private String fileName;

    private String filePath;

}

 