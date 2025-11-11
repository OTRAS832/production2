package com.OTRAS.DemoProject.DTO;
import java.util.List;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDetailsDto {
    private Long id;
    private String postName;
    private int total;
    private int age;
    private List<ReligionDto> religions;
}