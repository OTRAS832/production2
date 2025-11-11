package com.OTRAS.DemoProject.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.OTRAS.DemoProject.DTO.AdmitCardDTO;
import com.OTRAS.DemoProject.Service.AdmitCardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admit-card")
@RequiredArgsConstructor
public class AdmitCardController {

    private final AdmitCardService admitCardService;

    @PostMapping("/generate")
    public AdmitCardDTO generate(@RequestParam Long paymentId) {
        return admitCardService.generateAdmitCard(paymentId);
    }
    
    @PostMapping("/generate-by-job")
    public List<AdmitCardDTO> generateAllByJob(@RequestParam Long jobPostId) {
        return admitCardService.generateAllByJobPost(jobPostId);
    }

    @GetMapping("/all-by-job")
    public List<AdmitCardDTO> getAllByJob(@RequestParam Long jobPostId) {
        return admitCardService.getAllAdmitCardsByJob(jobPostId);
    }

    @GetMapping("/get-by-otr")
    public AdmitCardDTO getByOtr(@RequestParam String otrId) {
        return admitCardService.getAdmitCardByOtrId(otrId);
    }
    

    @GetMapping("/all-by-candidate")
    public ResponseEntity<?> getAllByCandidate(@RequestParam Long candidateProfileId) {
        try {
            List<AdmitCardDTO> admitCards = admitCardService.getAllAdmitCardsByCandidate(candidateProfileId);
            return ResponseEntity.ok(admitCards);
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(Map.of("status", "error", "message", e.getReason()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Something went wrong on the server."));
        }
    }

     
    
}
