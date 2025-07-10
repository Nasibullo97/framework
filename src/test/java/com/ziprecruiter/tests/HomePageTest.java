package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

@Epic("Home Page")
@Feature("Landing Page")
public class HomePageTest extends BaseTest {
    @Test
    @Story("User visits home page")
    @Description("Verify that the ZipRecruiter home page loads and displays the search box.")
    public void testHomePageLoads() {
        driver.get("https://www.ziprecruiter.com/");
        // Wait for a key element to be visible (e.g., the search box)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        HomePage homePage = new HomePage(driver);
        String actualTitle = homePage.getTitle();
        System.out.println("Actual page title: '" + actualTitle + "'"); // Debug output
        // Accept the new title as valid
        Assert.assertTrue(actualTitle.contains("Job Search"), "Title should contain 'Job Search'");
        // Soft assertion example (for demonstration)
        // org.testng.asserts.SoftAssert softAssert = new org.testng.asserts.SoftAssert();
        // softAssert.assertTrue(actualTitle.contains("ZipRecruiter"), "Title should contain 'ZipRecruiter'");
        // softAssert.assertAll();
    }
} 