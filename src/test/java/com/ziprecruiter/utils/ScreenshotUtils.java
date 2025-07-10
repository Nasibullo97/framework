package com.ziprecruiter.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtils - Utility class for taking screenshots
 * 
 * This class provides methods for capturing screenshots of the entire page,
 * specific elements, and managing screenshot files.
 */
public class ScreenshotUtils {
    
    private WebDriver driver;
    private String screenshotDirectory;
    
    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
        this.screenshotDirectory = "screenshots";
        createScreenshotDirectory();
    }
    
    public ScreenshotUtils(WebDriver driver, String directory) {
        this.driver = driver;
        this.screenshotDirectory = directory;
        createScreenshotDirectory();
    }
    
    /**
     * Create screenshot directory if it doesn't exist
     */
    private void createScreenshotDirectory() {
        File dir = new File(screenshotDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Take screenshot of entire page
     */
    public String takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = name + "_" + timestamp + ".png";
            String filePath = screenshotDirectory + File.separator + fileName;
            
            Path destination = Paths.get(filePath);
            Files.copy(source.toPath(), destination);
            
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Take screenshot with default name
     */
    public String takeScreenshot() {
        return takeScreenshot("screenshot");
    }
    
    public static String takeScreenshot(WebDriver driver, String fileName) {
        ScreenshotUtils utils = new ScreenshotUtils(driver);
        return utils.takeScreenshot(fileName);
    }
    
    /**
     * Take screenshot of specific element
     */
    public String takeElementScreenshot(WebElement element, String name) {
        try {
            File source = element.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = name + "_" + timestamp + ".png";
            String filePath = screenshotDirectory + File.separator + fileName;
            
            Path destination = Paths.get(filePath);
            Files.copy(source.toPath(), destination);
            
            System.out.println("Element screenshot saved: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to take element screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Take screenshot of element with default name
     */
    public String takeElementScreenshot(WebElement element) {
        return takeElementScreenshot(element, "element_screenshot");
    }
    
    /**
     * Take screenshot on test failure
     */
    public String takeFailureScreenshot(String testName) {
        return takeScreenshot("FAILURE_" + testName);
    }
    
    /**
     * Take screenshot on test success
     */
    public String takeSuccessScreenshot(String testName) {
        return takeScreenshot("SUCCESS_" + testName);
    }
    
    /**
     * Get screenshot directory
     */
    public String getScreenshotDirectory() {
        return screenshotDirectory;
    }
    
    /**
     * Set screenshot directory
     */
    public void setScreenshotDirectory(String directory) {
        this.screenshotDirectory = directory;
        createScreenshotDirectory();
    }
    
    /**
     * Get screenshot as byte array
     */
    public byte[] getScreenshotAsBytes() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            return ts.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to get screenshot as bytes: " + e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Get element screenshot as byte array
     */
    public byte[] getElementScreenshotAsBytes(WebElement element) {
        try {
            return element.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to get element screenshot as bytes: " + e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Clean old screenshots (older than specified days)
     */
    public void cleanOldScreenshots(int daysOld) {
        try {
            File dir = new File(screenshotDirectory);
            File[] files = dir.listFiles();
            
            if (files != null) {
                long cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L);
                
                for (File file : files) {
                    if (file.lastModified() < cutoffTime) {
                        file.delete();
                        System.out.println("Deleted old screenshot: " + file.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to clean old screenshots: " + e.getMessage());
        }
    }
    
    /**
     * Get screenshot count in directory
     */
    public int getScreenshotCount() {
        try {
            File dir = new File(screenshotDirectory);
            File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".png"));
            return files != null ? files.length : 0;
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Get total size of screenshot directory in MB
     */
    public double getScreenshotDirectorySizeMB() {
        try {
            File dir = new File(screenshotDirectory);
            File[] files = dir.listFiles();
            
            if (files != null) {
                long totalSize = 0;
                for (File file : files) {
                    totalSize += file.length();
                }
                return totalSize / (1024.0 * 1024.0); // Convert to MB
            }
            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
} 