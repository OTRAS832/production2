package com.OTRAS.DemoProject.Controller;
 
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
 
import com.OTRAS.DemoProject.DTO.AnswerKeyRequestDTO;

import com.OTRAS.DemoProject.Service.AnswerKeyService;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;
 
@RestController

@RequestMapping("/api/answerkey")


public class AnswerKeyController {
 
    private final AnswerKeyService answerKeyService;
 
    public AnswerKeyController(AnswerKeyService answerKeyService) {

        this.answerKeyService = answerKeyService;

    }
 

    @PostMapping("/uploadbyAnswer")

    public ResponseEntity<String> uploadAnswerKey(

            @RequestParam String jobCategory,

            @RequestParam String jobTitle,

            @RequestParam String description,

            @RequestParam String qualifications,

            @RequestParam String websiteUrl,

            @RequestPart("file") MultipartFile file

    ) {

        try {

            AnswerKeyRequestDTO dto = AnswerKeyRequestDTO.builder()

                    .jobCategory(jobCategory)

                    .jobTitle(jobTitle)

                    .description(description)

                    .qualifications(qualifications)

                    .websiteUrl(websiteUrl)

                    .build();
 
            answerKeyService.uploadAnswerKey(dto, file);

            return ResponseEntity.ok("Answer Key uploaded successfully!");
 
        } catch (IllegalStateException e) {


            return ResponseEntity.badRequest().body(e.getMessage());
 
        } catch (IOException e) {

            return ResponseEntity.internalServerError().body("Failed to upload Answer Key!");
 
        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());

        }

    }
 

    @GetMapping("/getallAnswerKeys")

    public ResponseEntity<List<AnswerKeyRequestDTO>> getAllAnswerKeys() {

        return ResponseEntity.ok(answerKeyService.getAllAnswerKeys());

    }
 

    @GetMapping("/getbyAnswerKeyid")

    public ResponseEntity<?> getAnswerKeyById(@RequestParam Long id) {

        Optional<AnswerKeyRequestDTO> answerKey = answerKeyService.getAnswerKeyById(id);

        return answerKey.map(ResponseEntity::ok)

                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}

 