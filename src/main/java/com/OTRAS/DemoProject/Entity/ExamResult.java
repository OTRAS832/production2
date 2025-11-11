
package com.OTRAS.DemoProject.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examRollNo;
    private String candidateName;
    private String setName;
    private Long questionPaperId;

    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double percentage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private ExamAssignment examAssignment;
}

