package com.ziprecruiter.pages;

import org.openqa.selenium.WebDriver;
import com.ziprecruiter.utils.ElementUtils;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }
    
    public boolean isPageLoaded() {
        return driver.getTitle() != null && !driver.getTitle().isEmpty();
    }
    
    public void searchJobs(String jobTitle, String location) {
        // Basic implementation - navigate to search page
        if (jobTitle != null && !jobTitle.isEmpty()) {
            driver.get("https://www.ziprecruiter.com/jobs?search=" + jobTitle);
        } else {
            driver.get("https://www.ziprecruiter.com/jobs");
        }
    }
    
    public void searchJobsWithKeyboard(String jobTitle, String location) {
        // Basic implementation
        searchJobs(jobTitle, location);
    }
    
    public void navigateToLogin() {
        driver.get("https://www.ziprecruiter.com/login");
    }
    
    public void navigateToRegister() {
        driver.get("https://www.ziprecruiter.com/register");
    }
    
    public void navigateToJobs() {
        driver.get("https://www.ziprecruiter.com/jobs");
    }
    
    public void navigateToProfile() {
        driver.get("https://www.ziprecruiter.com/profile");
    }
} 