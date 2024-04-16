package com.example.capstone.dto;

import com.example.capstone.model.AppointmentRequest;
import com.example.capstone.model.DogOwner;

public class AppointmentRequestDetailsDto {
    private AppointmentRequest appointmentRequest;
    private String dogOwnerName;
    private String dogOwnerId;

    public AppointmentRequestDetailsDto(AppointmentRequest appointmentRequest, DogOwner dogOwner) {
        this.appointmentRequest = appointmentRequest;
        if (dogOwner != null) {
            this.dogOwnerName = dogOwner.getFirstName() + " " + dogOwner.getLastName();
            this.dogOwnerId = dogOwner.getId();
        }
    }

    // Getters and Setters
}
