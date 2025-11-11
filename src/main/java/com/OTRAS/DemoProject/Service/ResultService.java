package com.OTRAS.DemoProject.Service;
 
import com.OTRAS.DemoProject.DTO.ResultRequestDTO;

import com.OTRAS.DemoProject.Entity.Result;

import com.OTRAS.DemoProject.Repository.ResultRepository;

import com.OTRAS.DemoProject.Util.FileUploadUtility;
 
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;

import java.util.stream.Collectors;
 
@Service

public class ResultService {
 
    private final ResultRepository resultRepository;

    private final FileUploadUtility fileUploadUtility;
 

    private static final String FOLDER_NAME = "result";
 
    public ResultService(ResultRepository resultRepository, FileUploadUtility fileUploadUtility) {

        this.resultRepository = resultRepository;

        this.fileUploadUtility = fileUploadUtility;

    }
 

    public Result uploadResult(ResultRequestDTO dto, MultipartFile file) throws IOException {
 

        boolean exists = resultRepository.existsByJobCategoryAndJobTitle(dto.getJobCategory(), dto.getJobTitle());

        if (exists) {

            throw new IllegalStateException(

                "Result already exists for Job Category: " + dto.getJobCategory() + 

                " and Job Title: " + dto.getJobTitle()

            );

        }
 

        String fileUrl = fileUploadUtility.uploadFile(file, FOLDER_NAME);
 

        Result result = Result.builder()

                .jobCategory(dto.getJobCategory())

                .jobTitle(dto.getJobTitle())

                .releasedDate(dto.getReleasedDate())

                .websiteUrl(dto.getWebsiteUrl())

                .filePath(fileUrl)

                .build();
 
        return resultRepository.save(result);

    }
 

    public List<ResultRequestDTO> getAllResults() {

        return resultRepository.findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }
 

    public Optional<ResultRequestDTO> getResultById(Long id) {

        return resultRepository.findById(id)

                .map(this::convertToDTO);

    }
 

    public void deleteResult(Long id) {

        Result result = resultRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));
 

        fileUploadUtility.deleteFile(result.getFilePath());
 

        resultRepository.delete(result);

    }
 

    private ResultRequestDTO convertToDTO(Result result) {

        return ResultRequestDTO.builder()

                .jobCategory(result.getJobCategory())

                .jobTitle(result.getJobTitle())

                .releasedDate(result.getReleasedDate())

                .websiteUrl(result.getWebsiteUrl())

                .build();

    }

}

 