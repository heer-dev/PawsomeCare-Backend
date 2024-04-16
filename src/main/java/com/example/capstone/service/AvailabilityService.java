package com.example.capstone.service;

import com.example.capstone.model.Availability;
import com.example.capstone.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Availability saveAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    public List<Availability> findAllAvailabilities() {
        return availabilityRepository.findAll();
    }
    public Availability updateAvailability(String id, Availability availability) {
        return availabilityRepository.findById(id)
                .map(existingAvailability -> {
                    existingAvailability.setLocation(availability.getLocation());
                    existingAvailability.setStartDate(availability.getStartDate());
                    existingAvailability.setEndDate(availability.getEndDate());
                    existingAvailability.setStartTime(availability.getStartTime());
                    existingAvailability.setEndTime(availability.getEndTime());
                    existingAvailability.setPay(availability.getPay());
                    existingAvailability.setAdditionalRequirements(availability.getAdditionalRequirements());
                    return availabilityRepository.save(existingAvailability);
                })
                .orElse(null);
    }

    public boolean deleteAvailability(String id) {
        return availabilityRepository.findById(id)
                .map(availability -> {
                    availabilityRepository.delete(availability);
                    return true;
                }).orElse(false);
    }

    public List<Availability> findAvailabilitiesByCaretakerId(String caretakerId) {
        return availabilityRepository.findByCaretakerId(caretakerId);
    }


}
