package com.example.capstone.repository;

import com.example.capstone.model.AppointmentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRequestRepository extends MongoRepository<AppointmentRequest, String> {
    List<AppointmentRequest> findByCaretakerId(String caretakerId);
    List<AppointmentRequest> findByCaretakerIdAndStatus(String caretakerId, String status);

}

