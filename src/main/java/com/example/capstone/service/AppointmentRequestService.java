package com.example.capstone.service;

import com.example.capstone.dto.AppointmentRequestDetailsDto;
import com.example.capstone.model.AppointmentRequest;
import com.example.capstone.model.DogOwner;
import com.example.capstone.repository.AppointmentRequestRepository;
import com.example.capstone.repository.DogOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentRequestService {
    private final AppointmentRequestRepository appointmentRequestRepository;
    private final DogOwnerRepository dogOwnerRepository; // Declare this variable

    @Autowired
    public AppointmentRequestService(AppointmentRequestRepository appointmentRequestRepository, DogOwnerRepository dogOwnerRepository) {
        this.appointmentRequestRepository = appointmentRequestRepository;
        this.dogOwnerRepository = dogOwnerRepository;  // Initialize it here
    }

    public AppointmentRequest createAppointment(AppointmentRequest appointmentRequest) {
        appointmentRequest.setStatus("pending");
        return appointmentRequestRepository.save(appointmentRequest);
    }

    public List<AppointmentRequest> getAppointmentsForCaretaker(String caretakerId) {
        return appointmentRequestRepository.findByCaretakerIdAndStatus(caretakerId, "pending");
    }

    public AppointmentRequest updateAppointmentStatus(String appointmentId, String status) {
        AppointmentRequest appointmentRequest = appointmentRequestRepository.findById(appointmentId).orElseThrow();
        appointmentRequest.setStatus(status);
        return appointmentRequestRepository.save(appointmentRequest);
    }

    public List<AppointmentRequestDetailsDto> getAppointmentsDetailsForCaretaker(String caretakerId) {
        return appointmentRequestRepository.findByCaretakerId(caretakerId).stream()
                .map(appointmentRequest -> {
                    DogOwner dogOwner = dogOwnerRepository.findById(appointmentRequest.getDogOwnerId()).orElse(null);
                    return new AppointmentRequestDetailsDto(appointmentRequest, dogOwner);
                })
                .collect(Collectors.toList());
    }
}
