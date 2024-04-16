package com.example.capstone.service;

import com.example.capstone.controller.PasswordChangeDto;
import com.example.capstone.model.Caretaker;
import com.example.capstone.repository.CaretakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaretakerService {
    @Autowired
    private CaretakerRepository caretakerRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public Caretaker registerCaretaker(Caretaker registrationDto) {
        Caretaker caretaker = new Caretaker();
        caretaker.setEmail(registrationDto.getEmail());
        caretaker.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        caretaker.setFirstName(registrationDto.getFirstName());
        caretaker.setLastName(registrationDto.getLastName());
        caretaker.setPhone(registrationDto.getPhone());
        caretaker.setPhoto(registrationDto.getPhoto());
        // Set other properties as necessary

        return caretakerRepository.save(caretaker);
    }

    public Caretaker authenticateCaretaker(Caretaker loginDto) throws Exception {
        return caretakerRepository.findByEmail(loginDto.getEmail())
                .filter(caretaker -> passwordEncoder.matches(loginDto.getPassword(), caretaker.getPassword()))
                .orElseThrow(() -> new Exception("Invalid credentials"));
    }

    // Additional methods for caretaker management

    public Caretaker getCaretakerProfile(String userId) {
        return caretakerRepository.findById(userId).orElse(null);
    }

    public Caretaker updateCaretakerProfile(String userId, Caretaker updatedCaretaker) {
        Caretaker existingCaretaker = caretakerRepository.findById(userId).orElse(null);
        if (existingCaretaker != null) {
            // Update profile fields
            existingCaretaker.setFirstName(updatedCaretaker.getFirstName());
            existingCaretaker.setLastName(updatedCaretaker.getLastName());
            existingCaretaker.setEmail(updatedCaretaker.getEmail());
            existingCaretaker.setPhone(updatedCaretaker.getPhone());
            existingCaretaker.setPhoto(updatedCaretaker.getPhoto());
            existingCaretaker.setAboutMe(updatedCaretaker.getAboutMe());
            existingCaretaker.setYearsOfExperience(updatedCaretaker.getYearsOfExperience());
            existingCaretaker.setAwards(updatedCaretaker.getAwards());

            // Save updated caretaker profile
            return caretakerRepository.save(existingCaretaker);
        }
        return null; // Handle error scenario
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Optional<Caretaker> caretakerOpt = caretakerRepository.findByEmail(email);
        if (!caretakerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email not found.");
        }

        // Generate a token and save it in the database with an expiration date
        String resetToken = UUID.randomUUID().toString();
        Caretaker caretaker = caretakerOpt.get();
        caretaker.setResetToken(resetToken);
        caretaker.setTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        caretakerRepository.save(caretaker);

        // TODO: Send the token to the user's email
        // ...
        emailService.sendPasswordResetEmail(caretaker.getEmail(), resetToken);


        return ResponseEntity.ok("Password reset email sent.");
    }



    public ResponseEntity<?> verifyAndUpdatePassword(PasswordChangeDto dto) {
        // Find the currently logged-in caretaker (e.g., from the security context)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLoggedInUserEmail = authentication.getName();

        // Retrieve the caretaker from the database using the email
        Optional<Caretaker> caretakerOpt = caretakerRepository.findByEmail(currentLoggedInUserEmail);

        if (!caretakerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Caretaker not found.");
        }

        Caretaker caretaker = caretakerOpt.get();


        // Check if the old password matches
        if (!passwordEncoder.matches(dto.getOldPassword(), caretaker.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is not correct.");
        }

        // Encode and set the new password
        caretaker.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        caretakerRepository.save(caretaker);

        return ResponseEntity.ok("Password updated successfully.");
    }

    // In CaretakerService

    public ResponseEntity<?> changePassword(String caretakerId, PasswordChangeDto passwordChangeDto) {
        Optional<Caretaker> caretakerOpt = caretakerRepository.findById(caretakerId);
        if (!caretakerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Caretaker not found.");
        }

        Caretaker caretaker = caretakerOpt.get();
        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), caretaker.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }

        caretaker.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        caretakerRepository.save(caretaker);

        return ResponseEntity.ok("Password updated successfully.");
    }
    public ResponseEntity<?> resetPassword(String token, String email,String newPassword) {
        // Find caretaker by reset token
        Optional<Caretaker> caretakerOpt = caretakerRepository.findByResetToken(token);

        // Check if caretaker with given token exists
        if (!caretakerOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Caretaker caretaker = caretakerOpt.get();

        // Check if the token has expired
        if (caretaker.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Password reset token has expired.");
        }

        // Update caretaker's password
        caretaker.setPassword(passwordEncoder.encode(newPassword));
        // Clear reset token and expiry date
        caretaker.setResetToken(null);
        caretaker.setTokenExpiryDate(null);

        // Save updated caretaker
        caretakerRepository.save(caretaker);

        return ResponseEntity.ok("Password reset successfully.");
    }
    public List<Caretaker> getAllCaretakers() {
        return caretakerRepository.findAll();
    }

}
