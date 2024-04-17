package com.example.capstone.controller;

import com.example.capstone.dto.PasswordChangeDto;
import com.example.capstone.dto.ResetPasswordDto;
import com.example.capstone.model.Caretaker;
import com.example.capstone.model.DogOwner;
import com.example.capstone.service.DogOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dogowners")
public class DogOwnerController {

    @Autowired
    private DogOwnerService dogOwnerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDogOwner(@RequestBody DogOwner registrationDto) {
        try {
            DogOwner dogOwner = dogOwnerService.registerDogOwner(registrationDto);
            return new ResponseEntity<>(dogOwner, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register dog owner", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginDogOwner(@RequestBody DogOwner loginDto) {
        try {
            DogOwner authenticatedDogOwner = dogOwnerService.authenticateDogOwner(loginDto.getEmail(), loginDto.getPassword());
            return ResponseEntity.ok(authenticatedDogOwner);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        return dogOwnerService.forgotPassword(email);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
            return dogOwnerService.resetPassword(resetPasswordDto.getToken(), resetPasswordDto.getEmail(), resetPasswordDto.getNewPassword());

    }


    @PutMapping("/change-password/{dogOwnerId}")
    public ResponseEntity<?> changePassword(@PathVariable String dogOwnerId, @RequestBody PasswordChangeDto passwordChangeDto) {
        return dogOwnerService.changePassword(dogOwnerId, passwordChangeDto);
    }


    @GetMapping("/profile/{userId}")
    public ResponseEntity<DogOwner> getDogOwnerProfile(@PathVariable String userId) {
        DogOwner dogOwner = dogOwnerService.getDogOwnerProfile(userId);
        if (dogOwner != null) {
            return ResponseEntity.ok(dogOwner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/profile/{userId}")
    public ResponseEntity<DogOwner> updateDogOwnerProfile(@PathVariable String userId, @RequestBody DogOwner dogOwner) {
        DogOwner updatedDogOwner = dogOwnerService.updateDogOwnerProfile(userId, dogOwner);
        if (updatedDogOwner != null) {
            return ResponseEntity.ok(updatedDogOwner);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
