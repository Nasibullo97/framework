package com.ziprecruiter.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

/**
 * WaitUtils - Utility class for various wait operations
 * 
 * This class provides static methods for different types of waits
 * including explicit waits, fluent waits, and custom wait conditions.
 */
public class WaitUtils {
    
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int SHORT_TIMEOUT = 5;
    private static final int LONG_TIMEOUT = 20;
    
    /**
     * Wait for element to be visible
     */
    public static boolean waitForVisible(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be visible with default timeout
     */
    public static boolean waitForVisible(WebDriver driver, By locator) {
        return waitForVisible(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to be clickable
     */
    public static boolean waitForClickable(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be clickable with default timeout
     */
    public static boolean waitForClickable(WebDriver driver, By locator) {
        return waitForClickable(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to be present in DOM
     */
    public static boolean waitForPresence(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be present with default timeout
     */
    public static boolean waitForPresence(WebDriver driver, By locator) {
        return waitForPresence(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to disappear
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to disappear with default timeout
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return waitForInvisibility(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for text to be present in element
     */
    public static boolean waitForTextToBePresent(WebDriver driver, By locator, String text, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for text to be present with default timeout
     */
    public static boolean waitForTextToBePresent(WebDriver driver, By locator, String text) {
        return waitForTextToBePresent(driver, locator, text, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for URL to contain specific text
     */
    public static boolean waitForUrlToContain(WebDriver driver, String text, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.urlContains(text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for URL to contain with default timeout
     */
    public static boolean waitForUrlToContain(WebDriver driver, String text) {
        return waitForUrlToContain(driver, text, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for title to contain specific text
     */
    public static boolean waitForTitleToContain(WebDriver driver, String text, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.titleContains(text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for title to contain with default timeout
     */
    public static boolean waitForTitleToContain(WebDriver driver, String text) {
        return waitForTitleToContain(driver, text, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for page to load completely
     */
    public static boolean waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(webDriver -> {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                return js.executeScript("return document.readyState").equals("complete");
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for page to load with default timeout
     */
    public static boolean waitForPageLoad(WebDriver driver) {
        return waitForPageLoad(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for jQuery to finish loading
     */
    public static boolean waitForJQueryToLoad(WebDriver driver, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(webDriver -> {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                return (Boolean) js.executeScript("return jQuery.active == 0");
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for jQuery to load with default timeout
     */
    public static boolean waitForJQueryToLoad(WebDriver driver) {
        return waitForJQueryToLoad(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for Angular to finish loading
     */
    public static boolean waitForAngularToLoad(WebDriver driver, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(webDriver -> {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                return (Boolean) js.executeScript("return angular.element(document).injector().get('$http').pendingRequests.length === 0");
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for Angular to load with default timeout
     */
    public static boolean waitForAngularToLoad(WebDriver driver) {
        return waitForAngularToLoad(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for specific number of seconds
     */
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Wait for element to be stale (removed from DOM)
     */
    public static boolean waitForElementToBeStale(WebDriver driver, WebElement element, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.stalenessOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be stale with default timeout
     */
    public static boolean waitForElementToBeStale(WebDriver driver, WebElement element) {
        return waitForElementToBeStale(driver, element, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for frame to be available and switch to it
     */
    public static boolean waitForFrameAndSwitch(WebDriver driver, By frameLocator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for frame and switch with default timeout
     */
    public static boolean waitForFrameAndSwitch(WebDriver driver, By frameLocator) {
        return waitForFrameAndSwitch(driver, frameLocator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for alert to be present
     */
    public static boolean waitForAlert(WebDriver driver, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for alert with default timeout
     */
    public static boolean waitForAlert(WebDriver driver) {
        return waitForAlert(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to have specific attribute value
     */
    public static boolean waitForAttributeToBe(WebDriver driver, By locator, String attribute, String value, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for attribute to be with default timeout
     */
    public static boolean waitForAttributeToBe(WebDriver driver, By locator, String attribute, String value) {
        return waitForAttributeToBe(driver, locator, attribute, value, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to have specific CSS class
     */
    public static boolean waitForElementToHaveClass(WebDriver driver, By locator, String className, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.attributeContains(locator, "class", className));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for element to have class with default timeout
     */
    public static boolean waitForElementToHaveClass(WebDriver driver, By locator, String className) {
        return waitForElementToHaveClass(driver, locator, className, DEFAULT_TIMEOUT);
    }
} 