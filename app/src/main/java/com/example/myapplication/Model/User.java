package com.example.myapplication.Model;

public class User {
    public String getImageURL;
    private String id;
    private String username;
    private String imageURL;
    private String role;

    public User(String id, String username, String imageURL, String role) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.role = role;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
