package com.example.capstone.service;

import com.example.capstone.model.Caretaker;
import com.example.capstone.repository.CaretakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CaretakerService {

    @Autowired
    private CaretakerRepository caretakerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
}
