package com.OTRAS.DemoProject.Repository;
 
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.OTRAS.DemoProject.DTO.PqpRequestDTO;

import com.OTRAS.DemoProject.Entity.Pqp;
 
public interface PqpRepository extends JpaRepository<Pqp, Long> {

    Optional<Pqp> findByJobCategoryAndJobTitle(String jobCategory, String jobTitle);

}

 