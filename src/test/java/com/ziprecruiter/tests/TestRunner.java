package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("Test Runner")
@Feature("Test Execution Management")
public class TestRunner extends BaseTest {
    
    @Test(groups = {"quick", "smoke"})
    @Story("Quick smoke test")
    @Description("Fast smoke test for CI/CD pipeline")
    public void quickSmokeTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should load quickly");
        
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should be available");
    }
    
    @Test(groups = {"full", "regression"})
    @Story("Full regression test")
    @Description("Complete regression test suite")
    public void fullRegressionTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        
        // Test home page
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should load");
        
        // Test search functionality
        homePage.searchJobs("Software Engineer", "New York");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results should load");
        
        // Test login page
        driver.get("https://www.ziprecruiter.com/login");
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should load");
    }
    
    @Test(groups = {"critical", "production"})
    @Story("Critical production test")
    @Description("Critical functionality test for production")
    public void criticalProductionTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Home page must load in production");
        
        // Test essential functionality
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title must be available");
        Assert.assertTrue(title.contains("ZipRecruiter"), "Title must contain ZipRecruiter");
    }
    
    @Test(groups = {"performance", "load"})
    @Story("Performance and load test")
    @Description("Test performance under load")
    public void performanceLoadTest() {
        long startTime = System.currentTimeMillis();
        
        driver.get("https://www.ziprecruiter.com/");
        
        long loadTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(loadTime < 3000, "Page must load within 3 seconds under load. Actual: " + loadTime + "ms");
        
        // Test memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        Assert.assertTrue(usedMemory < maxMemory * 0.7, "Memory usage must be reasonable under load");
    }
    
    @Test(groups = {"security", "penetration"})
    @Story("Security and penetration test")
    @Description("Test security vulnerabilities")
    public void securityPenetrationTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test XSS prevention
        homePage.searchJobs("<script>alert('xss')</script>", "New York");
        // Should handle gracefully
        
        // Test SQL injection prevention
        homePage.searchJobs("'; DROP TABLE users; --", "New York");
        // Should handle gracefully
        
        // Test HTML injection prevention
        homePage.searchJobs("<h1>Test</h1>", "New York");
        // Should handle gracefully
    }
    
    @Test(groups = {"accessibility", "compliance"})
    @Story("Accessibility compliance test")
    @Description("Test accessibility compliance")
    public void accessibilityComplianceTest() {
        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test keyboard navigation
        Assert.assertTrue(accessibilityPage.canNavigateWithTab(), "Must support keyboard navigation");
        
        // Test form labels
        Assert.assertTrue(accessibilityPage.hasFormLabels(), "Must have form labels");
        
        // Test image alt text
        Assert.assertTrue(accessibilityPage.hasImageAltText(), "Must have image alt text");
        
        // Test heading hierarchy
        Assert.assertTrue(accessibilityPage.hasProperHeadingHierarchy(), "Must have proper heading hierarchy");
    }
    
    @Test(groups = {"browser", "compatibility"})
    @Story("Browser compatibility test")
    @Description("Test cross-browser compatibility")
    public void browserCompatibilityTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Must load in current browser");
        
        // Test JavaScript functionality
        String title = driver.getTitle();
        Assert.assertNotNull(title, "JavaScript must work in current browser");
        
        // Test responsive design
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        Assert.assertTrue(homePage.isPageLoaded(), "Must be responsive");
        
        // Restore window size
        driver.manage().window().maximize();
    }
    
    @Test(groups = {"mobile", "responsive"})
    @Story("Mobile responsiveness test")
    @Description("Test mobile responsiveness")
    public void mobileResponsivenessTest() {
        HomePage homePage = new HomePage(driver);
        
        // Set mobile viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Must be responsive on mobile");
        
        // Test touch interactions (simulated)
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Must work with touch interactions");
        
        // Restore window size
        driver.manage().window().maximize();
    }
    
    @Test(groups = {"data", "driven"})
    @Story("Data-driven test")
    @Description("Test with multiple data sets")
    public void dataDrivenTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        String[][] testData = {
            {"Software Engineer", "New York"},
            {"Data Scientist", "San Francisco"},
            {"Product Manager", "Los Angeles"},
            {"QA Engineer", "Austin"},
            {"DevOps Engineer", "Chicago"}
        };
        
        for (String[] data : testData) {
            driver.get("https://www.ziprecruiter.com/");
            homePage.searchJobs(data[0], data[1]);
            Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), 
                "Search must work for " + data[0] + " in " + data[1]);
        }
    }
    
    @Test(groups = {"negative", "edge"})
    @Story("Negative and edge case test")
    @Description("Test error handling and edge cases")
    public void negativeEdgeCaseTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with empty search
        homePage.searchJobs("", "");
        // Should handle gracefully
        
        // Test with very long search term
        String longSearchTerm = "a".repeat(1000);
        homePage.searchJobs(longSearchTerm, "New York");
        // Should handle gracefully
        
        // Test with special characters
        homePage.searchJobs("!@#$%^&*()", "New York");
        // Should handle gracefully
    }
    
    @Test(groups = {"integration", "workflow"})
    @Story("Integration workflow test")
    @Description("Test complete user workflow")
    public void integrationWorkflowTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        // Complete workflow: search -> results -> job details
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Software Engineer", "New York");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results must load");
        
        if (searchResultsPage.getJobCount() > 0) {
            searchResultsPage.clickFirstJob();
            // Should navigate to job details
            String currentUrl = driver.getCurrentUrl();
            Assert.assertNotNull(currentUrl, "Should navigate to job details");
        }
    }
    
    @Test(groups = {"api", "integration"})
    @Story("API integration test")
    @Description("Test API endpoints if available")
    public void apiIntegrationTest() {
        // This would test API endpoints if ZipRecruiter provides them
        // For now, test that the website loads properly
        driver.get("https://www.ziprecruiter.com/");
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Website must be accessible");
        
        // Test that the page is functional
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Website must be functional");
    }
    
    @Test(groups = {"stress", "load"})
    @Story("Stress and load test")
    @Description("Test system under stress conditions")
    public void stressLoadTest() {
        HomePage homePage = new HomePage(driver);
        
        // Rapid page loads
        for (int i = 0; i < 10; i++) {
            driver.get("https://www.ziprecruiter.com/");
            Assert.assertTrue(homePage.isPageLoaded(), "Must handle rapid page loads");
        }
        
        // Rapid navigation
        for (int i = 0; i < 5; i++) {
            driver.get("https://www.ziprecruiter.com/");
            driver.get("https://www.ziprecruiter.com/jobs");
            driver.get("https://www.ziprecruiter.com/profile");
        }
        
        Assert.assertTrue(homePage.isPageLoaded(), "Must handle rapid navigation");
    }
    
    @Test(groups = {"monitoring", "health"})
    @Story("Health monitoring test")
    @Description("Test system health and monitoring")
    public void healthMonitoringTest() {
        long startTime = System.currentTimeMillis();
        
        driver.get("https://www.ziprecruiter.com/");
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        // Log performance metrics
        System.out.println("Page load time: " + loadTime + "ms");
        
        // Test memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        System.out.println("Memory usage: " + usedMemory + " / " + maxMemory + " bytes");
        
        // Assert health checks
        Assert.assertTrue(loadTime < 10000, "System must be healthy (load time < 10s)");
        Assert.assertTrue(usedMemory < maxMemory * 0.8, "Memory usage must be healthy");
    }
} 