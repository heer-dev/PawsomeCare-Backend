package com.example.capstone.service;

import com.example.capstone.dto.PasswordChangeDto;
import com.example.capstone.model.Caretaker;
import com.example.capstone.model.DogOwner;
import com.example.capstone.repository.DogOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public DogOwner registerDogOwner(DogOwner registrationDto) {
        DogOwner dogOwner = new DogOwner();
        dogOwner.setEmail(registrationDto.getEmail());
        dogOwner.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        dogOwner.setFirstName(registrationDto.getFirstName());
        dogOwner.setLastName(registrationDto.getLastName());
        dogOwner.setPhone(registrationDto.getPhone());
        dogOwner.setPhoto(registrationDto.getPhoto());
        // Set other properties as necessary

        return dogOwnerRepository.save(dogOwner);
    }
    public DogOwner authenticateDogOwner(DogOwner loginDto) throws Exception {
        return dogOwnerRepository.findByEmail(loginDto.getEmail())
                .filter(dogOwner -> passwordEncoder.matches(loginDto.getPassword(), dogOwner.getPassword()))
                .orElseThrow(() -> new Exception("Invalid credentials"));
    }


    public ResponseEntity<?> forgotPassword(String email) {
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findByEmail(email);
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email not found.");
        }

        // Generate a token and save it in the database with an expiration date
        String resetToken = UUID.randomUUID().toString();
        DogOwner dogOwner = dogOwnerOpt.get();
        dogOwner.setResetToken(resetToken);
        dogOwner.setTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        dogOwnerRepository.save(dogOwner);

        // TODO: Send the token to the user's email
        // ...
        emailService.sendPasswordResetEmail(dogOwner.getEmail(), resetToken);

        return ResponseEntity.ok("Password reset email sent.");
    }

    public ResponseEntity<?> changePassword(String dogOwnerId, PasswordChangeDto passwordChangeDto) {
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findById(dogOwnerId);
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dogowner not found.");
        }

        DogOwner dogOwner = dogOwnerOpt.get();
        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), dogOwner.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }

        dogOwner.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        dogOwnerRepository.save(dogOwner);

        return ResponseEntity.ok("Password updated successfully.");
    }

    public ResponseEntity<?> resetPassword(String token, String email,String newPassword) {
        // Find caretaker by reset token
        Optional<DogOwner> dogOwnerOpt = dogOwnerRepository.findByResetToken(token);

        // Check if caretaker with given token exists
        if (!dogOwnerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DogOwner dogOwner = dogOwnerOpt.get();

        // Check if the token has expired
        if (dogOwner.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Password reset token has expired.");
        }

        // Update caretaker's password
        dogOwner.setPassword(passwordEncoder.encode(newPassword));
        // Clear reset token and expiry date
        dogOwner.setResetToken(null);
        dogOwner.setTokenExpiryDate(null);

        // Save updated caretaker
        dogOwnerRepository.save(dogOwner);

        return ResponseEntity.ok("Password reset successfully.");
    }

    public long countAllDogOwners() {
        return dogOwnerRepository.count();
    }
}
