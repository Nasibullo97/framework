package com.ziprecruiter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private By emailField = By.name("email");
    private By continueButton = By.cssSelector("button[type='submit']");
    // Try multiple selectors for password field
    private List<By> passwordFieldLocators = Arrays.asList(
        By.id("login_password"),
        By.name("password"),
        By.cssSelector("input[type='password']")
    );
    private By loginButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
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
        driver.findElement(loginButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickLogin();
    }
}