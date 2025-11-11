package com.OTRAS.DemoProject.Mapper;

import com.OTRAS.DemoProject.DTO.CandidateProfileResponseDTO;
import com.OTRAS.DemoProject.Entity.CandidateProfile;
import com.OTRAS.DemoProject.Entity.PaymentSuccesfullData;

public class CandidateProfileMapper {

    public static CandidateProfileResponseDTO toDTO(CandidateProfile profile,PaymentSuccesfullData data ) {
        CandidateProfileResponseDTO dto = new CandidateProfileResponseDTO();

        dto.setId(profile.getId());
        dto.setOtrasId(profile.getOtrasId());

        if (profile.getCandidate() != null) {
            dto.setCandidateId(profile.getCandidate().getId());
            dto.setCandidateName(profile.getCandidateName());
            dto.setDateOfBirth(profile.getDateOfBirth());
            dto.setGender(profile.getGender());
            dto.setNationality(profile.getNationality());
        }

        // Permanent Address
        dto.setPermanentState(profile.getPermanentState());
        dto.setPermanentDistrict(profile.getPermanentDistrict());
        dto.setPermanentAddress(profile.getPermanentAddress());
        dto.setPermanentPincode(profile.getPermanentPincode());

        // Current Address
        dto.setCurrentState(profile.getCurrentState());
        dto.setCurrentDistrict(profile.getCurrentDistrict());
        dto.setCurrentAddress(profile.getCurrentAddress());
        dto.setCurrentPincode(profile.getCurrentPincode());

        // Matriculation
        dto.setMatriculationEducationBoard(profile.getMatriculationEducationBoard());
        dto.setMatriculationRollNumber(profile.getMatriculationRollNumber());
        dto.setMatriculationYearOfPassing(profile.getMatriculationYearOfPassing());
        dto.setMatriculationSchoolName(profile.getMatriculationSchoolName());
        dto.setMatriculationPercentage(profile.getMatriculationPercentage());
        dto.setMatriculationCgpa(profile.getMatriculationCgpa());

        // Secondary
        dto.setSecondaryEducationBoard(profile.getSecondaryEducationBoard());
        dto.setSecondaryRollNumber(profile.getSecondaryRollNumber());
        dto.setSecondaryYearOfPassing(profile.getSecondaryYearOfPassing());
        dto.setSecondaryCollegeName(profile.getSecondaryCollegeName());
        dto.setSecondaryPercentage(profile.getSecondaryPercentage());
        dto.setSecondaryCgpa(profile.getSecondaryCgpa());

        // Graduation
        dto.setGraduationEducationBoard(profile.getGraduationEducationBoard());
        dto.setGraduationRollNumber(profile.getGraduationRollNumber());
        dto.setGraduationYearOfPassing(profile.getGraduationYearOfPassing());
        dto.setGraduationCollegeName(profile.getGraduationCollegeName());
        dto.setGraduationPercentage(profile.getGraduationPercentage());
        dto.setGraduationCgpa(profile.getGraduationCgpa());

        dto.setUploadedDocuments(profile.getUploadedDocuments());
        
        dto.setLivePhoto(data.getLivePhoto());
        dto.setSignatureFile(data.getSignatureFile());
        dto.setCenters(data.getCenters());

        return dto;
    }
}
