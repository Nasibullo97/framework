package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {
    
    // Registration form locators
    private By emailField = By.cssSelector("input[name='email'], input[type='email'], #email");
    private By passwordField = By.cssSelector("input[name='password'], input[type='password'], #password");
    private By confirmPasswordField = By.cssSelector("input[name='confirmPassword'], input[name='password_confirm'], #confirmPassword");
    private By firstNameField = By.cssSelector("input[name='firstName'], input[name='first_name'], #firstName");
    private By lastNameField = By.cssSelector("input[name='lastName'], input[name='last_name'], #lastName");
    private By registerButton = By.cssSelector("button[type='submit'], .register-button, [data-testid='register']");
    private By termsCheckbox = By.cssSelector("input[type='checkbox'], .terms-checkbox, #terms");
    private By errorMessage = By.cssSelector(".error-message, .form-error, .alert-danger");
    
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(emailField) && isElementDisplayed(registerButton);
    }
    
    public void enterEmail(String email) {
        typeText(emailField, email);
    }
    
    public void enterPassword(String password) {
        typeText(passwordField, password);
    }
    
    public void enterConfirmPassword(String password) {
        typeText(confirmPasswordField, password);
    }
    
    public void enterFirstName(String firstName) {
        typeText(firstNameField, firstName);
    }
    
    public void enterLastName(String lastName) {
        typeText(lastNameField, lastName);
    }
    
    public void acceptTerms() {
        clickElement(termsCheckbox);
    }
    
    public void clickRegister() {
        clickElement(registerButton);
    }
    
    public void registerUser(String email, String password, String firstName, String lastName) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        enterFirstName(firstName);
        enterLastName(lastName);
        acceptTerms();
        clickRegister();
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public boolean isRegistrationSuccessful() {
        // Check for success message or redirect to dashboard
        return !isErrorMessageDisplayed() && (driver.getCurrentUrl().contains("dashboard") || 
               driver.getCurrentUrl().contains("profile") || 
               !driver.getCurrentUrl().contains("register"));
    }
    
    public boolean isRegistrationPageLoaded() {
        return isPageLoaded();
    }
} 