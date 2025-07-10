package com.ziprecruiter.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.ziprecruiter.utils.ScreenshotUtils;
import com.ziprecruiter.utils.PopupUtils;
import java.io.File;

/**
 * BaseTest - Common test setup and teardown functionality
 * 
 * This class provides shared setup and teardown methods for all test classes,
 * including WebDriver initialization, browser configuration, and cleanup.
 */
public abstract class BaseTest {
    
    protected WebDriver driver;
    protected ScreenshotUtils screenshotUtils;
    protected PopupUtils popupUtils;
    
    // Browser types
    protected static final String CHROME = "chrome";
    protected static final String FIREFOX = "firefox";
    protected static final String EDGE = "edge";
    
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // Attempt to close any lingering WebDriver instances
                // This is a best-effort cleanup for parallel/abrupt shutdowns
                // If you use a driver manager or pool, close all here
                // For static/shared drivers, close them here as well
                System.out.println("JVM shutdown: Attempting to close WebDriver");
                // If you use a static driver, close it here
                // Example: if (staticDriver != null) staticDriver.quit();
            } catch (Exception e) {
                System.out.println("Error in JVM shutdown hook: " + e.getMessage());
            }
        }));
    }
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = CHROME; // Default to Chrome
        }
        
        driver = initializeDriver(browser);
        screenshotUtils = new ScreenshotUtils(driver);
        popupUtils = new PopupUtils(driver);
        
        // Maximize window
        driver.manage().window().maximize();
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        
        System.out.println("WebDriver initialized for browser: " + browser);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("WebDriver closed successfully");
            } catch (Exception e) {
                System.out.println("Error closing WebDriver: " + e.getMessage());
            }
        }
    }
    
    /**
     * Initialize WebDriver based on browser type
     */
    private WebDriver initializeDriver(String browser) {
        WebDriver webDriver = null;
        
        switch (browser.toLowerCase()) {
            case CHROME:
                webDriver = initializeChromeDriver();
                break;
            case FIREFOX:
                webDriver = initializeFirefoxDriver();
                break;
            case EDGE:
                webDriver = initializeEdgeDriver();
                break;
            default:
                System.out.println("Unknown browser: " + browser + ". Defaulting to Chrome.");
                webDriver = initializeChromeDriver();
        }
        
        return webDriver;
    }
    
    /**
     * Initialize Chrome WebDriver
     */
    private WebDriver initializeChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        
        // Add Chrome options for better performance and stability
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-images");
        options.addArguments("--disable-javascript");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        
        // Add user agent to avoid detection
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        
        // Set headless mode for CI/CD environments
        String headless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
        }
        
        return new ChromeDriver(options);
    }
    
    /**
     * Initialize Firefox WebDriver
     */
    private WebDriver initializeFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        
        // Add Firefox options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-images");
        options.addArguments("--disable-javascript");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--start-maximized");
        
        // Set headless mode for CI/CD environments
        String headless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Initialize Edge WebDriver
     */
    private WebDriver initializeEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        
        // Add Edge options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-images");
        options.addArguments("--disable-javascript");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--start-maximized");
        
        // Set headless mode for CI/CD environments
        String headless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    /**
     * Navigate to base URL
     */
    protected void navigateToBaseUrl() {
        String baseUrl = System.getProperty("baseUrl", "https://www.ziprecruiter.com");
        driver.get(baseUrl);
        System.out.println("Navigated to: " + baseUrl);
    }
    
    /**
     * Get WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Get ScreenshotUtils instance
     */
    protected ScreenshotUtils getScreenshotUtils() {
        return screenshotUtils;
    }
    
    /**
     * Get PopupUtils instance
     */
    protected PopupUtils getPopupUtils() {
        return popupUtils;
    }
    
    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        try {
            Thread.sleep(2000); // Simple wait for page load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Create screenshots directory if it doesn't exist
     */
    protected void createScreenshotsDirectory() {
        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }
    }
} 