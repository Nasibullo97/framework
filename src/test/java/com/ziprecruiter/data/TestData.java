package com.ziprecruiter.data;

public class TestData {
    private String email;
    private String password;
    private String jobTitle;
    private String location;
    
    public TestData(String email, String password, String jobTitle, String location) {
        this.email = email;
        this.password = password;
        this.jobTitle = jobTitle;
        this.location = location;
    }
    
    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getJobTitle() { return jobTitle; }
    public String getLocation() { return location; }
    
    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setLocation(String location) { this.location = location; }
    
    @Override
    public String toString() {
        return "TestData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
} 