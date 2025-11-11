package com.OTRAS.DemoProject.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.OTRAS.DemoProject.Entity.AdmitCardConfig;
import com.OTRAS.DemoProject.Repository.AdmitCardConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmitCardConfigService {
   
    private final AdmitCardConfigRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();
   
    public AdmitCardConfig saveConfig(Long jobPostId, List<Map<String, String>> centers, String examDate, String examTime, String instructions, String signaturePath, String authSig) {
        try {
            String centersJson = objectMapper.writeValueAsString(centers);
            Map<String, Integer> caps = centers.stream().collect(
                Collectors.toMap(c -> (String) c.get("centerCode"), c -> Integer.parseInt((String) c.get("capacity"))));
           
            AdmitCardConfig config = AdmitCardConfig.builder()
                .jobPostId(jobPostId)
                .examDate(examDate)
                .examTime(examTime)
                .instructions(instructions)
                .extractedCenters(centersJson)
                .centerCapacities(caps)
                .signatureFilePath(signaturePath)
                .authorizedSignature(authSig)
                .build();
            return repository.save(config);
        } catch (Exception e) {
            throw new RuntimeException("Config save failed", e);
        }
    }
   
    public AdmitCardConfig getByJobPostId(Long jobPostId) {
        return repository.findByJobPostId(jobPostId).orElseThrow(() -> new RuntimeException("Config not found"));
    }
   
    public AdmitCardConfig save(AdmitCardConfig config) {
        return repository.save(config);
    }
}