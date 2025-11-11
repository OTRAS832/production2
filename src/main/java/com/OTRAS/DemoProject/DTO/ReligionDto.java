package com.OTRAS.DemoProject.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReligionDto {
    private Long id;
    private String religionName;
    private int seats;
}
