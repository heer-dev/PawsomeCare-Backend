package com.example.capstone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String title;
    private Date date;
    private String location;
    private String time;
    private String image;
    private String organizationDetails;

    public Event() {
    }

    public Event(String id, String title, Date date, String location, String time, String image, String organizationDetails) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.time = time;
        this.image = image;
        this.organizationDetails = organizationDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrganizationDetails() {
        return organizationDetails;
    }

    public void setOrganizationDetails(String organizationDetails) {
        this.organizationDetails = organizationDetails;
    }
}