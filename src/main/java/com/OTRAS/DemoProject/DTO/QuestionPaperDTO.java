package com.OTRAS.DemoProject.DTO;
 
import lombok.*;

import java.util.List;
 
@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class QuestionPaperDTO {

    private Long id;

    private String paperName;

    private Long jobPostId;

    private String jobPostTitle;

    private List<QuestionSetDTO> sets;

}

 