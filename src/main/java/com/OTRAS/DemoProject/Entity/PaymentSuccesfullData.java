package com.OTRAS.DemoProject.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentSuccesfullData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String otrId;
    private Long jobPostId;
    private Long vacancyId; 
    private String livePhoto;
    private String signatureFile;
    private String paymentStatus;
    private String examRollNo;
    @ElementCollection
    private List<String> centers;

    
    @ManyToOne
    @JoinColumn(name = "candidate_profile_id")
    private CandidateProfile candidateProfile;
}
