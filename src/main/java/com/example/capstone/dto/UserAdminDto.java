package com.example.capstone.dto;

public class UserAdminDto {
    private String id;
    private String userName;
    private String userType; // "Caretaker" or "DogOwner"
    private boolean isActive;

    // Constructors, getters, and setters
    public UserAdminDto(String id, String userName, String userType, boolean isActive) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
