package com.OTRAS.DemoProject.DTO;
 
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;
 
@Data

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class ResultRequestDTO {

    private String jobCategory;

    private String jobTitle;

    private String releasedDate;

    private String websiteUrl;

}

 