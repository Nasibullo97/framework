package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage {
    
    // Profile page locators
    private By editProfileButton = By.cssSelector(".edit-profile, [data-testid='edit-profile'], button:contains('Edit')");
    private By firstNameField = By.cssSelector("input[name='firstName'], input[name='first_name'], #firstName");
    private By lastNameField = By.cssSelector("input[name='lastName'], input[name='last_name'], #lastName");
    private By phoneField = By.cssSelector("input[name='phone'], input[type='tel'], #phone");
    private By locationField = By.cssSelector("input[name='location'], #location");
    private By bioField = By.cssSelector("textarea[name='bio'], #bio, .bio-textarea");
    private By saveButton = By.cssSelector("button[type='submit'], .save-button, [data-testid='save']");
    private By cancelButton = By.cssSelector(".cancel-button, [data-testid='cancel']");
    private By profileImage = By.cssSelector(".profile-image, img[alt*='profile']");
    private By uploadResumeButton = By.cssSelector(".upload-resume, input[type='file'], #resume");
    private By successMessage = By.cssSelector(".success-message, .alert-success");
    private By errorMessage = By.cssSelector(".error-message, .alert-danger");
    
    public ProfilePage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(editProfileButton) || isElementDisplayed(firstNameField);
    }
    
    public void clickEditProfile() {
        clickElement(editProfileButton);
    }
    
    public void updateFirstName(String firstName) {
        typeText(firstNameField, firstName);
    }
    
    public void updateLastName(String lastName) {
        typeText(lastNameField, lastName);
    }
    
    public void updatePhone(String phone) {
        typeText(phoneField, phone);
    }
    
    public void updateLocation(String location) {
        typeText(locationField, location);
    }
    
    public void updateBio(String bio) {
        typeText(bioField, bio);
    }
    
    public void clickSave() {
        clickElement(saveButton);
    }
    
    public void clickCancel() {
        clickElement(cancelButton);
    }
    
    public void uploadResume(String filePath) {
        // Handle file upload
        WebElement fileInput = waitForElement(uploadResumeButton);
        fileInput.sendKeys(filePath);
    }
    
    public void updateProfile(String firstName, String lastName, String phone, String location, String bio) {
        clickEditProfile();
        updateFirstName(firstName);
        updateLastName(lastName);
        updatePhone(phone);
        updateLocation(location);
        updateBio(bio);
        clickSave();
    }
    
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public String getCurrentFirstName() {
        return getText(firstNameField);
    }
    
    public String getCurrentLastName() {
        return getText(lastNameField);
    }
    
    public boolean isProfileUpdated() {
        return isSuccessMessageDisplayed();
    }
    
    public boolean isProfilePageLoaded() {
        return isPageLoaded();
    }
} 