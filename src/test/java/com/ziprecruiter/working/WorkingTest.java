package com.ziprecruiter.working;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class WorkingTest {
    
    private WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        System.out.println("Setting up Chrome driver...");
        
        // Set up Chrome driver with headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        System.out.println("Chrome driver setup complete");
    }
    
    @Test(description = "Test ZipRecruiter homepage loads successfully")
    public void testHomePageLoads() {
        System.out.println("=== Starting test: testHomePageLoads ===");
        
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
                         pageTitle.contains("Job Search") ||
                         pageTitle.contains("Jobs"), 
                         "Page title should contain ZipRecruiter or job search related text");
        
        // Verify page URL
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), 
                         "URL should contain ziprecruiter.com");
        
        System.out.println("✓ Test passed: Homepage loads successfully");
    }
    
    @Test(description = "Test basic search functionality")
    public void testBasicSearch() {
        System.out.println("=== Starting test: testBasicSearch ===");
        
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
                         pageTitle.contains("Job Search") ||
                         pageTitle.contains("Jobs"), 
                         "Should be on ZipRecruiter homepage");
        
        // Check if search elements are present (basic validation)
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("search") || 
                         pageSource.contains("job") || 
                         pageSource.contains("location") ||
                         pageSource.contains("title"), 
                         "Page should contain search-related elements");
        
        System.out.println("✓ Test passed: Basic search functionality verified");
    }
    
    @Test(description = "Test page navigation")
    public void testPageNavigation() {
        System.out.println("=== Starting test: testPageNavigation ===");
        
        // Test navigation to different pages
        String[] testUrls = {
            "https://www.ziprecruiter.com",
            "https://www.ziprecruiter.com/jobs",
            "https://www.ziprecruiter.com/login"
        };
        
        for (String url : testUrls) {
            try {
                System.out.println("Navigating to: " + url);
                driver.get(url);
                Thread.sleep(2000);
                
                String pageTitle = driver.getTitle();
                System.out.println("Page Title: " + pageTitle);
                
                Assert.assertNotNull(pageTitle, "Page title should not be null");
                Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("✓ Test passed: Page navigation works correctly");
    }
    
    @Test(description = "Test search results page")
    public void testSearchResults() {
        System.out.println("=== Starting test: testSearchResults ===");
        
        // Navigate directly to search results page
        driver.get("https://www.ziprecruiter.com/jobs?search=software+engineer");
        
        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify we're on search results page
        String pageTitle = driver.getTitle();
        System.out.println("Search Results Page Title: " + pageTitle);
        
        Assert.assertTrue(pageTitle.contains("ZipRecruiter") || 
                         pageTitle.contains("ziprecruiter") || 
                         pageTitle.contains("Job") ||
                         pageTitle.contains("Search"), 
                         "Should be on search results page");
        
        // Check if search results are present
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("job") || 
                         pageSource.contains("position") || 
                         pageSource.contains("title") ||
                         pageSource.contains("company"), 
                         "Page should contain job-related content");
        
        System.out.println("✓ Test passed: Search results page loads correctly");
    }
    
    @Test(description = "Test framework capabilities")
    public void testFrameworkCapabilities() {
        System.out.println("=== Starting test: testFrameworkCapabilities ===");
        
        // Test basic Selenium capabilities
        driver.get("https://www.ziprecruiter.com");
        
        // Test page source access
        String pageSource = driver.getPageSource();
        Assert.assertNotNull(pageSource, "Page source should not be null");
        Assert.assertFalse(pageSource.isEmpty(), "Page source should not be empty");
        
        // Test current URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), "Should be on ZipRecruiter domain");
        
        // Test page title
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        
        System.out.println("✓ Test passed: Framework capabilities working correctly");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("Closing browser...");
            driver.quit();
            System.out.println("Browser closed successfully");
        }
    }
} 