package com.OTRAS.DemoProject.DTO;
 
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;
 
@Data

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class AnswerKeyRequestDTO {

    private String jobCategory;

    private String jobTitle;

    private String description;

    private String qualifications;

    private String websiteUrl;

}

 