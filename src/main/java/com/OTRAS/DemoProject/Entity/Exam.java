package com.OTRAS.DemoProject.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examTitle;
    private LocalDate examDate;
    private LocalTime examTime;
    private int durationMinutes; 

    private Long jobPostId;  
    private Long vacancyId; 

    private Integer totalMarks; 
}
