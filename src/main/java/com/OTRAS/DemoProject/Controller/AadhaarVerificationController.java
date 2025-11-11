package com.OTRAS.DemoProject.Controller;
 
import org.springframework.core.io.ClassPathResource;

import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
 
@RestController

public class AadhaarVerificationController {
 
    @GetMapping(value = "/aadhaar-verification", produces = MediaType.TEXT_HTML_VALUE)

    public ResponseEntity<Resource> serveAadhaarVerificationPage() {

        try {

            Resource resource = new ClassPathResource("static/aadhaar-verification.html");

            return ResponseEntity.ok()

                    .contentType(MediaType.TEXT_HTML)

                    .body(resource);

        } catch (Exception e) {

            return ResponseEntity.notFound().build();

        }

    }

}
 