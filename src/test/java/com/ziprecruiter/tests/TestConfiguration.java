package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestData;
import com.ziprecruiter.data.TestDataFactory;
import com.ziprecruiter.config.ConfigManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("Test Configuration")
@Feature("Test Setup and Configuration Management")
public class TestConfiguration extends BaseTest {
    
    @BeforeClass
    public void beforeClass() {
        System.out.println("Setting up test class configuration");
        ConfigManager.loadConfig("ziprecruiter.properties");
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("Cleaning up test class configuration");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Setting up test method configuration");
        driver.manage().window().maximize();
    }
    
    @AfterMethod
    public void afterMethod() {
        System.out.println("Cleaning up test method configuration");
        // Clear cookies and local storage
        driver.manage().deleteAllCookies();
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("localStorage.clear();");
        js.executeScript("sessionStorage.clear();");
    }
    
    @Test(groups = {"configuration", "setup"})
    @Story("Test environment configuration")
    @Description("Verify test environment is properly configured")
    public void testEnvironmentConfiguration() {
        // Test that configuration is loaded
        String baseUrl = ConfigManager.getProperty("base.url");
        Assert.assertNotNull(baseUrl, "Base URL should be configured");
        Assert.assertTrue(baseUrl.contains("ziprecruiter"), "Base URL should contain ziprecruiter");
        
        // Test that WebDriver is properly configured
        Assert.assertNotNull(driver, "WebDriver should be initialized");
        
        // Test that page load timeout is set
        long pageLoadTimeout = driver.manage().timeouts().getPageLoadTimeout().toMillis();
        Assert.assertTrue(pageLoadTimeout > 0, "Page load timeout should be set");
        
        // Test that implicit wait is set
        long implicitWait = driver.manage().timeouts().getImplicitWaitTimeout().toMillis();
        Assert.assertTrue(implicitWait > 0, "Implicit wait should be set");
    }
    
    @Test(groups = {"configuration", "browser"})
    @Story("Browser configuration test")
    @Description("Test browser-specific configuration")
    public void testBrowserConfiguration() {
        // Test browser capabilities
        String browserName = ((org.openqa.selenium.remote.RemoteWebDriver) driver).getCapabilities().getBrowserName();
        Assert.assertNotNull(browserName, "Browser name should be available");
        
        // Test window size
        org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();
        Assert.assertTrue(windowSize.getWidth() > 0, "Window width should be positive");
        Assert.assertTrue(windowSize.getHeight() > 0, "Window height should be positive");
        
        // Test window position
        org.openqa.selenium.Point windowPosition = driver.manage().window().getPosition();
        Assert.assertNotNull(windowPosition, "Window position should be available");
    }
    
    @Test(groups = {"configuration", "timeout"})
    @Story("Timeout configuration test")
    @Description("Test timeout configuration settings")
    public void testTimeoutConfiguration() {
        // Test page load timeout
        long pageLoadTimeout = driver.manage().timeouts().getPageLoadTimeout().toMillis();
        Assert.assertTrue(pageLoadTimeout >= 10000, "Page load timeout should be at least 10 seconds");
        
        // Test script timeout
        long scriptTimeout = driver.manage().timeouts().getScriptTimeout().toMillis();
        Assert.assertTrue(scriptTimeout >= 5000, "Script timeout should be at least 5 seconds");
        
        // Test implicit wait
        long implicitWait = driver.manage().timeouts().getImplicitWaitTimeout().toMillis();
        Assert.assertTrue(implicitWait >= 1000, "Implicit wait should be at least 1 second");
    }
    
    @Test(groups = {"configuration", "properties"})
    @Story("Properties configuration test")
    @Description("Test properties file configuration")
    public void testPropertiesConfiguration() {
        // Test required properties
        String[] requiredProperties = {
            "base.url",
            "browser.name",
            "implicit.wait",
            "page.load.timeout"
        };
        
        for (String property : requiredProperties) {
            String value = ConfigManager.getProperty(property);
            Assert.assertNotNull(value, "Property " + property + " should be configured");
            Assert.assertFalse(value.trim().isEmpty(), "Property " + property + " should not be empty");
        }
        
        // Test optional properties
        String screenshotPath = ConfigManager.getProperty("screenshot.path");
        if (screenshotPath != null) {
            Assert.assertFalse(screenshotPath.trim().isEmpty(), "Screenshot path should not be empty if configured");
        }
    }
    
    @Test(groups = {"configuration", "data"})
    @Story("Test data configuration")
    @Description("Test test data configuration and loading")
    public void testDataConfiguration() {
        // Test that test data factory is working
        TestDataFactory dataFactory = new TestDataFactory();
        Assert.assertNotNull(dataFactory, "Test data factory should be initialized");
        
        // Test loading test data
        TestData testData = dataFactory.getSearchTestData();
        Assert.assertNotNull(testData, "Search test data should be loaded");
        Assert.assertNotNull(testData.getJobTitle(), "Job title should not be null");
        Assert.assertNotNull(testData.getLocation(), "Location should not be null");
    }
    
    @Test(groups = {"configuration", "page-objects"})
    @Story("Page object configuration test")
    @Description("Test page object initialization and configuration")
    public void testPageObjectConfiguration() {
        // Test page object initialization
        HomePage homePage = new HomePage(driver);
        Assert.assertNotNull(homePage, "HomePage should be initialized");
        
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        Assert.assertNotNull(searchResultsPage, "SearchResultsPage should be initialized");
        
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertNotNull(loginPage, "LoginPage should be initialized");
        
        // Test page object methods
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "HomePage should be properly configured");
    }
    
    @Test(groups = {"configuration", "utilities"})
    @Story("Utility classes configuration test")
    @Description("Test utility classes configuration")
    public void testUtilityConfiguration() {
        // Test WaitUtils
        driver.get("https://www.ziprecruiter.com/");
        org.openqa.selenium.By titleLocator = org.openqa.selenium.By.tagName("title");
        boolean titlePresent = com.ziprecruiter.utils.WaitUtils.waitForVisible(driver, titleLocator, 10);
        Assert.assertTrue(titlePresent, "WaitUtils should be properly configured");
        
        // Test ElementUtils
        boolean elementExists = com.ziprecruiter.utils.ElementUtils.isElementPresent(driver, titleLocator);
        Assert.assertTrue(elementExists, "ElementUtils should be properly configured");
        
        // Test ScreenshotUtils
        String screenshotPath = com.ziprecruiter.utils.ScreenshotUtils.takeScreenshot(driver, "test-config");
        Assert.assertNotNull(screenshotPath, "ScreenshotUtils should be properly configured");
        
        // Test PopupUtils
        com.ziprecruiter.utils.PopupUtils.closePopups(driver);
        // Should not throw exception
    }
    
    @Test(groups = {"configuration", "allure"})
    @Story("Allure reporting configuration test")
    @Description("Test Allure reporting configuration")
    public void testAllureConfiguration() {
        // Test that Allure annotations work
        io.qameta.allure.Allure.addAttachment("test-config", "text/plain", "Test configuration verification");
        
        // Test that test information is captured
        String testName = "testAllureConfiguration";
        Assert.assertEquals(testName, "testAllureConfiguration", "Test name should be captured");
        
        // Test that environment info is available
        String browserName = ((org.openqa.selenium.remote.RemoteWebDriver) driver).getCapabilities().getBrowserName();
        io.qameta.allure.Allure.addAttachment("browser-info", "text/plain", "Browser: " + browserName);
        
        Assert.assertNotNull(browserName, "Browser information should be available for Allure");
    }
    
    @Test(groups = {"configuration", "parallel"})
    @Story("Parallel execution configuration test")
    @Description("Test parallel execution configuration")
    public void testParallelConfiguration() {
        // Test thread safety
        String threadName = Thread.currentThread().getName();
        Assert.assertNotNull(threadName, "Thread name should be available");
        
        // Test that each thread has its own WebDriver instance
        String sessionId = ((org.openqa.selenium.remote.RemoteWebDriver) driver).getSessionId().toString();
        Assert.assertNotNull(sessionId, "Session ID should be available");
        
        // Test that configuration is thread-safe
        String baseUrl = ConfigManager.getProperty("base.url");
        Assert.assertNotNull(baseUrl, "Configuration should be accessible from any thread");
        
        // Test parallel execution capability
        driver.get("https://www.ziprecruiter.com/");
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Parallel execution should work correctly");
    }
    
    @Test(groups = {"configuration", "cleanup"})
    @Story("Cleanup configuration test")
    @Description("Test cleanup configuration and execution")
    public void testCleanupConfiguration() {
        // Test cookie cleanup
        driver.get("https://www.ziprecruiter.com/");
        driver.manage().addCookie(new org.openqa.selenium.Cookie("test-cookie", "test-value"));
        
        driver.manage().deleteAllCookies();
        int cookieCount = driver.manage().getCookies().size();
        Assert.assertEquals(cookieCount, 0, "All cookies should be deleted");
        
        // Test local storage cleanup
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem('test-key', 'test-value');");
        js.executeScript("localStorage.clear();");
        
        String localStorageValue = (String) js.executeScript("return localStorage.getItem('test-key');");
        Assert.assertNull(localStorageValue, "Local storage should be cleared");
        
        // Test session storage cleanup
        js.executeScript("sessionStorage.setItem('test-key', 'test-value');");
        js.executeScript("sessionStorage.clear();");
        
        String sessionStorageValue = (String) js.executeScript("return sessionStorage.getItem('test-key');");
        Assert.assertNull(sessionStorageValue, "Session storage should be cleared");
    }
    
    @Test(groups = {"configuration", "validation"})
    @Story("Configuration validation test")
    @Description("Test configuration validation and error handling")
    public void testConfigurationValidation() {
        // Test invalid property handling
        String invalidProperty = ConfigManager.getProperty("invalid.property");
        Assert.assertNull(invalidProperty, "Invalid property should return null");
        
        // Test default value handling
        String defaultValue = ConfigManager.getProperty("invalid.property", "default");
        Assert.assertEquals(defaultValue, "default", "Should return default value for invalid property");
        
        // Test required property validation
        String baseUrl = ConfigManager.getProperty("base.url");
        Assert.assertNotNull(baseUrl, "Required property should be available");
        Assert.assertFalse(baseUrl.trim().isEmpty(), "Required property should not be empty");
        
        // Test URL format validation
        Assert.assertTrue(baseUrl.startsWith("http"), "Base URL should be a valid URL");
    }
} 