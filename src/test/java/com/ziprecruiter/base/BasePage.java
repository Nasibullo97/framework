package com.ziprecruiter.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

/**
 * BasePage - Common functionality for all page objects
 * 
 * This class provides shared methods and utilities that all page objects
 * can use, including element interactions, waits, and common operations.
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;
    
    // Default timeout values
    protected static final int DEFAULT_TIMEOUT = 10;
    protected static final int SHORT_TIMEOUT = 5;
    protected static final int LONG_TIMEOUT = 20;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }
    
    /**
     * Wait for element to be visible
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be present in DOM
     */
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is present in DOM
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click on element
     */
    protected void clickElement(By locator) {
        WebElement element = waitForClickable(locator);
        try {
            element.click();
        } catch (Exception e) {
            // Fallback to JavaScript click
            js.executeScript("arguments[0].click();", element);
        }
    }
    
    /**
     * Type text into element
     */
    protected void typeText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Clear and type text into element
     */
    protected void clearAndType(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    protected String getText(By locator) {
        WebElement element = waitForElement(locator);
        return element.getText();
    }
    
    /**
     * Get attribute value from element
     */
    protected String getAttribute(By locator, String attribute) {
        WebElement element = waitForElement(locator);
        return element.getAttribute(attribute);
    }
    
    /**
     * Scroll to element
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Wait for specific timeout
     */
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
        waitForPageLoad();
    }
    
    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
    }
    
    /**
     * Go back to previous page
     */
    protected void goBack() {
        driver.navigate().back();
        waitForPageLoad();
    }
    
    /**
     * Go forward to next page
     */
    protected void goForward() {
        driver.navigate().forward();
        waitForPageLoad();
    }
    
    /**
     * Switch to frame by index
     */
    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }
    
    /**
     * Switch to frame by name or ID
     */
    protected void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }
    
    /**
     * Switch to frame by element
     */
    protected void switchToFrame(By locator) {
        WebElement frameElement = driver.findElement(locator);
        driver.switchTo().frame(frameElement);
    }
    
    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    /**
     * Accept alert
     */
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
    
    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }
    
    /**
     * Get alert text
     */
    protected String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }
    
    /**
     * Send keys to alert
     */
    protected void sendKeysToAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }
    
    /**
     * Wait for element to disappear
     */
    protected boolean waitForElementToDisappear(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for text to be present in element
     */
    protected boolean waitForTextToBePresent(By locator, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for URL to contain specific text
     */
    protected boolean waitForUrlToContain(String text) {
        try {
            wait.until(ExpectedConditions.urlContains(text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Wait for title to contain specific text
     */
    protected boolean waitForTitleToContain(String text) {
        try {
            wait.until(ExpectedConditions.titleContains(text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Execute JavaScript
     */
    protected Object executeJavaScript(String script, Object... args) {
        return js.executeScript(script, args);
    }
    
    /**
     * Take screenshot (placeholder for screenshot utility)
     */
    protected void takeScreenshot(String name) {
        // This will be implemented by ScreenshotUtils
        System.out.println("Screenshot taken: " + name);
    }
    
    /**
     * Abstract method that each page must implement
     */
    public abstract boolean isPageLoaded();
} 