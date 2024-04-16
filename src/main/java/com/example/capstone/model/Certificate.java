package com.example.capstone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document
public class Certificate {
    @Id
    private String id;
    private String caretakerId;
    private String filePath;
    private String status; // Possible values: "pending", "approved", "declined"
    private String provider;
    private String courseName;
    private String trainerName;
    private LocalDate dateStarted;
    private LocalDate dateCompletion;

    public Certificate() {
    }

    public Certificate(String id, String caretakerId, String filePath, String status, String provider, String courseName, String trainerName, LocalDate dateStarted, LocalDate dateCompletion) {
        this.id = id;
        this.caretakerId = caretakerId;
        this.filePath = filePath;
        this.status = status;
        this.provider = provider;
        this.courseName = courseName;
        this.trainerName = trainerName;
        this.dateStarted = dateStarted;
        this.dateCompletion = dateCompletion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaretakerId() {
        return caretakerId;
    }

    public void setCaretakerId(String caretakerId) {
        this.caretakerId = caretakerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public LocalDate getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDate dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDate getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(LocalDate dateCompletion) {
        this.dateCompletion = dateCompletion;
    }
}
