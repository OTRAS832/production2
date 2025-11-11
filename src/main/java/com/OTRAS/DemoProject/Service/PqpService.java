package com.OTRAS.DemoProject.Service;
 
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
 
import com.OTRAS.DemoProject.DTO.PqpRequestDTO;

import com.OTRAS.DemoProject.Entity.Pqp;

import com.OTRAS.DemoProject.Repository.PqpRepository;

import com.OTRAS.DemoProject.Util.FileUploadUtility;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;

import java.util.stream.Collectors;
 
@Service

public class PqpService {
 
    private final PqpRepository pqpRepository;

    private final FileUploadUtility fileUploadUtility;
 
    private static final String FOLDER_NAME = "pqp";
 
    public PqpService(PqpRepository pqpRepository, FileUploadUtility fileUploadUtility) {

        this.pqpRepository = pqpRepository;

        this.fileUploadUtility = fileUploadUtility;

    }
 

    public String uploadPqp(PqpRequestDTO dto, MultipartFile file) throws IOException {
 

        boolean exists = pqpRepository

                .findByJobCategoryAndJobTitle(dto.getJobCategory(), dto.getJobTitle())

                .isPresent();
 
        if (exists) {

            throw new IllegalArgumentException("PQP for this jobCategory and jobTitle already exists!");

        }
 

        String fileUrl = fileUploadUtility.uploadFile(file, FOLDER_NAME);
 
        Pqp pqpEntity = Pqp.builder()

                .jobCategory(dto.getJobCategory())

                .jobTitle(dto.getJobTitle())

                .languages(dto.getLanguages())

                .qualifications(dto.getQualifications())

                .pqp(dto.getPqp())

                .pdfFilePath(fileUrl)

                .build();
 
        pqpRepository.save(pqpEntity);

        return "PQP uploaded successfully!";

    }
 

    public List<PqpRequestDTO> getAllPqps() {

        return pqpRepository.findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }
 

    public Optional<PqpRequestDTO> getPqpById(Long id) {

        return pqpRepository.findById(id)

                .map(this::convertToDTO);

    }
 

    private PqpRequestDTO convertToDTO(Pqp pqp) {

        return PqpRequestDTO.builder()

                .jobCategory(pqp.getJobCategory())

                .jobTitle(pqp.getJobTitle())

                .languages(pqp.getLanguages())

                .qualifications(pqp.getQualifications())

                .pqp(pqp.getPdfFilePath())

                .build();

    }

}

 