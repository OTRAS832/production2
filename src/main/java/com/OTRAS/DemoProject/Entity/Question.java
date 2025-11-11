package com.OTRAS.DemoProject.Entity;
 
import jakarta.persistence.*;

import lombok.*;
 
@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class Question {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    

    @Column(length = 2000)

    private String questionText;
 
    @Column(length = 500)

    private String optionA;
 
    @Column(length = 500)

    private String optionB;
 
    @Column(length = 500)

    private String optionC;
 
    @Column(length = 500)

    private String optionD;
 
    private String correctAnswer; 
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "question_set_id")

    private QuestionSet questionSet;

}

 