package com.OTRAS.DemoProject.Service;
 
import com.OTRAS.DemoProject.DTO.SyllabusRequestDTO;

import com.OTRAS.DemoProject.Entity.Syllabus;

import com.OTRAS.DemoProject.Repository.SyllabusRepository;

import com.OTRAS.DemoProject.Util.FileUploadUtility;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;
 
@Service

public class SyllabusService {
 
    private final SyllabusRepository syllabusRepository;

    private final FileUploadUtility fileUploadUtility;
 
    private static final String FOLDER_NAME = "syllabus";
 
    public SyllabusService(SyllabusRepository syllabusRepository, FileUploadUtility fileUploadUtility) {

        this.syllabusRepository = syllabusRepository;

        this.fileUploadUtility = fileUploadUtility;

    }
 

    public Syllabus uploadSyllabus(SyllabusRequestDTO dto, MultipartFile file) throws IOException {


        boolean exists = syllabusRepository

                .findByJobCategoryAndJobTitle(dto.getJobCategory(), dto.getJobTitle())

                .isPresent();
 
        if (exists) {

            throw new IllegalArgumentException("Syllabus for this jobCategory and jobTitle already exists!");

        }
 

        String fileUrl = fileUploadUtility.uploadFile(file, FOLDER_NAME);
 
        Syllabus syllabus = Syllabus.builder()

                .jobCategory(dto.getJobCategory())

                .jobTitle(dto.getJobTitle())

                .qualifications(dto.getQualifications())

                .syllabusFilePath(fileUrl)

                .build();
 
        return syllabusRepository.save(syllabus);

    }
 

    public List<Syllabus> getAllSyllabuses() {

        return syllabusRepository.findAll();

    }
 

    public Optional<Syllabus> getSyllabusById(Long id) {

        return syllabusRepository.findById(id);

    }
 

    public Syllabus updateSyllabus(Long id, SyllabusRequestDTO dto, MultipartFile file) throws IOException {

        Syllabus existing = syllabusRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Syllabus not found with id: " + id));
 
        String updatedFileUrl = fileUploadUtility.replaceFile(file, FOLDER_NAME, existing.getSyllabusFilePath());
 
        existing.setJobCategory(dto.getJobCategory());

        existing.setJobTitle(dto.getJobTitle());

        existing.setQualifications(dto.getQualifications());

        existing.setSyllabusFilePath(updatedFileUrl);
 
        return syllabusRepository.save(existing);

    }
 

    public void deleteSyllabus(Long id) {

        Syllabus existing = syllabusRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Syllabus not found with id: " + id));
 
        fileUploadUtility.deleteFile(existing.getSyllabusFilePath());

        syllabusRepository.delete(existing);

    }

}

 