package com.example.capstone.controller;

import com.example.capstone.dto.UserAdminDto;
import com.example.capstone.model.Caretaker;
import com.example.capstone.model.DogOwner;
import com.example.capstone.repository.CaretakerRepository;
import com.example.capstone.repository.DogOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CaretakerRepository caretakerRepository;

    @Autowired
    private DogOwnerRepository dogOwnerRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserAdminDto>> getAllUsers(@RequestParam(required = false) String search) {
        List<UserAdminDto> users = new ArrayList<>();

        Stream<Caretaker> caretakersStream = search == null ? caretakerRepository.findAll().stream() :
                caretakerRepository.findByIdContainingOrFirstNameContainingOrLastNameContaining(search, search, search);

        Stream<DogOwner> dogOwnersStream = search == null ? dogOwnerRepository.findAll().stream() :
                dogOwnerRepository.findByIdContainingOrFirstNameContainingOrLastNameContaining(search, search, search);

        List<UserAdminDto> caretakers = caretakersStream
                .map(c -> new UserAdminDto(c.getId(), c.getFirstName() + " " + c.getLastName(), "Caretaker", c.isActive()))
                .collect(Collectors.toList());

        List<UserAdminDto> dogOwners = dogOwnersStream
                .map(d -> new UserAdminDto(d.getId(), d.getFirstName() + " " + d.getLastName(), "DogOwner", d.isActive()))
                .collect(Collectors.toList());

        users.addAll(caretakers);
        users.addAll(dogOwners);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String userId, @RequestParam String userType) {
        switch (userType.toLowerCase()) {
            case "caretaker":
                return deleteCaretaker(userId);
            case "dogowner":
                return deleteDogOwner(userId);
            default:
                return ResponseEntity.badRequest().body("Invalid user type specified.");
        }
    }

    private ResponseEntity<?> deleteCaretaker(String id) {
        if (!caretakerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        caretakerRepository.deleteById(id);
        return ResponseEntity.ok("Caretaker deleted successfully.");
    }

    private ResponseEntity<?> deleteDogOwner(String id) {
        if (!dogOwnerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dogOwnerRepository.deleteById(id);
        return ResponseEntity.ok("Dog Owner deleted successfully.");
    }

    @PutMapping("/toggle-status")
    @Transactional
    public ResponseEntity<?> toggleUserStatus(@RequestParam String userId, @RequestParam String userType) {
        switch (userType.toLowerCase()) {
            case "caretaker":
                return toggleCaretakerStatus(userId);
            case "dogowner":
                return toggleDogOwnerStatus(userId);
            default:
                return ResponseEntity.badRequest().body("Invalid user type specified.");
        }
    }

    private ResponseEntity<?> toggleCaretakerStatus(String id) {
        return caretakerRepository.findById(id)
                .map(caretaker -> {
                    caretaker.setActive(!caretaker.isActive());  // Toggle the status
                    caretakerRepository.save(caretaker);
                    return ResponseEntity.ok("Caretaker status toggled successfully.");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<?> toggleDogOwnerStatus(String id) {
        return dogOwnerRepository.findById(id)
                .map(dogOwner -> {
                    dogOwner.setActive(!dogOwner.isActive());  // Toggle the status
                    dogOwnerRepository.save(dogOwner);
                    return ResponseEntity.ok("Dog Owner status toggled successfully.");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user-counts")
    public ResponseEntity<Map<String, Long>> getUserCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("totalUsers", caretakerRepository.count() + dogOwnerRepository.count());
        counts.put("totalCaretakers", caretakerRepository.count());
        counts.put("totalDogOwners", dogOwnerRepository.count());
        return ResponseEntity.ok(counts);
    }

}
