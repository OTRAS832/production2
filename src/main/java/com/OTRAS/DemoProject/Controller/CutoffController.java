package com.OTRAS.DemoProject.Controller;
 
import com.OTRAS.DemoProject.DTO.CutoffRequestDTO;

import com.OTRAS.DemoProject.Entity.Cutoff;

import com.OTRAS.DemoProject.Service.CutoffService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;

import java.util.List;

import java.util.Optional;
 
@RestController

@RequestMapping("/api/cutoff")


public class CutoffController {
 
    private final CutoffService cutoffService;
 
    public CutoffController(CutoffService cutoffService) {

        this.cutoffService = cutoffService;

    }
 

    @PostMapping("/upload")

    public ResponseEntity<String> uploadCutoff(

            @RequestParam String jobCategory,

            @RequestParam String jobTitle,

            @RequestParam String releasedDate,

            @RequestPart("file") MultipartFile file

    ) {

        try {

            CutoffRequestDTO dto = CutoffRequestDTO.builder()

                    .jobCategory(jobCategory)

                    .jobTitle(jobTitle)

                    .releasedDate(releasedDate)

                    .build();
 
            cutoffService.uploadCutoff(dto, file);

            return ResponseEntity.ok("Cutoff uploaded successfully!");
 
        } catch (IllegalStateException e) {


            return ResponseEntity.badRequest().body(e.getMessage());
 
        } catch (IOException e) {

            return ResponseEntity.internalServerError().body("Error uploading cutoff file!");

        }

    }
 

    @GetMapping("/getAllCutOff")

    public ResponseEntity<List<CutoffRequestDTO>> getAllCutoffs() {

        List<CutoffRequestDTO> cutoffs = cutoffService.getAllCutoffs();

        return ResponseEntity.ok(cutoffs);

    }
 

    @GetMapping("/getbyCutOff")

    public ResponseEntity<?> getCutoffById(@RequestParam Long id) {

        Optional<CutoffRequestDTO> cutoff = cutoffService.getCutoffById(id);

        return cutoff.map(ResponseEntity::ok)

                     .orElseGet(() -> ResponseEntity.notFound().build());

    }
 

    @DeleteMapping("/deleteBycutOffid")

    public ResponseEntity<String> deleteCutoff(@RequestParam Long id) {

        boolean deleted = cutoffService.deleteCutoff(id);

        if (deleted) {

            return ResponseEntity.ok("Cutoff deleted successfully!");

        } else {

            return ResponseEntity.notFound().build();

        }

    }

}
 
 