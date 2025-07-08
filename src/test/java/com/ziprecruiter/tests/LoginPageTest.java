package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginPageTest extends BaseTest {
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"abdullayevbillo772@gmail.com", "PRIVATE_PASSWORD"},
            {"testuser@example.com", "PRIVATE_PASSWORD"},
            {"anotheruser@example.com", "PRIVATE_PASSWORD"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLoginWithInvalidPassword(String email, String password) {
        driver.get("https://www.ziprecruiter.com/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Wait for the email field and re-locate it before use
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.clear();
        emailField.sendKeys(email);
        // Click Continue and wait for password field
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Wait for any password field to appear and re-locate it
        WebElement passwordField = wait.until(d -> {
            for (By locator : new By[]{
                    By.id("login_password"),
                    By.name("password"),
                    By.cssSelector("input[type='password']")
            }) {
                for (WebElement el : d.findElements(locator)) {
                    if (el.isDisplayed()) return el;
                }
            }
            return null;
        });
        if (passwordField == null) {
            System.out.println("Password field not found with any generic selector");
            Assert.fail("Password field not found");
        }
        passwordField.clear();
        passwordField.sendKeys(password); // Use real password for real test
        // Click Login (button may be same as Continue)
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Wait for error message
        By errorMsg = By.cssSelector(".form-error-message, .error-message, .alert-danger");
        boolean errorDisplayed = wait.until(d -> d.findElements(errorMsg).size() > 0);
        Assert.assertTrue(errorDisplayed, "Error message should be displayed for invalid login");
    }
}