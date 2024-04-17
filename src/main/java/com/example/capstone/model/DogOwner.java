package com.example.capstone.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class DogOwner {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String photo; // Optional
    private String resetToken;
    private String dogName;

    private String aboutDog;

    private LocalDateTime tokenExpiryDate;
    private boolean isActive=true;

    public DogOwner() {
    }

    public DogOwner(String id, String firstName, String lastName, String email, String password, String phone, String photo, String resetToken, String dogName, String aboutDog, LocalDateTime tokenExpiryDate, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.photo = photo;
        this.resetToken = resetToken;
        this.dogName = dogName;
        this.aboutDog = aboutDog;
        this.tokenExpiryDate = tokenExpiryDate;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setResetToken(String resetToken) {
    }

    public String getResetToken() {
        return resetToken;
    }

    public LocalDateTime getTokenExpiryDate() {
        return tokenExpiryDate;
    }

    public void setTokenExpiryDate(LocalDateTime tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getAboutDog() {
        return aboutDog;
    }

    public void setAboutDog(String aboutDog) {
        this.aboutDog = aboutDog;
    }
}