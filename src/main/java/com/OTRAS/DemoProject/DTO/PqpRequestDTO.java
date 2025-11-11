package com.OTRAS.DemoProject.DTO;
 
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;
 
@Data

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class PqpRequestDTO {

    private String jobCategory;

    private String jobTitle;

    private String languages;

    private String qualifications;

    private String pqp;

}

 