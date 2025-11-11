package com.OTRAS.DemoProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.GovernmentAdmitCardForm;

@Repository
public interface GovernmentAdmitCardFormRepository extends JpaRepository<GovernmentAdmitCardForm, Long> {

	Optional<GovernmentAdmitCardForm> findByJobCategory(String jobCategory);
}
