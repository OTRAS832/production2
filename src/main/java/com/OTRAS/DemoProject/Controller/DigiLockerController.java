package com.OTRAS.DemoProject.Controller;
 
import java.util.Map;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;

import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
 
import com.OTRAS.DemoProject.Entity.Candidate;

import com.OTRAS.DemoProject.Entity.CandidateProfile;

import com.OTRAS.DemoProject.Repository.CandidateProfileRepository;

import com.OTRAS.DemoProject.Repository.CandidateRepository;

import com.OTRAS.DemoProject.Service.DigiLockerService;
 
@RestController

@RequestMapping("/api/digilocker")

public class DigiLockerController {
 
    @Autowired

    private DigiLockerService digiLockerService;
 
    @Autowired

    private CandidateRepository candidateRepository;
 

    @PostMapping("/send-otp/{aadhaarNumber}")

    public ResponseEntity<?> sendOtp(@PathVariable String aadhaarNumber) {

        System.out.println("Debuging for Aadhaar, For OTP has to send registration number");

        System.out.println("Raw aadhaarNumber: '" + aadhaarNumber + "'");

        System.out.println("Length: " + aadhaarNumber.length());

        System.out.println("Characters: ");

        for (int i = 0; i < aadhaarNumber.length(); i++) {

            System.out.println("  [" + i + "]: '" + aadhaarNumber.charAt(i) + "' -> " + (int) aadhaarNumber.charAt(i));

        }


        String cleanAadhaar = aadhaarNumber.replaceAll("\\D", "");

        System.out.println("Clean aadhaar: '" + cleanAadhaar + "'");

        System.out.println("Clean length: " + cleanAadhaar.length());

        System.out.println(".......................");

        if (cleanAadhaar.length() != 12) {

            return ResponseEntity.badRequest().body(

                Map.of("error", "Invalid Aadhaar number. Must be 12 digits.", 

                       "received", aadhaarNumber,

                       "clean", cleanAadhaar,

                       "length", cleanAadhaar.length())

            );

        }
        return digiLockerService.sendAadhaarOtp(cleanAadhaar);

    }
 

    @PostMapping("/verify-otp")

    public ResponseEntity<?> verifyOtp(

            @RequestParam String txnId, 

            @RequestParam String otp,

            @RequestParam Long candidateId) {


        if (candidateRepository == null) {

            return ResponseEntity.internalServerError().body(

                Map.of("error", "Candidate repository not available")

            );

        }

        ResponseEntity<?> uidaiResponse = digiLockerService.verifyAadhaarOtp(txnId, otp);


        if (uidaiResponse.getStatusCode().is2xxSuccessful()) {

            try {


                String digilockerUrl = "http://localhost:8068/api/digilocker/digilocker-page?candidateId=" + candidateId;

                System.out.println("Generated DigiLocker URL: " + digilockerUrl);

                return ResponseEntity.ok(Map.of(

                        "uidaiStatus", "VERIFIED",

                        "message", "Aadhaar verified successfully",

                        "redirectToDigiLocker", digilockerUrl,

                        "candidateId", candidateId

                ));

            } catch (Exception e) {

                return ResponseEntity.internalServerError().body(

                    Map.of("error", "Failed to generate DigiLocker URL: " + e.getMessage())

                );

            }

        } else {

            return uidaiResponse;

        }

    }

    @Autowired

    private CandidateProfileRepository candidateProfileRepository;

    @GetMapping("/digilocker-page")

    public ResponseEntity<Resource> serveDigiLockerPage(@RequestParam Long candidateId) {

        try {

            System.out.println("=== DIGILOCKER PAGE REQUEST ===");

            System.out.println("Candidate ID: " + candidateId);


            Candidate candidate = candidateRepository.findById(candidateId)

                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));


            Optional<CandidateProfile> profileOpt = candidateProfileRepository.findByCandidate(candidate);

            if (profileOpt.isPresent()) {

                CandidateProfile profile = profileOpt.get();

                System.out.println("Profile found for candidate: " + profile.getCandidateName());

            } else {

                System.out.println("No profile found yet for candidate, this is normal for DigiLocker flow");

            }


            Resource resource = new ClassPathResource("static/mock-digilocker.html");

            if (!resource.exists()) {

                System.out.println("HTML resource not found!");

                return ResponseEntity.notFound().build();

            }

            System.out.println("Serving DigiLocker page successfully");

            return ResponseEntity.ok()

                    .contentType(MediaType.TEXT_HTML)

                    .body(resource);

        } catch (Exception e) {

            System.out.println("ERROR serving DigiLocker page: " + e.getMessage());

            e.printStackTrace();

            return ResponseEntity.notFound().build();

        }

    }
 
}
 