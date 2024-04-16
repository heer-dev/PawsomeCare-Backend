package com.example.capstone.service;

import com.example.capstone.model.Certificate;
import com.example.capstone.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;


@Service
public class CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;

    public Certificate saveCertificate(String caretakerId, MultipartFile file, Path uploadDir, String provider, String courseName, String trainerName, LocalDate dateStarted, LocalDate dateCompletion) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }

        String fileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;
        Path targetLocation = uploadDir.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Certificate certificate = new Certificate();
        certificate.setCaretakerId(caretakerId);
        certificate.setFilePath(targetLocation.toString());
        certificate.setStatus("pending");
        certificate.setProvider(provider);
        certificate.setCourseName(courseName);
        certificate.setTrainerName(trainerName);
        certificate.setDateStarted(dateStarted);
        certificate.setDateCompletion(dateCompletion);
        return certificateRepository.save(certificate);
    }

    // Additional methods for certificate management
}
