package com.OTRAS.DemoProject.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.OTRAS.DemoProject.DTO.JobCategoryDTO;
import com.OTRAS.DemoProject.DTO.JobCategoryDTO;
import com.OTRAS.DemoProject.DTO.JobPostDto;
import com.OTRAS.DemoProject.Entity.JobPost;
import com.OTRAS.DemoProject.Entity.Religion;
import com.OTRAS.DemoProject.Entity.VacancyDetails;
import com.OTRAS.DemoProject.Exception.ResourceNotFoundException;
import com.OTRAS.DemoProject.Mapper.JobPostMapper;
import com.OTRAS.DemoProject.Repository.CandidateRepository;
import com.OTRAS.DemoProject.Repository.JobPostRepository;
import com.OTRAS.DemoProject.Repository.ReligionRepository;
import com.OTRAS.DemoProject.Util.FileUploadUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private ReligionRepository religionRepository;

    @Autowired
    private FileUploadUtility fileUploadUtility;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ObjectMapper objectMapper;



    public JobPostDto createJobPost(
            String jobCategory,
            String jobTitle,
            int totalVacancy,
            String description,
            String postDate,
            String lastDate,
            String qualification,
            String gender,
            double fee,
            String importantDates,
            String interviewDates,
            String ageLimit,
            String additionalDetails,
            String examCenters,
            boolean livePhotoRequired,
            boolean signatureRequired,
            boolean declarationRequired,
            List<String> postName,
            List<Integer> total,
            List<Integer> age,
            List<String> religionNameJson,
            List<String> seatsJson,
            String dynamicFieldsJson,
            MultipartFile uploadFile,
            List<String> states,
            String country
    ) {
        try {
            JobPost jobPost = new JobPost();
            jobPost.setJobCategory(jobCategory);
            jobPost.setJobTitle(jobTitle);
            jobPost.setTotalVacancy(totalVacancy);
            jobPost.setDescription(description);
            jobPost.setPostDate(LocalDate.parse(postDate));
            jobPost.setLastDate(LocalDate.parse(lastDate));
            jobPost.setQualification(qualification);
            jobPost.setGender(gender);
            jobPost.setFee(fee);
            jobPost.setImportantDates(importantDates);
            jobPost.setInterviewDates(interviewDates);
            jobPost.setAgeLimit(ageLimit);
            jobPost.setAdditionalDetails(additionalDetails);
            jobPost.setExamCenters(examCenters);
            jobPost.setLivePhotoRequired(livePhotoRequired);
            jobPost.setSignatureRequired(signatureRequired);
            jobPost.setDeclarationRequired(declarationRequired);
            jobPost.setStates(states);
            jobPost.setCountry(country);

            Object dynamicObj = objectMapper.readValue(dynamicFieldsJson, Object.class);
            jobPost.setDynamicFields(objectMapper.writeValueAsString(dynamicObj));

            List<VacancyDetails> vacancyDetailsList = new ArrayList<>();
            for (int i = 0; i < postName.size(); i++) {
                VacancyDetails vd = new VacancyDetails();
                vd.setPostName(postName.get(i));
                vd.setTotal(total.get(i));
                vd.setAge(age.get(i));
                vd.setJobPost(jobPost);

                List<String> relNames = objectMapper.readValue(religionNameJson.get(i), new TypeReference<>() {});
                List<Integer> relSeats = objectMapper.readValue(seatsJson.get(i), new TypeReference<>() {});

                List<Religion> religions = new ArrayList<>();
                for (int j = 0; j < relNames.size(); j++) {
                    Religion r = new Religion();
                    r.setReligionName(relNames.get(j));
                    r.setSeats(relSeats.get(j));
                    r.setVacancyDetails(vd);
                    religions.add(r);
                }
                vd.setReligions(religions);
                vacancyDetailsList.add(vd);
            }
            jobPost.setVacancyDetails(vacancyDetailsList);

            if (uploadFile != null && !uploadFile.isEmpty()) {
                jobPost.setUploadFile(fileUploadUtility.uploadFile(uploadFile, "JobNotifications"));
            }
            
            List<String>tokens=candidateRepository.getAllDeviceTokens();

            JobPost saved = jobPostRepository.save(jobPost);
            
            for(String token:tokens) {
                notificationService.sendNotification(token, "New Job Posted" ,"  :"+jobTitle);
            }
            
            return JobPostMapper.toDto(saved);

        } catch (Exception e) {
            throw new RuntimeException("Error saving job post: " + e.getMessage(), e);
        }
    }





    
    public List<JobPostDto> getAllJobPosts() {
        return jobPostRepository.findAll()
                .stream()
                .map(JobPostMapper::toDto)
                .collect(Collectors.toList());
    }

   
    public JobPostDto getJobPostById(Long id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost not found with ID: " + id));
        return JobPostMapper.toDto(jobPost);
    }

  
    public void deleteJobPost(Long id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost not found with ID: " + id));
        jobPostRepository.delete(jobPost);
    }




	public List<JobPostDto> searchJobs(String state, String country, String jobTitle, String jobCategory,
			String qualification) {

		List<JobPost> posts = jobPostRepository.findAll(); // ✅ load all once

		if (state != null && !state.isBlank()) {
			posts = posts.stream()
					.filter(p -> p.getStates() != null && p.getStates().stream().anyMatch(s -> matches(s, state)))
					.toList();
		}

		if (country != null && !country.isBlank()) {
			posts = posts.stream().filter(p -> matches(p.getCountry(), country)).toList();
		}

		if (jobTitle != null && !jobTitle.isBlank()) {
			posts = posts.stream().filter(p -> matches(p.getJobTitle(), jobTitle)).toList();
		}

		if (jobCategory != null && !jobCategory.isBlank()) {
			posts = posts.stream().filter(p -> matches(p.getJobCategory(), jobCategory)).toList();
		}

		if (qualification != null && !qualification.isBlank()) {
			posts = posts.stream().filter(p -> matches(p.getQualification(), qualification)).toList();
		}

		return JobPostMapper.toDtoList(posts);
	}

	private boolean matches(String fieldValue, String filterValue) {
		if (fieldValue == null || filterValue == null || filterValue.isBlank())
			return true;

		fieldValue = fieldValue.toLowerCase();
		filterValue = filterValue.toLowerCase();

		if (filterValue.startsWith("=")) {
			return fieldValue.equals(filterValue.substring(1));
		}

		if (filterValue.endsWith("*")) {
			return fieldValue.startsWith(filterValue.substring(0, filterValue.length() - 1));
		}

		if (filterValue.startsWith("*")) {
			return fieldValue.endsWith(filterValue.substring(1));
		}

		return fieldValue.contains(filterValue);
	}
	 public List<JobCategoryDTO> getAllCategoriesWithIds() {
	        List<JobCategoryDTO> categories = jobPostRepository.findAllJobCategoriesWithIds();

	        if (categories.isEmpty()) {
	            throw new RuntimeException("No job categories found");
	        }

	        return categories;
	    }






	
}
