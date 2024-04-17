package com.example.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceDogOwner {

    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Pawsomecare12@gmail.com"); // Replace with the email that is actually sending the email
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + "http://localhost:3000/ForgetPasswordPage2?token=" + token);

        mailSender.send(message);
    }
}
