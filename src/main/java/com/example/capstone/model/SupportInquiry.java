package com.example.capstone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supportInquiries")
public class SupportInquiry {
    @Id
    private String id;
    private String email;
    private String inquiry;

    // Constructors, Getters, and Setters


    public SupportInquiry() {
    }

    public SupportInquiry(String id, String email, String inquiry) {
        this.id = id;
        this.email = email;
        this.inquiry = inquiry;
    }

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

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }
}