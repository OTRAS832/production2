package com.OTRAS.DemoProject.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ExamSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setName; 

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ElementCollection
    @CollectionTable(name = "examset_question_ids", joinColumns = @JoinColumn(name="examset_id"))
    @Column(name = "question_id")
    private List<Long> questionIds; 
}
