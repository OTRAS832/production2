package com.OTRAS.DemoProject.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExamDTO {
    private String examTitle;
    private LocalDate examDate;
    private LocalTime examTime;
    private int durationMinutes;
    private Long jobPostId;
    private Long vacancyId;
    private Integer totalMarks;
}
