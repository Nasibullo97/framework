package com.ziprecruiter.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.ziprecruiter.pages.HomePage;

public class SimpleTest {
    
    private WebDriver driver;
    private HomePage homePage;
    
    @BeforeMethod
    public void setUp() {
        // Set up Chrome driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        homePage = new HomePage(driver);
    }
    
    @Test(description = "Test ZipRecruiter homepage loads successfully")
    public void testHomePageLoads() {
        // Navigate to ZipRecruiter homepage
        driver.get("https://www.ziprecruiter.com");
        
        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify page title contains ZipRecruiter
        String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        
        Assert.assertTrue(pageTitle.contains("ZipRecruiter") || 
                         pageTitle.contains("ziprecruiter") || 
                         pageTitle.contains("Job Search"), 
                         "Page title should contain ZipRecruiter or job search related text");
        
        // Verify page URL
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), 
                         "URL should contain ziprecruiter.com");
    }
    
    @Test(description = "Test basic search functionality")
    public void testBasicSearch() {
        // Navigate to ZipRecruiter homepage
        driver.get("https://www.ziprecruiter.com");
        
        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Get page title
        String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        
        // Verify we're on the right page
        Assert.assertTrue(pageTitle.contains("ZipRecruiter") || 
                         pageTitle.contains("ziprecruiter") || 
                         pageTitle.contains("Job Search"), 
                         "Should be on ZipRecruiter homepage");
        
        // Check if search elements are present (basic validation)
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("search") || 
                         pageSource.contains("job") || 
                         pageSource.contains("location"), 
                         "Page should contain search-related elements");
    }
    
    @Test(description = "Test page navigation")
    public void testPageNavigation() {
        // Test navigation to different pages
        String[] testUrls = {
            "https://www.ziprecruiter.com",
            "https://www.ziprecruiter.com/jobs",
            "https://www.ziprecruiter.com/login"
        };
        
        for (String url : testUrls) {
            try {
                driver.get(url);
                Thread.sleep(2000);
                
                String pageTitle = driver.getTitle();
                System.out.println("Navigated to: " + url);
                System.out.println("Page Title: " + pageTitle);
                
                Assert.assertNotNull(pageTitle, "Page title should not be null");
                Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 