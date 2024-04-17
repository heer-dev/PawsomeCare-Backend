package com.example.capstone.controller;

import com.example.capstone.dto.AppointmentRequestDetailsDto;
import com.example.capstone.model.AppointmentRequest;
import com.example.capstone.service.AppointmentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentRequestController {
    private final AppointmentRequestService appointmentRequestService;

    public AppointmentRequestController(AppointmentRequestService appointmentRequestService) {
        this.appointmentRequestService = appointmentRequestService;
    }

    @PostMapping
    public AppointmentRequest requestAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return appointmentRequestService.createAppointment(appointmentRequest);
    }

    @GetMapping("/{caretakerId}")
    public List<AppointmentRequest> getAppointments(@PathVariable String caretakerId) {
        return appointmentRequestService.getAppointmentsForCaretaker(caretakerId);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentRequest respondToAppointment(@PathVariable String appointmentId, @RequestParam String status) {
        return appointmentRequestService.updateAppointmentStatus(appointmentId, status);
    }

    @GetMapping("/details/{caretakerId}")
    public ResponseEntity<List<AppointmentRequestDetailsDto>> getAppointmentsDetails(@PathVariable String caretakerId) {
        try {
            List<AppointmentRequestDetailsDto> appointments = appointmentRequestService.getAppointmentsDetailsForCaretaker(caretakerId);
            if (appointments.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            e.printStackTrace(); // Add this to log the exception
            return ResponseEntity.badRequest().body(null);
        }
    }

}