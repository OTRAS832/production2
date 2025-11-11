package com.OTRAS.DemoProject.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Religion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long religionId;

    private String religionName;
    private int seats; 

    @ManyToOne
    @JoinColumn(name = "vacancy_details_id")
    private VacancyDetails vacancyDetails;
}

