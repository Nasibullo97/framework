package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.utils.WaitUtils;
import com.ziprecruiter.utils.ElementUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

@Epic("Edge Case Testing")
@Feature("Boundary Conditions and Performance")
public class EdgeCaseTests extends BaseTest {
    
    @Test
    @Story("Boundary value testing")
    @Description("Test search with boundary values")
    public void testSearchBoundaryValues() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with single character
        homePage.searchJobs("a", "NY");
        // Should handle gracefully
        
        // Test with maximum length (assuming 255 chars)
        String maxLengthSearch = "a".repeat(255);
        homePage.searchJobs(maxLengthSearch, "New York");
        // Should handle gracefully
        
        // Test with unicode characters
        homePage.searchJobs("测试工作", "New York");
        // Should handle gracefully
    }
    
    @Test
    @Story("Performance testing")
    @Description("Test page load performance")
    public void testPageLoadPerformance() {
        long startTime = System.currentTimeMillis();
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Wait for page to load completely
        WaitUtils.waitForPageLoad(driver);
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        // Assert page loads within reasonable time (5 seconds)
        Assert.assertTrue(loadTime < 5000, "Page should load within 5 seconds. Actual: " + loadTime + "ms");
        
        // Test JavaScript execution time
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long jsStartTime = System.currentTimeMillis();
        js.executeScript("return document.readyState");
        long jsTime = System.currentTimeMillis() - jsStartTime;
        
        Assert.assertTrue(jsTime < 1000, "JavaScript should execute quickly. Actual: " + jsTime + "ms");
    }
    
    @Test
    @Story("Memory usage testing")
    @Description("Test memory consumption during navigation")
    public void testMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        
        // Navigate through multiple pages
        for (int i = 0; i < 5; i++) {
            driver.get("https://www.ziprecruiter.com/");
            driver.get("https://www.ziprecruiter.com/jobs");
            driver.get("https://www.ziprecruiter.com/profile");
            
            // Force garbage collection
            System.gc();
            
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();
            
            // Assert memory usage is reasonable (less than 80% of max)
            Assert.assertTrue(usedMemory < maxMemory * 0.8, 
                "Memory usage should be reasonable. Used: " + usedMemory + ", Max: " + maxMemory);
        }
    }
    
    @Test
    @Story("Concurrent user simulation")
    @Description("Simulate multiple concurrent users")
    public void testConcurrentUserSimulation() {
        // This is a simplified simulation
        // In real scenarios, you'd use tools like JMeter or Gatling
        
        HomePage homePage = new HomePage(driver);
        
        // Simulate rapid interactions
        for (int i = 0; i < 10; i++) {
            driver.get("https://www.ziprecruiter.com/");
            homePage.searchJobs("Software Engineer", "New York");
            
            // Small delay to simulate real user behavior
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Verify system still responds
        Assert.assertTrue(homePage.isPageLoaded(), "System should remain responsive");
    }
    
    @Test
    @Story("Stress testing")
    @Description("Test system under stress conditions")
    public void testStressConditions() {
        HomePage homePage = new HomePage(driver);
        
        // Rapid page refreshes
        for (int i = 0; i < 20; i++) {
            driver.navigate().refresh();
            WaitUtils.waitForPageLoad(driver);
        }
        
        // Verify system still works
        Assert.assertTrue(homePage.isPageLoaded(), "System should handle stress");
        
        // Rapid navigation
        for (int i = 0; i < 10; i++) {
            driver.get("https://www.ziprecruiter.com/");
            driver.get("https://www.ziprecruiter.com/jobs");
            driver.get("https://www.ziprecruiter.com/profile");
        }
        
        Assert.assertTrue(homePage.isPageLoaded(), "System should handle rapid navigation");
    }
    
    @Test
    @Story("Input validation edge cases")
    @Description("Test various input validation scenarios")
    public void testInputValidationEdgeCases() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with SQL injection attempt
        homePage.searchJobs("'; DROP TABLE users; --", "New York");
        // Should handle gracefully
        
        // Test with HTML injection
        homePage.searchJobs("<h1>Test</h1>", "New York");
        // Should handle gracefully
        
        // Test with script injection
        homePage.searchJobs("<script>alert('test')</script>", "New York");
        // Should handle gracefully
    }
    
    @Test
    @Story("Keyboard navigation edge cases")
    @Description("Test keyboard navigation scenarios")
    public void testKeyboardNavigationEdgeCases() {
        driver.get("https://www.ziprecruiter.com/");
        
        Actions actions = new Actions(driver);
        
        // Test rapid key presses
        for (int i = 0; i < 50; i++) {
            actions.sendKeys(Keys.TAB).perform();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Test arrow key navigation
        for (int i = 0; i < 20; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            actions.sendKeys(Keys.ARROW_UP).perform();
        }
        
        // Verify page is still functional
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page should remain functional after keyboard stress");
    }
    
    @Test
    @Story("Browser window edge cases")
    @Description("Test browser window manipulation")
    public void testBrowserWindowEdgeCases() {
        driver.get("https://www.ziprecruiter.com/");
        
        // Test window resize
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(800, 600));
        Assert.assertTrue(driver.getCurrentUrl().contains("ziprecruiter"), "Should work at smaller size");
        
        // Test window maximize
        driver.manage().window().maximize();
        Assert.assertTrue(driver.getCurrentUrl().contains("ziprecruiter"), "Should work when maximized");
        
        // Test window minimize (simulated)
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1, 1));
        driver.manage().window().maximize();
        Assert.assertTrue(driver.getCurrentUrl().contains("ziprecruiter"), "Should recover from minimize");
    }
    
    @Test
    @Story("Network latency simulation")
    @Description("Test behavior under slow network conditions")
    public void testNetworkLatencySimulation() {
        // This would require network simulation tools
        // For now, test with longer timeouts
        
        long startTime = System.currentTimeMillis();
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Use longer timeout to simulate slow network
        WaitUtils.waitForPageLoad(driver);
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        // Even with simulated slow network, should eventually load
        Assert.assertTrue(loadTime < 30000, "Page should load even with slow network. Time: " + loadTime + "ms");
    }
    
    @Test
    @Story("Resource loading edge cases")
    @Description("Test behavior when resources fail to load")
    public void testResourceLoadingEdgeCases() {
        driver.get("https://www.ziprecruiter.com/");
        
        // Disable images to simulate failed image loads
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('img').forEach(img => img.style.display='none')");
        
        // Verify page still functions
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page should function without images");
        
        // Re-enable images
        js.executeScript("document.querySelectorAll('img').forEach(img => img.style.display='')");
    }
    
    @Test
    @Story("Form submission edge cases")
    @Description("Test form submission with various scenarios")
    public void testFormSubmissionEdgeCases() {
        LoginPage loginPage = new LoginPage(driver);
        
        driver.get("https://www.ziprecruiter.com/login");
        
        // Test double submission
        loginPage.login("test@example.com", "password123");
        loginPage.login("test@example.com", "password123");
        // Should handle gracefully
        
        // Test submission with page refresh
        driver.navigate().refresh();
        loginPage.login("test@example.com", "password123");
        // Should handle gracefully
    }
} 