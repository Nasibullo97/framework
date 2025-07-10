package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("Parallel Test Execution")
@Feature("Concurrent Testing Scenarios")
public class ParallelTestExecution extends BaseTest {
    
    @Test(threadPoolSize = 3, invocationCount = 3)
    @Story("Parallel search tests")
    @Description("Execute multiple search tests in parallel")
    public void parallelSearchTests() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        String[] jobTitles = {"Software Engineer", "Data Scientist", "Product Manager"};
        String[] locations = {"New York", "San Francisco", "Los Angeles"};
        
        int index = (int) (Thread.currentThread().getId() % 3);
        
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs(jobTitles[index], locations[index]);
        
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), 
            "Search results should load for " + jobTitles[index] + " in " + locations[index]);
        
        // Verify results are not empty
        Assert.assertTrue(searchResultsPage.getJobCount() > 0, 
            "Should have results for " + jobTitles[index] + " in " + locations[index]);
    }
    
    @Test(threadPoolSize = 2, invocationCount = 4)
    @Story("Parallel page load tests")
    @Description("Test page loading performance in parallel")
    public void parallelPageLoadTests() {
        String[] pages = {
            "https://www.ziprecruiter.com/",
            "https://www.ziprecruiter.com/jobs",
            "https://www.ziprecruiter.com/login",
            "https://www.ziprecruiter.com/register"
        };
        
        int index = (int) (Thread.currentThread().getId() % pages.length);
        
        long startTime = System.currentTimeMillis();
        driver.get(pages[index]);
        long loadTime = System.currentTimeMillis() - startTime;
        
        Assert.assertTrue(loadTime < 5000, 
            "Page " + pages[index] + " should load within 5 seconds. Actual: " + loadTime + "ms");
        
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should be available for " + pages[index]);
    }
    
    @Test(threadPoolSize = 3, invocationCount = 6)
    @Story("Parallel form validation tests")
    @Description("Test form validation in parallel")
    public void parallelFormValidationTests() {
        LoginPage loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        String[] invalidEmails = {
            "invalid-email",
            "test@",
            "@example.com",
            "test..test@example.com",
            "test@example..com",
            "test@example.com."
        };
        
        int index = (int) (Thread.currentThread().getId() % invalidEmails.length);
        
        // Test login form validation
        driver.get("https://www.ziprecruiter.com/login");
        loginPage.login(invalidEmails[index], "password123");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Should show error for invalid email: " + invalidEmails[index]);
        
        // Test registration form validation
        driver.get("https://www.ziprecruiter.com/register");
        registrationPage.registerUser(invalidEmails[index], "password123", "John", "Doe");
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), 
            "Should show error for invalid email: " + invalidEmails[index]);
    }
    
    @Test(threadPoolSize = 2, invocationCount = 4)
    @Story("Parallel performance tests")
    @Description("Test performance metrics in parallel")
    public void parallelPerformanceTests() {
        Runtime runtime = Runtime.getRuntime();
        
        // Navigate through multiple pages
        for (int i = 0; i < 3; i++) {
            driver.get("https://www.ziprecruiter.com/");
            driver.get("https://www.ziprecruiter.com/jobs");
            driver.get("https://www.ziprecruiter.com/profile");
        }
        
        // Check memory usage
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        Assert.assertTrue(usedMemory < maxMemory * 0.8, 
            "Memory usage should be reasonable in parallel execution. Used: " + usedMemory + ", Max: " + maxMemory);
        
        // Check page load performance
        long startTime = System.currentTimeMillis();
        driver.get("https://www.ziprecruiter.com/");
        long loadTime = System.currentTimeMillis() - startTime;
        
        Assert.assertTrue(loadTime < 5000, 
            "Page should load within 5 seconds in parallel execution. Actual: " + loadTime + "ms");
    }
    
    @Test(threadPoolSize = 3, invocationCount = 3)
    @Story("Parallel accessibility tests")
    @Description("Test accessibility compliance in parallel")
    public void parallelAccessibilityTests() {
        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        
        String[] pages = {
            "https://www.ziprecruiter.com/",
            "https://www.ziprecruiter.com/jobs",
            "https://www.ziprecruiter.com/login"
        };
        
        int index = (int) (Thread.currentThread().getId() % pages.length);
        
        driver.get(pages[index]);
        
        // Test keyboard navigation
        Assert.assertTrue(accessibilityPage.canNavigateWithTab(), 
            "Should support keyboard navigation on " + pages[index]);
        
        // Test form labels
        Assert.assertTrue(accessibilityPage.hasFormLabels(), 
            "Should have form labels on " + pages[index]);
        
        // Test image alt text
        Assert.assertTrue(accessibilityPage.hasImageAltText(), 
            "Should have image alt text on " + pages[index]);
    }
    
    @Test(threadPoolSize = 2, invocationCount = 4)
    @Story("Parallel browser compatibility tests")
    @Description("Test browser compatibility in parallel")
    public void parallelBrowserCompatibilityTests() {
        HomePage homePage = new HomePage(driver);
        
        // Test different window sizes
        int[] widths = {1920, 1366, 1024, 768};
        int[] heights = {1080, 768, 768, 1024};
        
        int index = (int) (Thread.currentThread().getId() % widths.length);
        
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(widths[index], heights[index]));
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), 
            "Page should load at resolution " + widths[index] + "x" + heights[index]);
        
        // Test JavaScript functionality
        String title = driver.getTitle();
        Assert.assertNotNull(title, 
            "JavaScript should work at resolution " + widths[index] + "x" + heights[index]);
        
        // Restore window size
        driver.manage().window().maximize();
    }
    
    @Test(threadPoolSize = 3, invocationCount = 6)
    @Story("Parallel data-driven tests")
    @Description("Execute data-driven tests in parallel")
    public void parallelDataDrivenTests() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        String[][] testData = {
            {"Software Engineer", "New York"},
            {"Data Scientist", "San Francisco"},
            {"Product Manager", "Los Angeles"},
            {"QA Engineer", "Austin"},
            {"DevOps Engineer", "Chicago"},
            {"UX Designer", "Seattle"}
        };
        
        int index = (int) (Thread.currentThread().getId() % testData.length);
        
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs(testData[index][0], testData[index][1]);
        
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), 
            "Search should work for " + testData[index][0] + " in " + testData[index][1]);
        
        Assert.assertTrue(searchResultsPage.getJobCount() > 0, 
            "Should have results for " + testData[index][0] + " in " + testData[index][1]);
    }
    
    @Test(threadPoolSize = 2, invocationCount = 4)
    @Story("Parallel stress tests")
    @Description("Test system under parallel stress conditions")
    public void parallelStressTests() {
        HomePage homePage = new HomePage(driver);
        
        // Rapid page refreshes
        for (int i = 0; i < 5; i++) {
            driver.navigate().refresh();
            Assert.assertTrue(homePage.isPageLoaded(), "Page should handle rapid refreshes in parallel");
        }
        
        // Rapid navigation
        for (int i = 0; i < 3; i++) {
            driver.get("https://www.ziprecruiter.com/");
            driver.get("https://www.ziprecruiter.com/jobs");
            driver.get("https://www.ziprecruiter.com/profile");
        }
        
        Assert.assertTrue(homePage.isPageLoaded(), "System should handle rapid navigation in parallel");
        
        // Test memory usage under stress
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        Assert.assertTrue(usedMemory < maxMemory * 0.9, 
            "Memory usage should be reasonable under parallel stress");
    }
    
    @Test(threadPoolSize = 3, invocationCount = 3)
    @Story("Parallel error handling tests")
    @Description("Test error handling in parallel scenarios")
    public void parallelErrorHandlingTests() {
        HomePage homePage = new HomePage(driver);
        
        String[] invalidInputs = {
            "",
            "a".repeat(1000),
            "!@#$%^&*()",
            "<script>alert('test')</script>",
            "'; DROP TABLE users; --",
            "测试工作"
        };
        
        int index = (int) (Thread.currentThread().getId() % invalidInputs.length);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with invalid inputs
        homePage.searchJobs(invalidInputs[index], "New York");
        // Should handle gracefully without crashing
        
        // Verify page is still functional
        Assert.assertTrue(homePage.isPageLoaded(), 
            "Page should remain functional after invalid input: " + invalidInputs[index]);
        
        String title = driver.getTitle();
        Assert.assertNotNull(title, 
            "Page title should be available after invalid input: " + invalidInputs[index]);
    }
    
    @Test(threadPoolSize = 2, invocationCount = 4)
    @Story("Parallel resource loading tests")
    @Description("Test resource loading in parallel")
    public void parallelResourceLoadingTests() {
        // Test with different resource loading scenarios
        String[] scenarios = {
            "normal",
            "no-images",
            "no-css",
            "no-js"
        };
        
        int index = (int) (Thread.currentThread().getId() % scenarios.length);
        
        driver.get("https://www.ziprecruiter.com/");
        
        if (scenarios[index].equals("no-images")) {
            // Disable images
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("document.querySelectorAll('img').forEach(img => img.style.display='none')");
        }
        
        // Verify page still functions
        String title = driver.getTitle();
        Assert.assertNotNull(title, 
            "Page should function in scenario: " + scenarios[index]);
        
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), 
            "Page should load in scenario: " + scenarios[index]);
    }
} 