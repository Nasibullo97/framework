package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends BasePage {
    private By emailField = By.name("email");
    private By continueButton = By.cssSelector("button[type='submit']");
    // Try multiple selectors for password field
    private List<By> passwordFieldLocators = Arrays.asList(
        By.id("login_password"),
        By.name("password"),
        By.cssSelector("input[type='password']")
    );
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".error-message, .alert-danger, [data-testid='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(emailField);
    }

    public void enterEmail(String email) {
        typeText(emailField, email);
    }

    public void clickContinue() {
        clickElement(continueButton);
    }

    public WebElement getPasswordField() {
        for (By locator : passwordFieldLocators) {
            List<WebElement> found = driver.findElements(locator);
            if (!found.isEmpty()) {
                return found.get(0);
            }
        }
        return null;
    }

    public void enterPassword(String password) {
        WebElement passwordField = getPasswordField();
        if (passwordField != null) {
            passwordField.sendKeys(password);
        } else {
            throw new RuntimeException("Password field not found with any generic selector");
        }
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void login(String email, String password) {
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickLogin();
    }
    
    public boolean isLoginPageLoaded() {
        return isPageLoaded();
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}