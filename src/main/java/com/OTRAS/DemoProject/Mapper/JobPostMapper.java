package com.OTRAS.DemoProject.Mapper;

import com.OTRAS.DemoProject.DTO.*;
import com.OTRAS.DemoProject.Entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobPostMapper {

    public static JobPostDto toDto(JobPost jobPost) {
        if (jobPost == null) return null;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> dynamicMap = null;

        try {
            if (jobPost.getDynamicFields() != null)
                dynamicMap = mapper.readValue(jobPost.getDynamicFields(), Map.class);
        } catch (Exception ignored) {}

        return JobPostDto.builder()
                .id(jobPost.getId())
                .jobCategory(jobPost.getJobCategory())
                .jobTitle(jobPost.getJobTitle())
                .totalVacancy(jobPost.getTotalVacancy())
                .description(jobPost.getDescription())
                .postDate(jobPost.getPostDate())
                .lastDate(jobPost.getLastDate())
                .qualification(jobPost.getQualification())
                .vacancyDetails(
                        jobPost.getVacancyDetails().stream().map(v -> VacancyDetailsDto.builder()
                                .id(v.getId())
                                .postName(v.getPostName())
                                .total(v.getTotal())
                                .age(v.getAge())
                                .religions(
                                        v.getReligions().stream()
                                                .map(r -> new ReligionDto(r.getReligionId(), r.getReligionName(), r.getSeats()))
                                                .collect(Collectors.toList())
                                )
                                .build()
                        ).collect(Collectors.toList())
                )
                .gender(jobPost.getGender())
                .fee(jobPost.getFee())
                .importantDates(jobPost.getImportantDates())
                .interviewDates(jobPost.getInterviewDates())
                .ageLimit(jobPost.getAgeLimit())
                .additionalDetails(jobPost.getAdditionalDetails())
                .examCenters(jobPost.getExamCenters())
                .livePhotoRequired(jobPost.isLivePhotoRequired())
                .signatureRequired(jobPost.isSignatureRequired())
                .declarationRequired(jobPost.isDeclarationRequired())
                .dynamicFields(dynamicMap)
                .uploadFile(jobPost.getUploadFile())
                .states(jobPost.getStates())
                .country(jobPost.getCountry())
                .build();
    }
    
   
    
    
    
    
    public static List<JobPostDto> toDtoList(List<JobPost> jobPosts) {
        return jobPosts.stream()
                .map(JobPostMapper::toDto)
                .collect(Collectors.toList());
    }
}
