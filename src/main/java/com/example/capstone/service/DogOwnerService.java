package com.example.capstone.service;

import com.example.capstone.dto.PasswordChangeDto;
import com.example.capstone.model.DogOwner;
import com.example.capstone.repository.DogOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DogOwnerService {

    @Autowired
    private DogOwnerRepository dogOwnerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public DogOwner registerDogOwner(DogOwner dogOwner) {
        dogOwner.setPassword(passwordEncoder.encode(dogOwner.getPassword()));
        return dogOwnerRepository.save(dogOwner);
    }

    public DogOwner authenticateDogOwner(String email, String password) throws Exception {
        return dogOwnerRepository.findByEmail(email)
                .filter(dogOwner -> passwordEncoder.matches(password, dogOwner.getPassword()))
                .orElseThrow(() -> new Exception("Invalid credentials"));
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findByEmail(email);
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email not found.");
        }

        String resetToken = UUID.randomUUID().toString();
        DogOwner dogOwner = dogOwnerOpt.get();
        dogOwner.setResetToken(resetToken);
        dogOwner.setTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        dogOwnerRepository.save(dogOwner);

        emailService.sendPasswordResetEmail(dogOwner.getEmail(), resetToken);

        return ResponseEntity.ok("Password reset email sent.");
    }


    public ResponseEntity<?> changePassword(String dogOwnerId, PasswordChangeDto passwordChangeDto) {
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findById(dogOwnerId);
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dog owner not found.");
        }

        DogOwner dogOwner = dogOwnerOpt.get();
        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), dogOwner.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }

        dogOwner.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        dogOwnerRepository.save(dogOwner);

        return ResponseEntity.ok("Password updated successfully.");
    }


    public ResponseEntity<?> resetPassword(String token, String email, String newPassword) {
        // Find dog owner by reset token
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findByResetToken(token);

        // Check if dog owner with given token exists
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Check if the token belongs to the email provided
        DogOwner dogOwner = dogOwnerOpt.get();
        if (!dogOwner.getEmail().equalsIgnoreCase(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email for the provided reset token.");
        }

        // Check if the token has expired
        if (dogOwner.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Password reset token has expired.");
        }

        // Update dog owner's password
        dogOwner.setPassword(passwordEncoder.encode(newPassword));
        // Clear reset token and expiry date
        dogOwner.setResetToken(null);
        dogOwner.setTokenExpiryDate(null);

        // Save updated dog owner
        dogOwnerRepository.save(dogOwner);

        return ResponseEntity.ok("Password has been reset successfully.");
    }


    public DogOwner getDogOwnerProfile(String userId) {
        return dogOwnerRepository.findById(userId).orElse(null);
    }

    public DogOwner updateDogOwnerProfile(String userId, DogOwner updatedDogOwner) {
        DogOwner existingDogOwner = dogOwnerRepository.findById(userId).orElse(null);
        if (existingDogOwner != null) {
            existingDogOwner.setFirstName(updatedDogOwner.getFirstName());
            existingDogOwner.setLastName(updatedDogOwner.getLastName());
            existingDogOwner.setEmail(updatedDogOwner.getEmail());
            existingDogOwner.setPhone(updatedDogOwner.getPhone());
            existingDogOwner.setDogName(updatedDogOwner.getDogName());
            existingDogOwner.setAboutDog(updatedDogOwner.getAboutDog());
            return dogOwnerRepository.save(existingDogOwner);
        }
        return null;
    }
}
