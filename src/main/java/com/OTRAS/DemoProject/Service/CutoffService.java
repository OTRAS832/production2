package com.OTRAS.DemoProject.Service;
 
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.OTRAS.DemoProject.DTO.CutoffRequestDTO;
import com.OTRAS.DemoProject.Entity.Cutoff;
import com.OTRAS.DemoProject.Repository.CutoffRepository;
import com.OTRAS.DemoProject.Util.FileUploadUtility;
 
@Service

public class CutoffService {
 
    private final CutoffRepository cutoffRepository;

    private final FileUploadUtility fileUploadUtility;
 

    private static final String FOLDER_NAME = "cutoff";
 
    public CutoffService(CutoffRepository cutoffRepository, FileUploadUtility fileUploadUtility) {

        this.cutoffRepository = cutoffRepository;

        this.fileUploadUtility = fileUploadUtility;

    }
 

    public Cutoff uploadCutoff(CutoffRequestDTO dto, MultipartFile file) throws IOException {


        String fileUrl = fileUploadUtility.uploadFile(file, FOLDER_NAME);


        boolean exists = cutoffRepository.existsByJobCategoryAndJobTitleAndReleasedDate(

                dto.getJobCategory(),

                dto.getJobTitle(),

                dto.getReleasedDate()

        );
 
        if (exists) {

            throw new IllegalStateException("Cutoff for this job already exists!");

        }
 

        Cutoff cutoff = Cutoff.builder()

                .jobCategory(dto.getJobCategory())

                .jobTitle(dto.getJobTitle())

                .releasedDate(dto.getReleasedDate())

                .filePath(fileUrl)

                .build();
 
        return cutoffRepository.save(cutoff);

    }
 

    public List<CutoffRequestDTO> getAllCutoffs() {

        return cutoffRepository.findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());

    }
 

    public Optional<CutoffRequestDTO> getCutoffById(Long id) {

        return cutoffRepository.findById(id)

                .map(this::convertToDTO);

    }
 

    public boolean deleteCutoff(Long id) {

        if (cutoffRepository.existsById(id)) {

            cutoffRepository.deleteById(id);

            return true;

        }

        return false;

    }
 

    private CutoffRequestDTO convertToDTO(Cutoff cutoff) {

        return CutoffRequestDTO.builder()

//                .id(cutoff.getId())

                .jobCategory(cutoff.getJobCategory())

                .jobTitle(cutoff.getJobTitle())

                .releasedDate(cutoff.getReleasedDate())

                .filePath(cutoff.getFilePath())

                .build();

    }

}
 