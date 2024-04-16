package com.example.capstone.controller;

import com.example.capstone.model.Certificate;
import com.example.capstone.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    @PostMapping("/upload/{caretakerId}")
    public ResponseEntity<?> uploadCertificate(@PathVariable String caretakerId,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("provider") String provider,
                                               @RequestParam("courseName") String courseName,
                                               @RequestParam("trainerName") String trainerName,
                                               @RequestParam("dateStarted") LocalDate dateStarted,
                                               @RequestParam("dateCompletion") LocalDate dateCompletion) {
        try {
            Certificate certificate = certificateService.saveCertificate(caretakerId, file, Paths.get("C:/Users/heerv/OneDrive/Documents/certificates"), provider, courseName, trainerName, dateStarted, dateCompletion);
            return ResponseEntity.ok(certificate);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error uploading certificate: " + e.getMessage());
        }
    }

    // Additional endpoints for certificate management
}