package com.OTRAS.DemoProject.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otrId; 
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private String setAssigned; 
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean completed;
    private String status; 
}
