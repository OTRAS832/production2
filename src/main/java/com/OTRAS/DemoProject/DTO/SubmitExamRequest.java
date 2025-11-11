package com.OTRAS.DemoProject.DTO;

import java.util.Map;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class SubmitExamRequest {
    public Long examId;
    public String otrId;
    public Map<Long, String> answers;
}
