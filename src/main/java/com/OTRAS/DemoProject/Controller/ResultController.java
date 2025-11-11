package com.OTRAS.DemoProject.Controller;
 
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
 
import com.OTRAS.DemoProject.DTO.ResultRequestDTO;

import com.OTRAS.DemoProject.Service.ResultService;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;
 
@RestController

@RequestMapping("/api/result")


public class ResultController {
 
    private final ResultService resultService;
 
    public ResultController(ResultService resultService) {

        this.resultService = resultService;

    }
 

    @PostMapping("/Resultupload")

    public ResponseEntity<String> uploadResult(

            @RequestParam String jobCategory,

            @RequestParam String jobTitle,

            @RequestParam String releasedDate,

            @RequestParam String websiteUrl,

            @RequestPart("file") MultipartFile file) {
 
        try {

            ResultRequestDTO dto = ResultRequestDTO.builder()

                    .jobCategory(jobCategory)

                    .jobTitle(jobTitle)

                    .releasedDate(releasedDate)

                    .websiteUrl(websiteUrl)

                    .build();
 
            resultService.uploadResult(dto, file);

            return ResponseEntity.ok(" Result uploaded successfully!");
 
        } catch (IllegalStateException e) {


            return ResponseEntity.badRequest()

                    .body(" Duplicate entry: " + e.getMessage());
 
        } catch (IOException e) {
            return ResponseEntity.internalServerError()

                    .body(" File upload failed: " + e.getMessage());
 
        } catch (Exception e) {


            return ResponseEntity.internalServerError()

                    .body(" Unexpected error: " + e.getMessage());

        }

    }
 

    @GetMapping("/all")

    public ResponseEntity<List<ResultRequestDTO>> getAllResults() {

        List<ResultRequestDTO> results = resultService.getAllResults();

        return ResponseEntity.ok(results);

    }
 

    @GetMapping("/Result")

    public ResponseEntity<?> getResultById(@RequestParam Long id) {

        Optional<ResultRequestDTO> result = resultService.getResultById(id);

        return result.map(ResponseEntity::ok)

                     .orElseGet(() -> ResponseEntity.notFound().build());

    }
 

    @DeleteMapping("/Resultid")

    public ResponseEntity<String> deleteResult(@RequestParam Long id) {

        try {

            resultService.deleteResult(id);

            return ResponseEntity.ok(" Result deleted successfully!");

        } catch (Exception e) {

            return ResponseEntity.internalServerError()

                    .body(" Failed to delete result: " + e.getMessage());

        }

    }

}

 