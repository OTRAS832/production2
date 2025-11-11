package com.OTRAS.DemoProject.Service;
 
import com.OTRAS.DemoProject.DTO.AnswerKeyRequestDTO;

import com.OTRAS.DemoProject.Entity.AnswerKey;

import com.OTRAS.DemoProject.Repository.AnswerKeyRepository;

import com.OTRAS.DemoProject.Util.FileUploadUtility;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;

import java.util.stream.Collectors;
 
@Service

public class AnswerKeyService {
 
    private final AnswerKeyRepository answerKeyRepository;

    private final FileUploadUtility fileUploadUtility;
 
    private static final String FOLDER_NAME = "answerkey";
 
    public AnswerKeyService(AnswerKeyRepository answerKeyRepository, FileUploadUtility fileUploadUtility) {

        this.answerKeyRepository = answerKeyRepository;

        this.fileUploadUtility = fileUploadUtility;

    }
 

    public AnswerKey uploadAnswerKey(AnswerKeyRequestDTO dto, MultipartFile file) throws IOException {


        boolean exists = answerKeyRepository.existsByJobCategoryAndJobTitle(

                dto.getJobCategory(),

                dto.getJobTitle()

        );
 
        if (exists) {

            throw new IllegalStateException("Answer Key for this job already exists!");

        }
 

        String fileUrl = fileUploadUtility.uploadFile(file, FOLDER_NAME);
 

        AnswerKey answerKey = AnswerKey.builder()

                .jobCategory(dto.getJobCategory())

                .jobTitle(dto.getJobTitle())

                .description(dto.getDescription())

                .qualifications(dto.getQualifications())

                .websiteUrl(dto.getWebsiteUrl())

                .filePath(fileUrl)

                .build();
 
        return answerKeyRepository.save(answerKey);

    }
 

    public List<AnswerKeyRequestDTO> getAllAnswerKeys() {

        return answerKeyRepository.findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }
 

    public Optional<AnswerKeyRequestDTO> getAnswerKeyById(Long id) {

        return answerKeyRepository.findById(id)

                .map(this::convertToDTO);

    }
 

    private AnswerKeyRequestDTO convertToDTO(AnswerKey answerKey) {

        return AnswerKeyRequestDTO.builder()

//                .id(answerKey.getId())

                .jobCategory(answerKey.getJobCategory())

                .jobTitle(answerKey.getJobTitle())

                .description(answerKey.getDescription())

                .qualifications(answerKey.getQualifications())

                .websiteUrl(answerKey.getWebsiteUrl())

//                .filePath(answerKey.getFilePath())

                .build();

    }

}

 