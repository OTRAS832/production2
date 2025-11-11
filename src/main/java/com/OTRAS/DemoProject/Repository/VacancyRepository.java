package com.OTRAS.DemoProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.VacancyDetails;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyDetails, Long> {

}
