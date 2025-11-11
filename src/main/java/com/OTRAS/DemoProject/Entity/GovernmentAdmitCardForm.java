package com.OTRAS.DemoProject.Entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GovernmentAdmitCardForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long admitCardID;
	private String jobCategory;
	private String authorizedSignature;
	private String uploadSignature;  
	private String uploadAllLocations; 
}
