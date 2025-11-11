package com.OTRAS.DemoProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.OTRAS.DemoProject.Entity.Religion;

@Repository
public interface ReligionRepository extends JpaRepository<Religion, Long> {

}
