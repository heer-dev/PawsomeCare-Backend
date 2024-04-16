package com.example.capstone.controller;

import com.example.capstone.dto.AvailabilityDto;
import com.example.capstone.dto.CaretakerDto;
import com.example.capstone.dto.ResetPasswordDto;
import com.example.capstone.model.Availability;
import com.example.capstone.model.Caretaker;
import com.example.capstone.service.AvailabilityService;
import com.example.capstone.service.CaretakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/caretakers")
public class CaretakerController {
    @Autowired
    private CaretakerService caretakerService;
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/register")
    public ResponseEntity<Caretaker> registerCaretaker(@RequestBody Caretaker caretaker) {
        Caretaker registered = caretakerService.registerCaretaker(caretaker);
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCaretaker(@RequestBody Caretaker loginDto) {
        try {
            Caretaker authenticatedCaretaker = caretakerService.authenticateCaretaker(loginDto);
            return ResponseEntity.ok(authenticatedCaretaker);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Caretaker> getCaretakerProfile(@PathVariable String userId) {
        Caretaker caretaker = caretakerService.getCaretakerProfile(userId);
        if (caretaker != null) {
            return ResponseEntity.ok(caretaker);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<Caretaker> updateCaretakerProfile(@PathVariable String userId, @RequestBody Caretaker caretaker) {
        Caretaker updatedCaretaker = caretakerService.updateCaretakerProfile(userId, caretaker);
        if (updatedCaretaker != null) {
            return ResponseEntity.ok(updatedCaretaker);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        return caretakerService.forgotPassword(email);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return caretakerService.resetPassword(resetPasswordDto.getToken(), resetPasswordDto.getEmail(), resetPasswordDto.getNewPassword());
    }
    @PutMapping("/change-password/{caretakerId}")
    public ResponseEntity<?> changePassword(@PathVariable String caretakerId, @RequestBody PasswordChangeDto passwordChangeDto) {
        return caretakerService.changePassword(caretakerId, passwordChangeDto);
    }

    @GetMapping("/list-for-dogowners")
    public ResponseEntity<List<CaretakerDto>> listCaretakersForDogOwners() {
        List<Caretaker> caretakers = caretakerService.getAllCaretakers();
        List<Availability> allAvailabilities = availabilityService.findAllAvailabilities();

        List<CaretakerDto> caretakerDtos = caretakers.stream().map(caretaker -> {
            CaretakerDto dto = new CaretakerDto();
            dto.setName(caretaker.getFirstName() + " " + caretaker.getLastName());
            dto.setYearsOfExperience(caretaker.getYearsOfExperience());
            dto.setPhoto(caretaker.getPhoto() != null ? caretaker.getPhoto() : "path_to_default_photo.jpg");

            List<AvailabilityDto> availabilityDtos = allAvailabilities.stream()
                    .filter(a -> a.getCaretakerId() != null && a.getCaretakerId().equals(caretaker.getId()))
                    .map(availability -> {
                        AvailabilityDto availabilityDto = new AvailabilityDto();
                        availabilityDto.setId(availability.getId());
                        availabilityDto.setStartDate(availability.getStartDate());
                        availabilityDto.setEndDate(availability.getEndDate());
                        availabilityDto.setStartTime(availability.getStartTime());
                        availabilityDto.setEndTime(availability.getEndTime());
                        availabilityDto.setLocation(availability.getLocation());
                        availabilityDto.setPay(availability.getPay());
                        availabilityDto.setAdditionalRequirements(availability.getAdditionalRequirements());
                        return availabilityDto;
                    })
                    .collect(Collectors.toList());

            dto.setAvailabilities(availabilityDtos);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(caretakerDtos);
    }

}
