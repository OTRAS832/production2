package com.OTRAS.DemoProject.Entity;

import java.time.LocalDateTime;
import java.util.Map;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admit_card_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmitCardConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobPostId;
    private String examDate;
    private String examTime;
    private String instructions;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String extractedCenters;

    private String signatureFilePath;
    private String authorizedSignature;

    @ElementCollection
    @CollectionTable(
            name = "admit_card_config_center_capacities",
            joinColumns = @JoinColumn(name = "admit_card_config_id")
    )
    @MapKeyColumn(name = "center_code")  
    @Column(name = "capacity")            
    private Map<String, Integer> centerCapacities;

    private Boolean admitCardsGenerated = false;
    private LocalDateTime generatedAt;
}
