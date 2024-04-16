package com.example.capstone.controller;

import com.example.capstone.model.Availability;
import com.example.capstone.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/post")
    public ResponseEntity<?> postAvailability(@RequestBody Availability availability) {
        try {
            Availability savedAvailability = availabilityService.saveAvailability(availability);
            return ResponseEntity.ok(savedAvailability);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to post availability: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Availability>> getAllAvailabilities() {
        try {
            List<Availability> availabilities = availabilityService.findAllAvailabilities();
            return ResponseEntity.ok(availabilities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAvailability(@PathVariable String id, @RequestBody Availability availability) {
        try {
            Availability updatedAvailability = availabilityService.updateAvailability(id, availability);
            if (updatedAvailability == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedAvailability);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update availability: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAvailability(@PathVariable String id) {
        try {
            if (availabilityService.deleteAvailability(id)) {
                return ResponseEntity.ok().body("Availability deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete availability: " + e.getMessage());
        }
    }

    @GetMapping("/caretaker/{caretakerId}")
    public ResponseEntity<List<Availability>> getAvailabilitiesByCaretakerId(@PathVariable String caretakerId) {
        try {
            List<Availability> availabilities = availabilityService.findAvailabilitiesByCaretakerId(caretakerId);
            if (availabilities.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(availabilities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
