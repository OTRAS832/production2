package com.OTRAS.DemoProject.DTO;
 
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;
 
@Data

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class SyllabusRequestDTO {

    private String jobCategory;

    private String jobTitle;

    private String qualifications;

}

 