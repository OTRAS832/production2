package com.OTRAS.DemoProject.Entity;
 
import java.time.LocalDate;

import java.time.LocalDateTime;
 
import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;

import jakarta.persistence.PrePersist;

import jakarta.persistence.Table;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;
 
@Entity

@Table(name = "candidates")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Candidate {
 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;


	 @Column(nullable = false)

	 private String fullName;

	 @Column(nullable = false)

	 private LocalDate dateOfBirth;

	 @Column(length = 1, nullable = false)

	 private String gender;

	 @Column(nullable = false)

	 private String fatherName;

	 @Column(nullable = false)

	 private String motherName;

	 @Column(nullable = false)

	 private String nationality = "Indian";

	 @Column(unique = true, nullable = false)

	 private String email;

	 @Column(name = "email_verified")

	 private boolean emailVerified = false;

	 @Column(unique = true, nullable = false)

	 private String mobile;

	 @Column(name = "mobile_verified")

	 private boolean mobileVerified = false;

	 @Column(nullable = false)

	 private String qualification;

	 @Column(nullable = false)

	 private String interest;

	 @Column(nullable = false)

	 private String password;

	 @Column(nullable = false)

	 private boolean termsAccepted = false;

	 @Column(updatable = false)

	 private LocalDateTime createdAt;

	 @Column(name = "last_login")

	 private LocalDateTime lastLogin;


//	 @Column(name = "otras_id", length = 30, unique = true, nullable = true)

//	 private String otrasId;

//	 

	 @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

	    private CandidateProfile profile;
	 
	 private String fcmDeviceToken;

	 @PrePersist

	 protected void onCreate() {

	     this.createdAt = LocalDateTime.now();

	 }

}
 