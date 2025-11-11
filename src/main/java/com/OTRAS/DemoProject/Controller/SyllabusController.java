package com.OTRAS.DemoProject.Controller;
 
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
 
import com.OTRAS.DemoProject.DTO.SyllabusRequestDTO;

import com.OTRAS.DemoProject.Entity.Syllabus;

import com.OTRAS.DemoProject.Service.SyllabusService;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;
 
@RestController

@RequestMapping("/api/syllabus")

public class SyllabusController {
 
    private final SyllabusService syllabusService;
 
    public SyllabusController(SyllabusService syllabusService) {

        this.syllabusService = syllabusService;

    }
 

    @PostMapping("/upload")

    public ResponseEntity<String> uploadSyllabus(

            @RequestParam String jobCategory,

            @RequestParam String jobTitle,

            @RequestParam String qualifications,

            @RequestPart("file") MultipartFile file) {

        try {

            SyllabusRequestDTO dto = SyllabusRequestDTO.builder()

                    .jobCategory(jobCategory)

                    .jobTitle(jobTitle)

                    .qualifications(qualifications)

                    .build();
 
            Syllabus uploadSyllabus = syllabusService.uploadSyllabus(dto, file);

            return ResponseEntity.ok("Syllabus uploaded successfully! " +

                    uploadSyllabus.getJobCategory() + " - " +

                    uploadSyllabus.getJobTitle());

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (IOException e) {

            return ResponseEntity.internalServerError().body("Syllabus upload failed!");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());

        }

    }
 

    @GetMapping("/GetAllSyllabus")

    public ResponseEntity<List<Syllabus>> getAllSyllabuses() {

        return ResponseEntity.ok(syllabusService.getAllSyllabuses());

    }
 

    @GetMapping("/getSyllabusId")

    public ResponseEntity<?> getSyllabusById(@RequestParam Long id) {

        Optional<Syllabus> syllabus = syllabusService.getSyllabusById(id);

        return syllabus.map(ResponseEntity::ok)

                       .orElseGet(() -> ResponseEntity.notFound().build());

    }

}

 