package com.OTRAS.DemoProject.DTO;
 
import java.util.List;

import com.OTRAS.DemoProject.Entity.Question;

import lombok.*;
 
@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class ExamConductResponseDTO {

    private String candidateName;

    private String examRollNo;

    private String setName;

    private String questionPaperTitle;

    private List<Question> questions;

}

 