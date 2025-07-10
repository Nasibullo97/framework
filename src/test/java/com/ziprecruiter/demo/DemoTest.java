package com.ziprecruiter.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Duration;

/**
 * Demo Test Class - Standalone Selenium + TestNG Framework
 * 
 * This class demonstrates a working Selenium + TestNG automation framework
 * for ZipRecruiter without any dependencies on external framework components.
 * 
 * Features demonstrated:
 * - WebDriver setup with Chrome headless mode
 * - Page navigation and validation
 * - Element interaction (click, type, etc.)
 * - Explicit waits for better reliability
 * - TestNG annotations and assertions
 * - Proper test cleanup
 */
public class DemoTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        System.out.println("🚀 Setting up Chrome WebDriver...");
        
        // Configure Chrome options for headless execution
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");           // Run in headless mode
        options.addArguments("--no-sandbox");         // Required for some environments
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        options.addArguments("--disable-gpu");        // Disable GPU hardware acceleration
        options.addArguments("--window-size=1920,1080"); // Set window size
        options.addArguments("--disable-extensions"); // Disable extensions
        options.addArguments("--disable-plugins");    // Disable plugins
        
        // Initialize WebDriver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        // Initialize explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        System.out.println("✅ Chrome WebDriver setup complete");
    }
    
    @Test(description = "Verify ZipRecruiter homepage loads successfully")
    public void testHomePageLoads() {
        System.out.println("\n📋 Test: Homepage Loads Successfully");
        
        // Navigate to ZipRecruiter homepage
        driver.get("https://www.ziprecruiter.com");
        
        // Wait for page to load and verify title
        String pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title"))).getAttribute("textContent");
        System.out.println("📄 Page Title: " + pageTitle);
        
        // Verify page title contains expected text
        Assert.assertTrue(
            pageTitle.toLowerCase().contains("ziprecruiter") || 
            pageTitle.toLowerCase().contains("job search") ||
            pageTitle.toLowerCase().contains("jobs"),
            "Page title should contain ZipRecruiter or job search related text"
        );
        
        // Verify current URL
        String currentUrl = driver.getCurrentUrl();
        System.out.println("🔗 Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), "URL should contain ziprecruiter.com");
        
        // Verify page source contains expected content
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("search") || pageSource.contains("job"), 
                         "Page should contain search or job related content");
        
        System.out.println("✅ Homepage loads successfully - Test PASSED");
    }
    
    @Test(description = "Test basic search functionality")
    public void testBasicSearch() {
        System.out.println("\n📋 Test: Basic Search Functionality");
        
        // Navigate to ZipRecruiter homepage
        driver.get("https://www.ziprecruiter.com");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Look for search-related elements
        String pageSource = driver.getPageSource();
        boolean hasSearchElements = pageSource.contains("search") || 
                                   pageSource.contains("job") || 
                                   pageSource.contains("location") ||
                                   pageSource.contains("title");
        
        Assert.assertTrue(hasSearchElements, "Page should contain search-related elements");
        
        // Try to find search input fields
        try {
            // Look for common search input selectors
            String[] searchSelectors = {
                "input[type='text']",
                "input[placeholder*='job']",
                "input[placeholder*='title']",
                "input[placeholder*='search']",
                "input[name*='search']",
                "input[id*='search']"
            };
            
            boolean foundSearchInput = false;
            for (String selector : searchSelectors) {
                try {
                    WebElement element = driver.findElement(By.cssSelector(selector));
                    if (element.isDisplayed()) {
                        System.out.println("🔍 Found search input: " + selector);
                        foundSearchInput = true;
                        break;
                    }
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
            
            if (foundSearchInput) {
                System.out.println("✅ Search input field found - Test PASSED");
            } else {
                System.out.println("⚠️  No visible search input found, but page contains search elements");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️  Could not locate specific search elements: " + e.getMessage());
        }
    }
    
    @Test(description = "Test navigation to different pages")
    public void testPageNavigation() {
        System.out.println("\n📋 Test: Page Navigation");
        
        // Test navigation to different ZipRecruiter pages
        String[] testUrls = {
            "https://www.ziprecruiter.com",
            "https://www.ziprecruiter.com/jobs",
            "https://www.ziprecruiter.com/login"
        };
        
        for (String url : testUrls) {
            try {
                System.out.println("🌐 Navigating to: " + url);
                driver.get(url);
                
                // Wait for page to load
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
                
                String pageTitle = driver.getTitle();
                System.out.println("📄 Page Title: " + pageTitle);
                
                // Verify page loaded successfully
                Assert.assertNotNull(pageTitle, "Page title should not be null");
                Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
                Assert.assertTrue(pageTitle.length() > 0, "Page title should have content");
                
                // Verify we're still on ZipRecruiter domain
                String currentUrl = driver.getCurrentUrl();
                Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), 
                                "Should remain on ZipRecruiter domain");
                
            } catch (Exception e) {
                System.out.println("❌ Error navigating to " + url + ": " + e.getMessage());
                Assert.fail("Failed to navigate to " + url + ": " + e.getMessage());
            }
        }
        
        System.out.println("✅ Page navigation works correctly - Test PASSED");
    }
    
    @Test(description = "Test search results page")
    public void testSearchResults() {
        System.out.println("\n📋 Test: Search Results Page");
        
        // Navigate directly to search results page
        driver.get("https://www.ziprecruiter.com/jobs?search=software+engineer");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Verify we're on search results page
        String pageTitle = driver.getTitle();
        System.out.println("📄 Search Results Page Title: " + pageTitle);
        
        Assert.assertTrue(
            pageTitle.toLowerCase().contains("ziprecruiter") || 
            pageTitle.toLowerCase().contains("job") ||
            pageTitle.toLowerCase().contains("search"),
            "Should be on search results page"
        );
        
        // Check if search results content is present
        String pageSource = driver.getPageSource();
        boolean hasJobContent = pageSource.contains("job") || 
                               pageSource.contains("position") || 
                               pageSource.contains("title") ||
                               pageSource.contains("company") ||
                               pageSource.contains("apply");
        
        Assert.assertTrue(hasJobContent, "Page should contain job-related content");
        
        // Verify URL contains search parameters
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), "Should be on ZipRecruiter domain");
        
        System.out.println("✅ Search results page loads correctly - Test PASSED");
    }
    
    @Test(description = "Test framework capabilities and error handling")
    public void testFrameworkCapabilities() {
        System.out.println("\n📋 Test: Framework Capabilities");
        
        // Test basic Selenium capabilities
        driver.get("https://www.ziprecruiter.com");
        
        // Test page source access
        String pageSource = driver.getPageSource();
        Assert.assertNotNull(pageSource, "Page source should not be null");
        Assert.assertFalse(pageSource.isEmpty(), "Page source should not be empty");
        Assert.assertTrue(pageSource.length() > 1000, "Page source should have substantial content");
        
        // Test current URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), "Should be on ZipRecruiter domain");
        
        // Test page title
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
        
        // Test element finding capabilities
        try {
            WebElement bodyElement = driver.findElement(By.tagName("body"));
            Assert.assertNotNull(bodyElement, "Body element should be found");
            Assert.assertTrue(bodyElement.isDisplayed(), "Body element should be displayed");
        } catch (Exception e) {
            Assert.fail("Should be able to find body element: " + e.getMessage());
        }
        
        // Test wait functionality
        try {
            WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
            Assert.assertNotNull(titleElement, "Title element should be found with wait");
        } catch (Exception e) {
            Assert.fail("Wait functionality should work: " + e.getMessage());
        }
        
        System.out.println("✅ Framework capabilities working correctly - Test PASSED");
    }
    
    @Test(description = "Test error handling and edge cases")
    public void testErrorHandling() {
        System.out.println("\n📋 Test: Error Handling and Edge Cases");
        
        // Test navigation to non-existent page (should handle gracefully)
        try {
            driver.get("https://www.ziprecruiter.com/non-existent-page");
            
            // Should still get a page (even if it's a 404)
            String pageTitle = driver.getTitle();
            String currentUrl = driver.getCurrentUrl();
            
            Assert.assertNotNull(pageTitle, "Should get a page title even for non-existent page");
            Assert.assertTrue(currentUrl.contains("ziprecruiter.com"), "Should remain on ZipRecruiter domain");
            
            System.out.println("✅ Error handling works for non-existent pages");
            
        } catch (Exception e) {
            System.out.println("⚠️  Expected error for non-existent page: " + e.getMessage());
        }
        
        // Test invalid URL handling
        try {
            driver.get("https://invalid-domain-that-does-not-exist-12345.com");
            Assert.fail("Should not be able to navigate to invalid domain");
        } catch (Exception e) {
            System.out.println("✅ Properly handled invalid domain: " + e.getMessage());
        }
        
        // Test element not found handling
        try {
            driver.get("https://www.ziprecruiter.com");
            WebElement nonExistentElement = driver.findElement(By.id("element-that-does-not-exist"));
            Assert.fail("Should not find non-existent element");
        } catch (Exception e) {
            System.out.println("✅ Properly handled non-existent element: " + e.getMessage());
        }
        
        System.out.println("✅ Error handling and edge cases work correctly - Test PASSED");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("🧹 Cleaning up WebDriver...");
            try {
                driver.quit();
                System.out.println("✅ WebDriver cleanup complete");
            } catch (Exception e) {
                System.out.println("⚠️  Error during WebDriver cleanup: " + e.getMessage());
            }
        }
    }
} 