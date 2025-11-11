//package com.OTRAS.DemoProject.Controller;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional; 
//import java.util.Random;
//
//import org.apache.coyote.BadRequestException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.OTRAS.DemoProject.DTO.CandidateProfileResponseDTO;
//import com.OTRAS.DemoProject.Entity.Candidate;
//import com.OTRAS.DemoProject.Entity.CandidateProfile;
//import com.OTRAS.DemoProject.Repository.CandidateProfileRepository;
//import com.OTRAS.DemoProject.Repository.CandidateRepository;
//import com.OTRAS.DemoProject.Util.FileUploadUtility;
//
//import jakarta.persistence.EntityNotFoundException;
//
//@RestController
//@RequestMapping("/api/candidate")
//public class CandidateProfileController {
//
//    @Autowired
//    private CandidateRepository candidateRepository;
//
//    @Autowired
//    private CandidateProfileRepository candidateProfileRepository;
//
//    @Autowired
//    private FileUploadUtility fileUploadUtility;
//
//
//    @PostMapping(value = "/profile", consumes = "multipart/form-data")
//    public ResponseEntity<String> createCandidateProfile(
//
//            @RequestParam("candidateId") Long candidateId,
//            @RequestParam("aadharCardNumber") String aadharCardNumber,
//            @RequestParam("identificationCard") String identificationCard,
//            @RequestParam("identificationCardNumber") String identificationCardNumber,
//            @RequestParam("candidateName") String candidateName,
//            @RequestParam("nameChanged") boolean nameChanged,
//            // Use Optional for all required=false fields
//            @RequestParam(value = "changedName", required = false) Optional<String> changedName,
//            @RequestParam("gender") String gender,
//            @RequestParam("dateOfBirth") String dateOfBirth,
//            @RequestParam("fathersName") String fathersName,
//            @RequestParam("mothersName") String mothersName,
//
//            @RequestParam("matriculationEducationBoard") String matriculationEducationBoard,
//            @RequestParam("matriculationRollNumber") String matriculationRollNumber,
//            @RequestParam("matriculationYearOfPassing") Integer matriculationYearOfPassing,
//            @RequestParam("matriculationSchoolName") String matriculationSchoolName,
//            @RequestParam("matriculationPercentage") Double matriculationPercentage,
//            @RequestParam(value = "matriculationCgpa", required = false) Optional<String> matriculationCgpa,
//
//            @RequestParam("secondaryEducationBoard") String secondaryEducationBoard,
//            @RequestParam("secondaryRollNumber") String secondaryRollNumber,
//            @RequestParam("secondaryYearOfPassing") Integer secondaryYearOfPassing,
//            @RequestParam("secondaryCollegeName") String secondaryCollegeName,
//            @RequestParam("secondaryPercentage") Double secondaryPercentage,
//            @RequestParam(value = "secondaryCgpa", required = false) Optional<String> secondaryCgpa,
//
//            @RequestParam(value = "diplomaEducationBoard", required = false) Optional<String> diplomaEducationBoard,
//            @RequestParam(value = "diplomaRollNumber", required = false) Optional<String> diplomaRollNumber,
//            @RequestParam(value = "diplomaYearOfPassing", required = false) Optional<Integer> diplomaYearOfPassing,
//            @RequestParam(value = "diplomaCollegeName", required = false) Optional<String> diplomaCollegeName,
//            @RequestParam(value = "diplomaPercentage", required = false) Optional<Double> diplomaPercentage,
//            @RequestParam(value = "diplomaCgpa", required = false) Optional<String> diplomaCgpa,
//
//            @RequestParam("graduationEducationBoard") String graduationEducationBoard,
//            @RequestParam("graduationRollNumber") String graduationRollNumber,
//            @RequestParam("graduationYearOfPassing") Integer graduationYearOfPassing,
//            @RequestParam("graduationCollegeName") String graduationCollegeName,
//            @RequestParam("graduationPercentage") Double graduationPercentage,
//            @RequestParam(value = "graduationCgpa", required = false) Optional<String> graduationCgpa,
//
//            @RequestParam(value = "postGraduationEducationBoard", required = false) Optional<String> postGraduationEducationBoard,
//            @RequestParam(value = "postGraduationRollNumber", required = false) Optional<String> postGraduationRollNumber,
//            @RequestParam(value = "postGraduationYearOfPassing", required = false) Optional<Integer> postGraduationYearOfPassing,
//            @RequestParam(value = "postGraduationCollegeName", required = false) Optional<String> postGraduationCollegeName,
//            @RequestParam(value = "postGraduationPercentage", required = false) Optional<Double> postGraduationPercentage,
//            @RequestParam(value = "postGraduationCgpa", required = false) Optional<String> postGraduationCgpa,
//
//            @RequestParam(value = "phdEducationBoard", required = false) Optional<String> phdEducationBoard,
//            @RequestParam(value = "phdRollNumber", required = false) Optional<String> phdRollNumber,
//            @RequestParam(value = "phdYearOfPassing", required = false) Optional<Integer> phdYearOfPassing,
//            @RequestParam(value = "phdCollegeName", required = false) Optional<String> phdCollegeName,
//            @RequestParam(value = "phdPercentage", required = false) Optional<Double> phdPercentage,
//            @RequestParam(value = "phdCgpa", required = false) Optional<String> phdCgpa,
//
//            @RequestParam("casteCertificateIssued") boolean casteCertificateIssued,
//            @RequestParam(value = "casteCertificateNumber", required = false) Optional<String> casteCertificateNumber,
//            @RequestParam("nationality") String nationality,
//            @RequestParam("visibleIdentificationMarks") String visibleIdentificationMarks,
//            @RequestParam(value = "typeOfDisability", required = false) Optional<String> typeOfDisability,
//            @RequestParam(value = "disabilityCertificateNumber", required = false) Optional<String> disabilityCertificateNumber,
//
//            @RequestParam("permanentAddress") String permanentAddress,
//            @RequestParam("permanentState") String permanentState,
//            @RequestParam("permanentDistrict") String permanentDistrict,
//            @RequestParam("permanentPincode") String permanentPincode,
//
//            @RequestParam("currentAddressSameAsPermanent") boolean currentAddressSameAsPermanent,
//            @RequestParam(value = "currentAddress", required = false) Optional<String> currentAddress,
//            @RequestParam(value = "currentState", required = false) Optional<String> currentState,
//            @RequestParam(value = "currentDistrict", required = false) Optional<String> currentDistrict,
//            @RequestParam(value = "currentPincode", required = false) Optional<String> currentPincode,
//
//            @RequestParam("documents") MultipartFile[] documents
//    ) throws BadRequestException {
//
//        if (documents.length > 50) {
//            return ResponseEntity.badRequest().body("Max 50 files allowed!");
//        }
//        
//        Candidate candidate = candidateRepository.findById(candidateId)
//                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + candidateId));
//        
//
//        // --- Extract Optional values safely, using .orElse(null) ---
//        String changedNameValue = changedName.orElse(null);
//        String matriculationCgpaValue = matriculationCgpa.orElse(null);
//        String secondaryCgpaValue = secondaryCgpa.orElse(null);
//        
//        String diplomaEducationBoardValue = diplomaEducationBoard.orElse(null);
//        String diplomaRollNumberValue = diplomaRollNumber.orElse(null);
//        Integer diplomaYearOfPassingValue = diplomaYearOfPassing.orElse(null);
//        String diplomaCollegeNameValue = diplomaCollegeName.orElse(null);
//        Double diplomaPercentageValue = diplomaPercentage.orElse(null);
//        String diplomaCgpaValue = diplomaCgpa.orElse(null);
//
//        String graduationCgpaValue = graduationCgpa.orElse(null);
//        
//        String postGraduationEducationBoardValue = postGraduationEducationBoard.orElse(null);
//        String postGraduationRollNumberValue = postGraduationRollNumber.orElse(null);
//        Integer postGraduationYearOfPassingValue = postGraduationYearOfPassing.orElse(null);
//        String postGraduationCollegeNameValue = postGraduationCollegeName.orElse(null);
//        Double postGraduationPercentageValue = postGraduationPercentage.orElse(null);
//        String postGraduationCgpaValue = postGraduationCgpa.orElse(null);
//
//        String phdEducationBoardValue = phdEducationBoard.orElse(null);
//        String phdRollNumberValue = phdRollNumber.orElse(null);
//        Integer phdYearOfPassingValue = phdYearOfPassing.orElse(null);
//        String phdCollegeNameValue = phdCollegeName.orElse(null);
//        Double phdPercentageValue = phdPercentage.orElse(null);
//        String phdCgpaValue = phdCgpa.orElse(null);
//
//        String casteCertificateNumberValue = casteCertificateNumber.orElse(null);
//        String typeOfDisabilityValue = typeOfDisability.orElse(null);
//        String disabilityCertificateNumberValue = disabilityCertificateNumber.orElse(null);
//
//        String currentAddressValue = currentAddress.orElse(null);
//        String currentStateValue = currentState.orElse(null);
//        String currentDistrictValue = currentDistrict.orElse(null);
//        String currentPincodeValue = currentPincode.orElse(null);
//        // -----------------------------------------------------------
//
//        String generatedOtrasId = generateOtrasId(permanentState);
//        CandidateProfile profile = CandidateProfile.builder()
//                .candidate(candidate)
//                .aadharCardNumber(aadharCardNumber)
//                .identificationCard(identificationCard)
//                .identificationCardNumber(identificationCardNumber)
//                .candidateName(candidateName)
//                .nameChanged(nameChanged)
//                // Use the extracted value
//                .changedName(nameChanged ? changedNameValue : null)
//                .gender(gender)
//                .dateOfBirth(LocalDate.parse(dateOfBirth))
//                .fathersName(fathersName)
//                .mothersName(mothersName)
//                .otrasId(generatedOtrasId)
//                .candidate(candidate)          
//                // Education
//                .matriculationEducationBoard(matriculationEducationBoard)
//                .matriculationRollNumber(matriculationRollNumber)
//                .matriculationYearOfPassing(matriculationYearOfPassing)
//                .matriculationSchoolName(matriculationSchoolName)
//                .matriculationPercentage(matriculationPercentage)
//                .matriculationCgpa(matriculationCgpaValue) // Use extracted value
//
//                .secondaryEducationBoard(secondaryEducationBoard)
//                .secondaryRollNumber(secondaryRollNumber)
//                .secondaryYearOfPassing(secondaryYearOfPassing)
//                .secondaryCollegeName(secondaryCollegeName)
//                .secondaryPercentage(secondaryPercentage)
//                .secondaryCgpa(secondaryCgpaValue) // Use extracted value
//
//                .diplomaEducationBoard(diplomaEducationBoardValue)
//                .diplomaRollNumber(diplomaRollNumberValue)
//                .diplomaYearOfPassing(diplomaYearOfPassingValue)
//                .diplomaCollegeName(diplomaCollegeNameValue)
//                .diplomaPercentage(diplomaPercentageValue)
//                .diplomaCgpa(diplomaCgpaValue)
//
//                .graduationEducationBoard(graduationEducationBoard)
//                .graduationRollNumber(graduationRollNumber)
//                .graduationYearOfPassing(graduationYearOfPassing)
//                .graduationCollegeName(graduationCollegeName)
//                .graduationPercentage(graduationPercentage)
//                .graduationCgpa(graduationCgpaValue) // Use extracted value
//
//                .postGraduationEducationBoard(postGraduationEducationBoardValue)
//                .postGraduationRollNumber(postGraduationRollNumberValue)
//                .postGraduationYearOfPassing(postGraduationYearOfPassingValue)
//                .postGraduationCollegeName(postGraduationCollegeNameValue)
//                .postGraduationPercentage(postGraduationPercentageValue)
//                .postGraduationCgpa(postGraduationCgpaValue)
//
//                .phdEducationBoard(phdEducationBoardValue)
//                .phdRollNumber(phdRollNumberValue)
//                .phdYearOfPassing(phdYearOfPassingValue)
//                .phdCollegeName(phdCollegeNameValue)
//                .phdPercentage(phdPercentageValue)
//                .phdCgpa(phdCgpaValue)
//
//                // Caste/Other details
//                .casteCertificateIssued(casteCertificateIssued)
//                .casteCertificateNumber(casteCertificateNumberValue) // Use extracted value
//                .nationality(nationality)
//                .visibleIdentificationMarks(visibleIdentificationMarks)
//                .typeOfDisability(typeOfDisabilityValue) // Use extracted value
//                .disabilityCertificateNumber(disabilityCertificateNumberValue) // Use extracted value
//                // Address
//                .permanentAddress(permanentAddress)
//                .permanentState(permanentState)
//                .permanentDistrict(permanentDistrict)
//                .permanentPincode(permanentPincode)
//                .currentAddressSameAsPermanent(currentAddressSameAsPermanent)
//                // Use extracted values for current address fields
//                .currentAddress(currentAddressSameAsPermanent ? permanentAddress : currentAddressValue)
//                .currentState(currentAddressSameAsPermanent ? permanentState : currentStateValue)
//                .currentDistrict(currentAddressSameAsPermanent ? permanentDistrict : currentDistrictValue)
//                .currentPincode(currentAddressSameAsPermanent ? permanentPincode : currentPincodeValue)
//
//                .build();
//
//
//        List<String> uploadedFilePaths = new ArrayList<>();
//
//        // Handle case where 'documents' might be null or empty if no file was selected
//        if (documents != null) { 
//            for (MultipartFile file : documents) {
//                if (file != null && !file.isEmpty()) {
//                    try {
//                        String uploadedPath = fileUploadUtility.uploadCandidateDocument(
//                                file, candidateId, file.getOriginalFilename()
//                        );
//    
//                        uploadedFilePaths.add(uploadedPath);
//    
//                    } catch (Exception e) {
//                        return ResponseEntity.internalServerError()
//                                .body("Failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
//                    }
//                }
//            }
//        }
//                
//        profile.setUploadedDocuments(uploadedFilePaths);
//        candidateProfileRepository.save(profile);
//
//        return ResponseEntity.ok("Candidate Profile Created. OTRAS ID: " + generatedOtrasId);
//    }
//    
//    String generateOtrasId(String permanentState) {
//
//        // If it contains comma, take first part only
//        String state = permanentState.contains(",")
//                ? permanentState.split(",")[0].trim()
//                : permanentState.trim();
//
//        // Extract first character of each word → Andhra Pradesh -> AP
//        StringBuilder stateCodeBuilder = new StringBuilder();
//        for (String word : state.split(" ")) {
//            if (!word.isEmpty()) {
//                stateCodeBuilder.append(word.charAt(0));
//            }
//        }
//
//        String stateCode = stateCodeBuilder.toString().toUpperCase(); // AP
//
//        // Last 2 digits of current year → 2025 → 25
//        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
//
//        // Random 6 digits
//        int randomSixDigits = new Random().nextInt(900000) + 100000;
//
//        // Final OTRAS ID format: AP25001234
//        return stateCode + year + randomSixDigits;
//    }
//    
//    public CandidateProfileResponseDTO mapToDTO(CandidateProfile profile) {
//
//        CandidateProfileResponseDTO dto = new CandidateProfileResponseDTO();
//
//        dto.setId(profile.getId());
//        dto.setOtrasId(profile.getOtrasId());
//        dto.setCandidateId(profile.getCandidate().getId());
//
//        dto.setCandidateName(profile.getCandidateName());
//        dto.setDateOfBirth(profile.getDateOfBirth());
//        dto.setGender(profile.getGender());
//        dto.setNationality(profile.getNationality());
//
//        // Permanent Address
//        dto.setPermanentState(profile.getPermanentState());
//        dto.setPermanentDistrict(profile.getPermanentDistrict());
//        dto.setPermanentAddress(profile.getPermanentAddress());
//        dto.setPermanentPincode(profile.getPermanentPincode());
//
//        // Current Address
//        dto.setCurrentState(profile.getCurrentState());
//        dto.setCurrentDistrict(profile.getCurrentDistrict());
//        dto.setCurrentAddress(profile.getCurrentAddress());
//        dto.setCurrentPincode(profile.getCurrentPincode());
//
//        // Matriculation
//        dto.setMatriculationEducationBoard(profile.getMatriculationEducationBoard());
//        dto.setMatriculationRollNumber(profile.getMatriculationRollNumber());
//        dto.setMatriculationYearOfPassing(profile.getMatriculationYearOfPassing());
//        dto.setMatriculationSchoolName(profile.getMatriculationSchoolName());
//        dto.setMatriculationPercentage(profile.getMatriculationPercentage());
//        dto.setMatriculationCgpa(profile.getMatriculationCgpa());
//
//        // Secondary (12th)
//        dto.setSecondaryEducationBoard(profile.getSecondaryEducationBoard());
//        dto.setSecondaryRollNumber(profile.getSecondaryRollNumber());
//        dto.setSecondaryYearOfPassing(profile.getSecondaryYearOfPassing());
//        dto.setSecondaryCollegeName(profile.getSecondaryCollegeName());
//        dto.setSecondaryPercentage(profile.getSecondaryPercentage());
//        dto.setSecondaryCgpa(profile.getSecondaryCgpa());
//
//        // Graduation
//        dto.setGraduationEducationBoard(profile.getGraduationEducationBoard());
//        dto.setGraduationRollNumber(profile.getGraduationRollNumber());
//        dto.setGraduationYearOfPassing(profile.getGraduationYearOfPassing());
//        dto.setGraduationCollegeName(profile.getGraduationCollegeName());
//        dto.setGraduationPercentage(profile.getGraduationPercentage());
//        dto.setGraduationCgpa(profile.getGraduationCgpa());
//
//        // Uploaded documents (List<String>)
//        dto.setUploadedDocuments(profile.getUploadedDocuments());
//
//        return dto;
//    }
//
//    @GetMapping("/GetProfileByCandidateId")
//    public ResponseEntity<?> getProfileByCandidateId(@RequestParam Long candidateId) {
//
//        CandidateProfile profile = candidateProfileRepository.findByCandidateId(candidateId);
//
//        if (profile == null) {
//            return ResponseEntity.badRequest().body("No profile found for candidateId: " + candidateId);
//        }
//
//        CandidateProfileResponseDTO response = mapToDTO(profile);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/GetProfileByCandidateOTRAS_ID")
//    public ResponseEntity<?> getProfileByOtrasId(@RequestParam String otrasId) {
//
//        CandidateProfile profile = candidateProfileRepository.findByOtrasId(otrasId);
//
//        if (profile == null) {
//            return ResponseEntity.badRequest().body("No profile found with OTRAS ID: " + otrasId);
//        }
//
//        CandidateProfileResponseDTO response = mapToDTO(profile);
//
//        return ResponseEntity.ok(response);
//    }
//}
// //////////////////////////// Friday============================================================



package com.OTRAS.DemoProject.Controller;
 
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.Optional;

import java.util.Random;
 
import org.apache.coyote.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
 
import com.OTRAS.DemoProject.DTO.CandidateProfileResponseDTO;

import com.OTRAS.DemoProject.DTO.DigiLockerDocumentDTO;

import com.OTRAS.DemoProject.Entity.Candidate;

import com.OTRAS.DemoProject.Entity.CandidateProfile;

import com.OTRAS.DemoProject.Repository.CandidateProfileRepository;

import com.OTRAS.DemoProject.Repository.CandidateRepository;

import com.OTRAS.DemoProject.Service.DigiLockerService;

import com.OTRAS.DemoProject.Util.FileUploadUtility;
 
import jakarta.persistence.EntityNotFoundException;
 
@RestController

@RequestMapping("/api/candidate")

public class CandidateProfileController {
 
    @Autowired

    private CandidateRepository candidateRepository;
 
    @Autowired

    private CandidateProfileRepository candidateProfileRepository;
 
    @Autowired

    private FileUploadUtility fileUploadUtility;
 
    @Autowired

    private DigiLockerService digiLockerService;
 
    // Health check endpoint

    @GetMapping("/health")

    public ResponseEntity<?> healthCheck() {

        return ResponseEntity.ok(Map.of(

                "status", "UP",

                "service", "Candidate Profile API",

                "timestamp", System.currentTimeMillis()

        ));

    }
 

    @PostMapping(value = "/register", consumes = "multipart/form-data")

    public ResponseEntity<?> registerCandidate(

            @RequestParam("candidateId") Long candidateId,

            @RequestParam(value= "registrationType" , required = false) String registrationType, // "MANUAL" or "DIGILOCKER"

            @RequestParam("aadharCardNumber") String aadharCardNumber,

            @RequestParam("identificationCard") String identificationCard,

            @RequestParam("identificationCardNumber") String identificationCardNumber,

            @RequestParam("candidateName") String candidateName,

            @RequestParam("nameChanged") boolean nameChanged,

            @RequestParam(value = "changedName", required = false) String changedName,

            @RequestParam("gender") String gender,

            @RequestParam("dateOfBirth") String dateOfBirth,

            @RequestParam("fathersName") String fathersName,

            @RequestParam("mothersName") String mothersName,
 

            @RequestParam(value = "matriculationEducationBoard", required = false) String matriculationEducationBoard,
            
            @RequestParam(value ="matriculationRollNumber", required = false) String matriculationRollNumber,
 
            @RequestParam(value = "matriculationYearOfPassing", required = false) Integer matriculationYearOfPassing,
 
            @RequestParam(value = "matriculationSchoolName", required = false) String matriculationSchoolName,
 
            @RequestParam(value = "matriculationPercentage", required = false) Double matriculationPercentage,
 
            @RequestParam(value = "matriculationCgpa", required = false) String matriculationCgpa,
 
            @RequestParam(value = "secondaryEducationBoard", required = false) String secondaryEducationBoard,
 
            @RequestParam(value = "secondaryRollNumber", required = false) String secondaryRollNumber,
 
            @RequestParam(value = "secondaryYearOfPassing", required = false) Integer secondaryYearOfPassing,
 
            @RequestParam(value = "secondaryCollegeName", required = false) String secondaryCollegeName,
 
            @RequestParam(value = "secondaryPercentage",required = false) Double secondaryPercentage,
 
            @RequestParam(value = "secondaryCgpa", required = false) String secondaryCgpa,
 
            @RequestParam(value = "diplomaEducationBoard", required = false) String diplomaEducationBoard,
 
            @RequestParam(value = "diplomaRollNumber", required = false) String diplomaRollNumber,
 
            @RequestParam(value = "diplomaYearOfPassing", required = false) Integer diplomaYearOfPassing,
 
            @RequestParam(value = "diplomaCollegeName", required = false) String diplomaCollegeName,
 
            @RequestParam(value = "diplomaPercentage", required = false) Double diplomaPercentage,
 
            @RequestParam(value = "diplomaCgpa", required = false) String diplomaCgpa,
 
            @RequestParam(value = "graduationEducationBoard", required = false) String graduationEducationBoard,
 
            @RequestParam(value = "graduationRollNumber", required = false) String graduationRollNumber,
 
            @RequestParam(value = "graduationYearOfPassing", required = false) Integer graduationYearOfPassing,
 
            @RequestParam(value = "graduationCollegeName", required = false) String graduationCollegeName,
 
            @RequestParam(value = "graduationPercentage", required = false) Double graduationPercentage,
 
            @RequestParam(value = "graduationCgpa", required = false) String graduationCgpa,
 
            @RequestParam(value = "postGraduationEducationBoard", required = false) String postGraduationEducationBoard,
 
            @RequestParam(value = "postGraduationRollNumber", required = false) String postGraduationRollNumber,
 
            @RequestParam(value = "postGraduationYearOfPassing", required = false) Integer postGraduationYearOfPassing,
 
            @RequestParam(value = "postGraduationCollegeName", required = false) String postGraduationCollegeName,
 
            @RequestParam(value = "postGraduationPercentage", required = false) Double postGraduationPercentage,
 
            @RequestParam(value = "postGraduationCgpa", required = false) String postGraduationCgpa,
 
            @RequestParam(value = "phdEducationBoard", required = false) String phdEducationBoard,
 
            @RequestParam(value = "phdRollNumber", required = false) String phdRollNumber,
 
            @RequestParam(value = "phdYearOfPassing", required = false) Integer phdYearOfPassing,
 
            @RequestParam(value = "phdCollegeName", required = false) String phdCollegeName,
 
            @RequestParam(value = "phdPercentage", required = false) Double phdPercentage,
 
            @RequestParam(value = "phdCgpa", required = false) String phdCgpa,
 
            @RequestParam("casteCertificateIssued") boolean casteCertificateIssued,
 
            @RequestParam(value = "casteCertificateNumber", required = false) String casteCertificateNumber,
 
            @RequestParam("nationality") String nationality,
 
            @RequestParam("visibleIdentificationMarks") String visibleIdentificationMarks,
 
            @RequestParam(value = "typeOfDisability", required = false) String typeOfDisability,
 
            @RequestParam(value = "disabilityCertificateNumber", required = false) String disabilityCertificateNumber,
 
            @RequestParam("permanentAddress") String permanentAddress,
 
            @RequestParam("permanentState") String permanentState,
 
            @RequestParam("permanentDistrict") String permanentDistrict,
 
            @RequestParam("permanentPincode") String permanentPincode,
 
            @RequestParam("currentAddressSameAsPermanent") boolean currentAddressSameAsPermanent,
 
            @RequestParam(value = "currentAddress", required = false) String currentAddress,
 
            @RequestParam(value = "currentState", required = false) String currentState,
 
            @RequestParam(value = "currentDistrict", required = false) String currentDistrict,
 
            @RequestParam(value = "currentPincode", required = false) String currentPincode,
 
            @RequestParam(value = "documents", required = false) MultipartFile[] documents
 
 

    ) throws BadRequestException {
 
        System.out.println(".....Debuging gfor Registration......");

        System.out.println("Received registrationType: '" + registrationType + "'");

        System.out.println("Trimmed registrationType: '" + (registrationType != null ? registrationType.trim() : "null") + "'");

        System.out.println("Is DIGILOCKER: " + "DIGILOCKER".equalsIgnoreCase(registrationType != null ? registrationType.trim() : ""));

        System.out.println("Is MANUAL: " + "MANUAL".equalsIgnoreCase(registrationType != null ? registrationType.trim() : ""));

        System.out.println("Documents received: " + (documents != null ? documents.length : 0));

        System.out.println("Permanent State: '" + permanentState + "'");

        System.out.println(".................................");
 
        Candidate candidate = candidateRepository.findById(candidateId)

                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + candidateId));
 
        Map<String, Object> response = new HashMap<>();

        boolean profileExists = candidateProfileRepository.findByCandidate(candidate).isPresent();
        if (profileExists) {
            return ResponseEntity.badRequest()
                    .body("Profile already exists for candidate ID: " + candidateId);
        }
 
        String normalizedType = registrationType != null ? registrationType.trim().toUpperCase() : "";
        
        if ("DIGILOCKER".equals(normalizedType)) {
            String generatedOtrasId = generateOtrasId(permanentState);
            
            CandidateProfile profile = buildCandidateProfile(
                    candidate, generatedOtrasId,
                    aadharCardNumber, identificationCard, identificationCardNumber,
                    candidateName, nameChanged, changedName, gender, dateOfBirth, fathersName, mothersName,
                    matriculationEducationBoard, matriculationRollNumber, matriculationYearOfPassing,
                    matriculationSchoolName, matriculationPercentage, matriculationCgpa,
                    secondaryEducationBoard, secondaryRollNumber, secondaryYearOfPassing,
                    secondaryCollegeName, secondaryPercentage, secondaryCgpa,
                    diplomaEducationBoard, diplomaRollNumber, diplomaYearOfPassing,
                    diplomaCollegeName, diplomaPercentage, diplomaCgpa,
                    graduationEducationBoard, graduationRollNumber, graduationYearOfPassing,
                    graduationCollegeName, graduationPercentage, graduationCgpa,
                    postGraduationEducationBoard, postGraduationRollNumber, postGraduationYearOfPassing,
                    postGraduationCollegeName, postGraduationPercentage, postGraduationCgpa,
                    phdEducationBoard, phdRollNumber, phdYearOfPassing,
                    phdCollegeName, phdPercentage, phdCgpa,
                    casteCertificateIssued, casteCertificateNumber, nationality,
                    visibleIdentificationMarks, typeOfDisability, disabilityCertificateNumber,
                    permanentAddress, permanentState, permanentDistrict, permanentPincode,
                    currentAddressSameAsPermanent, currentAddress, currentState, currentDistrict, currentPincode
            );
 
            profile.setDigilockerVerified(false);
            profile.setRegistrationStatus("PENDING_DOCUMENTS");
            
            candidateProfileRepository.save(profile);
 
            String aadhaarVerificationUrl = digiLockerService.generateAadhaarVerificationUrl(
                    candidateId, candidateName, aadharCardNumber);
            
            response.put("status", "AADHAAR_VERIFICATION_REQUIRED");
            response.put("message", "Please complete Aadhaar verification to access DigiLocker");
            response.put("redirectUrl", aadhaarVerificationUrl);
            response.put("candidateId", candidateId);
//            response.put("otrasId", generatedOtrasId); 
            
        } else if ("MANUAL".equals(normalizedType)) {
            if (documents == null || documents.length == 0) {
                return ResponseEntity.badRequest().body("Documents are required for manual registration");
            }
 
            if (documents.length > 50) {
                return ResponseEntity.badRequest().body("Max 50 files allowed!");
            }
 
            for (MultipartFile file : documents) {
                if (file != null && !file.isEmpty()) {
                    long fileSizeInMB = file.getSize() / (1024 * 1024);
                    if (fileSizeInMB > 100) {
                        return ResponseEntity.badRequest().body(
                            "File '" + file.getOriginalFilename() + "' is too large! " +
                            "Size: " + fileSizeInMB + "MB. Max allowed: 100MB"
                        );
                    }
                }
            }
            
            String generatedOtrasId = generateOtrasId(permanentState);
 
            CandidateProfile profile = buildCandidateProfile(
                    candidate, generatedOtrasId,
                    aadharCardNumber, identificationCard, identificationCardNumber,
                    candidateName, nameChanged, changedName, gender, dateOfBirth, fathersName, mothersName,
                    matriculationEducationBoard, matriculationRollNumber, matriculationYearOfPassing,
                    matriculationSchoolName, matriculationPercentage, matriculationCgpa,
                    secondaryEducationBoard, secondaryRollNumber, secondaryYearOfPassing,
                    secondaryCollegeName, secondaryPercentage, secondaryCgpa,
                    diplomaEducationBoard, diplomaRollNumber, diplomaYearOfPassing,
                    diplomaCollegeName, diplomaPercentage, diplomaCgpa,
                    graduationEducationBoard, graduationRollNumber, graduationYearOfPassing,
                    graduationCollegeName, graduationPercentage, graduationCgpa,
                    postGraduationEducationBoard, postGraduationRollNumber, postGraduationYearOfPassing,
                    postGraduationCollegeName, postGraduationPercentage, postGraduationCgpa,
                    phdEducationBoard, phdRollNumber, phdYearOfPassing,
                    phdCollegeName, phdPercentage, phdCgpa,
                    casteCertificateIssued, casteCertificateNumber, nationality,
                    visibleIdentificationMarks, typeOfDisability, disabilityCertificateNumber,
                    permanentAddress, permanentState, permanentDistrict, permanentPincode,
                    currentAddressSameAsPermanent, currentAddress, currentState, currentDistrict, currentPincode
            );
 
            List<String> uploadedFilePaths = new ArrayList<>();
            for (MultipartFile file : documents) {
                if (file != null && !file.isEmpty()) {
                    try {
                        String uploadedPath = fileUploadUtility.uploadCandidateDocument(
                                file, candidateId, file.getOriginalFilename()
                        );
                        uploadedFilePaths.add(uploadedPath);
                        System.out.println("Successfully uploaded: " + uploadedPath); // DEBUG
                    } catch (Exception e) {
                        System.out.println("Failed to upload: " + file.getOriginalFilename() + " - " + e.getMessage()); // DEBUG
                        return ResponseEntity.internalServerError()
                                .body("Failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
                    }
                }
            }
 
            if (uploadedFilePaths.isEmpty()) {
                return ResponseEntity.badRequest().body("No documents were successfully uploaded");
            }
 
            profile.setUploadedDocuments(uploadedFilePaths);
            profile.setDigilockerVerified(false);
            
            CandidateProfile savedProfile = candidateProfileRepository.save(profile);
            System.out.println("Profile saved with ID: " + savedProfile.getId());
            System.out.println("Documents saved: " + savedProfile.getUploadedDocuments());
            response.put("status", "SUCCESS");
            response.put("message", "Candidate Profile Created Successfully");
            response.put("otrasId", generatedOtrasId);
            response.put("registrationType", "MANUAL");
            response.put("uploadedDocumentsCount", uploadedFilePaths.size());
        } else {
            return ResponseEntity.badRequest().body("Invalid registrationType: '" + registrationType + "'. Use 'MANUAL' or 'DIGILOCKER'");
        }
 
        return ResponseEntity.ok(response);
    }
 
    @PostMapping("/digilocker/callback")
    public ResponseEntity<?> handleDigiLockerCallback(@RequestBody DigiLockerDocumentDTO digiLockerDocuments) {
        try {
            System.out.println("=== DIGILOCKER CALLBACK DEBUG ===");
            System.out.println("Received DTO: " + digiLockerDocuments);
            
            Long candidateId = digiLockerDocuments.getCandidateId();
            
            if (candidateId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "ERROR", "message", "Candidate ID is required"));
            }
 
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + candidateId));
 
            CandidateProfile profile = candidateProfileRepository.findByCandidate(candidate)
                    .orElseThrow(() -> new RuntimeException("Profile not found for candidate: " + candidateId + ". Please complete registration first."));
 
            System.out.println("Found existing profile with OTRAS ID: " + profile.getOtrasId());
            System.out.println("Permanent State in profile: " + profile.getPermanentState());
 
            List<String> documentList = new ArrayList<>();
            if (digiLockerDocuments.getTenthCertificate() != null)
                documentList.add(digiLockerDocuments.getTenthCertificate());
            if (digiLockerDocuments.getIntermediateCertificate() != null)
                documentList.add(digiLockerDocuments.getIntermediateCertificate());
            if (digiLockerDocuments.getDegreeCertificate() != null)
                documentList.add(digiLockerDocuments.getDegreeCertificate());
            if (digiLockerDocuments.getBtechCertificate() != null)
                documentList.add(digiLockerDocuments.getBtechCertificate());
            if (digiLockerDocuments.getDiplomaCertificate() != null)
                documentList.add(digiLockerDocuments.getDiplomaCertificate());
            if (digiLockerDocuments.getPostGraduationCeritifcate() != null)
                documentList.add(digiLockerDocuments.getPostGraduationCeritifcate());
 
            profile.setUploadedDocuments(documentList);
            profile.setDigilockerVerified(true);
            profile.setRegistrationStatus("COMPLETED");
            
            candidateProfileRepository.save(profile);
 
            System.out.println("Profile updated successfully with DigiLocker documents");
 
            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Documents verified using DigiLocker and profile completed successfully");
            response.put("otrasId", profile.getOtrasId()); // Use existing OTRAS ID
            response.put("registrationType", "DIGILOCKER");
 
            return ResponseEntity.ok(response);
 
        } catch (Exception e) {
            System.out.println("ERROR in callback: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }
 
    @GetMapping("/{candidateId}/profile")
    public ResponseEntity<?> getCandidateProfileForDigiLocker(@PathVariable Long candidateId) {
        try {
            System.out.println("=== DIGILOCKER PROFILE REQUEST ===");
            System.out.println("Candidate ID: " + candidateId);
            
            Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + candidateId));
 
            Optional<CandidateProfile> profileOpt = candidateProfileRepository.findByCandidate(candidate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("candidateId", candidate.getId());
            response.put("fullName", candidate.getFullName()); // Make sure Candidate entity has this field
            
            if (profileOpt.isPresent()) {
                CandidateProfile profile = profileOpt.get();
                response.put("aadharNumber", profile.getAadharCardNumber());
                response.put("dateOfBirth", profile.getDateOfBirth());
                response.put("profileExists", true);
            } else {
                response.put("aadharNumber", "Not available");
                response.put("dateOfBirth", "Not available");
                response.put("profileExists", false);
            }
            
            System.out.println("Sending profile data: " + response);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR in profile endpoint: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to load candidate data",
                "message", e.getMessage()
            ));
        }
    }
    private CandidateProfile buildCandidateProfile(Candidate candidate, String otrasId,
            String aadharCardNumber, String identificationCard, String identificationCardNumber,
            String candidateName, boolean nameChanged, String changedName, String gender,
            String dateOfBirth, String fathersName, String mothersName,
            String matriculationEducationBoard, String matriculationRollNumber, Integer matriculationYearOfPassing,
            String matriculationSchoolName, Double matriculationPercentage, String matriculationCgpa,
            String secondaryEducationBoard, String secondaryRollNumber, Integer secondaryYearOfPassing,
            String secondaryCollegeName, Double secondaryPercentage, String secondaryCgpa,
            String diplomaEducationBoard, String diplomaRollNumber, Integer diplomaYearOfPassing,
            String diplomaCollegeName, Double diplomaPercentage, String diplomaCgpa,
            String graduationEducationBoard, String graduationRollNumber, Integer graduationYearOfPassing,
            String graduationCollegeName, Double graduationPercentage, String graduationCgpa,
            String postGraduationEducationBoard, String postGraduationRollNumber, Integer postGraduationYearOfPassing,
            String postGraduationCollegeName, Double postGraduationPercentage, String postGraduationCgpa,
            String phdEducationBoard, String phdRollNumber, Integer phdYearOfPassing,
            String phdCollegeName, Double phdPercentage, String phdCgpa,
            boolean casteCertificateIssued, String casteCertificateNumber, String nationality,
            String visibleIdentificationMarks, String typeOfDisability, String disabilityCertificateNumber,
            String permanentAddress, String permanentState, String permanentDistrict, String permanentPincode,
            boolean currentAddressSameAsPermanent, String currentAddress, String currentState,
            String currentDistrict, String currentPincode) {
 
        LocalDate parsedDateOfBirth;
        try {
            if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
                parsedDateOfBirth = LocalDate.parse(dateOfBirth.trim());
            } else {
                throw new IllegalArgumentException("Date of birth is required");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format for dateOfBirth: '" + dateOfBirth + "'. Expected format: yyyy-MM-dd");
        }
 
        return CandidateProfile.builder()
                .candidate(candidate)
                .otrasId(otrasId)
                .aadharCardNumber(aadharCardNumber)
                .identificationCard(identificationCard)
                .identificationCardNumber(identificationCardNumber)
                .candidateName(candidateName)
                .nameChanged(nameChanged)
                .changedName(nameChanged ? changedName : null)
                .gender(gender)
                .dateOfBirth(parsedDateOfBirth)
                .fathersName(fathersName)
                .mothersName(mothersName)
                .matriculationEducationBoard(matriculationEducationBoard)
                .matriculationRollNumber(matriculationRollNumber)
                .matriculationYearOfPassing(matriculationYearOfPassing)
                .matriculationSchoolName(matriculationSchoolName)
                .matriculationPercentage(matriculationPercentage)
                .matriculationCgpa(matriculationCgpa)
                .secondaryEducationBoard(secondaryEducationBoard)
                .secondaryRollNumber(secondaryRollNumber)
                .secondaryYearOfPassing(secondaryYearOfPassing)
                .secondaryCollegeName(secondaryCollegeName)
                .secondaryPercentage(secondaryPercentage)
                .secondaryCgpa(secondaryCgpa)
                .diplomaEducationBoard(diplomaEducationBoard)
                .diplomaRollNumber(diplomaRollNumber)
                .diplomaYearOfPassing(diplomaYearOfPassing)
                .diplomaCollegeName(diplomaCollegeName)
                .diplomaPercentage(diplomaPercentage)
                .diplomaCgpa(diplomaCgpa)
                .graduationEducationBoard(graduationEducationBoard)
                .graduationRollNumber(graduationRollNumber)
                .graduationYearOfPassing(graduationYearOfPassing)
                .graduationCollegeName(graduationCollegeName)
                .graduationPercentage(graduationPercentage)
                .graduationCgpa(graduationCgpa)
                .postGraduationEducationBoard(postGraduationEducationBoard)
                .postGraduationRollNumber(postGraduationRollNumber)
                .postGraduationYearOfPassing(postGraduationYearOfPassing)
                .postGraduationCollegeName(postGraduationCollegeName)
                .postGraduationPercentage(postGraduationPercentage)
                .postGraduationCgpa(postGraduationCgpa)
                .phdEducationBoard(phdEducationBoard)
                .phdRollNumber(phdRollNumber)
                .phdYearOfPassing(phdYearOfPassing)
                .phdCollegeName(phdCollegeName)
                .phdPercentage(phdPercentage)
                .phdCgpa(phdCgpa)
                .casteCertificateIssued(casteCertificateIssued)
                .casteCertificateNumber(casteCertificateNumber)
                .nationality(nationality)
                .visibleIdentificationMarks(visibleIdentificationMarks)
                .typeOfDisability(typeOfDisability)
                .disabilityCertificateNumber(disabilityCertificateNumber)
                .permanentAddress(permanentAddress)
                .permanentState(permanentState)
                .permanentDistrict(permanentDistrict)
                .permanentPincode(permanentPincode)
                .currentAddressSameAsPermanent(currentAddressSameAsPermanent)
                .currentAddress(currentAddressSameAsPermanent ? permanentAddress : currentAddress)
                .currentState(currentAddressSameAsPermanent ? permanentState : currentState)
                .currentDistrict(currentAddressSameAsPermanent ? permanentDistrict : currentDistrict)
                .currentPincode(currentAddressSameAsPermanent ? permanentPincode : currentPincode)
                .digilockerVerified(false)
                .build();
    }
 
    String generateOtrasId(String permanentState) {
        if (permanentState == null || permanentState.trim().isEmpty()) {
            System.out.println("WARNING: permanentState is null or empty, using default 'IN'");
            permanentState = "India"; // Default value
        }
 
        String state = permanentState.contains(",")
                ? permanentState.split(",")[0].trim()
                : permanentState.trim();
 
        StringBuilder stateCodeBuilder = new StringBuilder();
        for (String word : state.split(" ")) {
            if (!word.isEmpty()) {
                stateCodeBuilder.append(word.charAt(0));
            }
        }
 
        String stateCode = stateCodeBuilder.toString().toUpperCase();
        if (stateCode.length() < 2) {
            stateCode = "IN"; // Default India code
        }
 
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        int randomSixDigits = new Random().nextInt(900000) + 100000;
 
        String otrasId = stateCode + year + randomSixDigits;
        System.out.println("Generated OTRAS ID: " + otrasId + " for state: " + permanentState);
        return otrasId;
    }
 
    @GetMapping("/GetProfileByCandidateId")
    public ResponseEntity<?> getProfileByCandidateId(@RequestParam Long candidateId) {
        CandidateProfile profile = candidateProfileRepository.findByCandidateId(candidateId);
        if (profile == null) {
            return ResponseEntity.badRequest().body("No profile found for candidateId: " + candidateId);
        }
        CandidateProfileResponseDTO response = mapToDTO(profile);
        return ResponseEntity.ok(response);
    }
 
    @GetMapping("/GetProfileByCandidateOTRAS_ID")
    public ResponseEntity<?> getProfileByOtrasId(@RequestParam String otrasId) {
        CandidateProfile profile = candidateProfileRepository.findByOtrasId(otrasId);
        if (profile == null) {
            return ResponseEntity.badRequest().body("No profile found with OTRAS ID: " + otrasId);
        }
        CandidateProfileResponseDTO response = mapToDTO(profile);
        return ResponseEntity.ok(response);
    }
 
    public CandidateProfileResponseDTO mapToDTO(CandidateProfile profile) {
        CandidateProfileResponseDTO dto = new CandidateProfileResponseDTO();
        
        dto.setId(profile.getId());
        dto.setOtrasId(profile.getOtrasId());
        dto.setCandidateId(profile.getCandidate().getId());
        dto.setCandidateName(profile.getCandidateName());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setGender(profile.getGender());
        dto.setNationality(profile.getNationality());
        dto.setPermanentState(profile.getPermanentState());
        dto.setPermanentDistrict(profile.getPermanentDistrict());
        dto.setPermanentAddress(profile.getPermanentAddress());
        dto.setPermanentPincode(profile.getPermanentPincode());
        dto.setCurrentState(profile.getCurrentState());
        dto.setCurrentDistrict(profile.getCurrentDistrict());
        dto.setCurrentAddress(profile.getCurrentAddress());
        dto.setCurrentPincode(profile.getCurrentPincode());
        dto.setMatriculationEducationBoard(profile.getMatriculationEducationBoard());
        dto.setMatriculationRollNumber(profile.getMatriculationRollNumber());
        dto.setMatriculationYearOfPassing(profile.getMatriculationYearOfPassing());
        dto.setMatriculationSchoolName(profile.getMatriculationSchoolName());
        dto.setMatriculationPercentage(profile.getMatriculationPercentage());
        dto.setMatriculationCgpa(profile.getMatriculationCgpa());
        dto.setSecondaryEducationBoard(profile.getSecondaryEducationBoard());
        dto.setSecondaryRollNumber(profile.getSecondaryRollNumber());
        dto.setSecondaryYearOfPassing(profile.getSecondaryYearOfPassing());
        dto.setSecondaryCollegeName(profile.getSecondaryCollegeName());
        dto.setSecondaryPercentage(profile.getSecondaryPercentage());
        dto.setSecondaryCgpa(profile.getSecondaryCgpa());
        dto.setGraduationEducationBoard(profile.getGraduationEducationBoard());
        dto.setGraduationRollNumber(profile.getGraduationRollNumber());
        dto.setGraduationYearOfPassing(profile.getGraduationYearOfPassing());
        dto.setGraduationCollegeName(profile.getGraduationCollegeName());
        dto.setGraduationPercentage(profile.getGraduationPercentage());
        dto.setGraduationCgpa(profile.getGraduationCgpa());
        dto.setUploadedDocuments(profile.getUploadedDocuments());
        
        return dto;
    }
}
 

