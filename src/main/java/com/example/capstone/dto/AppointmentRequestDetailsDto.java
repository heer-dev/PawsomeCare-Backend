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

    public AppointmentRequest getAppointmentRequest() {
        return appointmentRequest;
    }

    public void setAppointmentRequest(AppointmentRequest appointmentRequest) {
        this.appointmentRequest = appointmentRequest;
    }

    public String getDogOwnerName() {
        return dogOwnerName;
    }

    public void setDogOwnerName(String dogOwnerName) {
        this.dogOwnerName = dogOwnerName;
    }

    public String getDogOwnerId() {
        return dogOwnerId;
    }

    public void setDogOwnerId(String dogOwnerId) {
        this.dogOwnerId = dogOwnerId;
    }
}
