package com.example.capstone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class AppointmentRequest {
    @Id
    private String id;
    private String availabilityId;
    private String caretakerId;
    private String dogOwnerId;
    private LocalDate requestedDate;
    private String status; // Pending, Accepted, Rejected

    // Constructors, getters, setters


    public AppointmentRequest() {
    }

    public AppointmentRequest(String id, String availabilityId, String caretakerId, String dogOwnerId, LocalDate requestedDate, String status) {
        this.id = id;
        this.availabilityId = availabilityId;
        this.caretakerId = caretakerId;
        this.dogOwnerId = dogOwnerId;
        this.requestedDate = requestedDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }

    public String getCaretakerId() {
        return caretakerId;
    }

    public void setCaretakerId(String caretakerId) {
        this.caretakerId = caretakerId;
    }

    public String getDogOwnerId() {
        return dogOwnerId;
    }

    public void setDogOwnerId(String dogOwnerId) {
        this.dogOwnerId = dogOwnerId;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
