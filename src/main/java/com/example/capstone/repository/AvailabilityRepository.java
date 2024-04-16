package com.example.capstone.repository;

import com.example.capstone.model.Availability;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AvailabilityRepository extends MongoRepository<Availability, String> {
    List<Availability> findByCaretakerId(String caretakerId);
}
