package com.example.capstone.dto;

import java.time.LocalDate;

public class SupportInquiryDTO {
    private String id;
    private String email;
    private LocalDate date;
    private String inquiry;

    public SupportInquiryDTO(String id, String email, LocalDate date, String inquiry) {
        this.id = id;
        this.email = email;
        this.date = date;
        this.inquiry = inquiry;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }
}
