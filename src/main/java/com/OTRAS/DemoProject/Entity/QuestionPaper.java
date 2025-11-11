package com.OTRAS.DemoProject.Entity;
 
import jakarta.persistence.*;

import lombok.*;

import java.util.List;
 
@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class QuestionPaper {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    private String paperName;  
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "job_post_id")

    private JobPost jobPost;
 
    @OneToMany(mappedBy = "questionPaper", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<QuestionSet> sets;

}

 