package com.ziprecruiter.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

/**
 * PopupUtils - Utility class for handling popups and modals
 * 
 * This class provides methods for detecting and handling various types
 * of popups, modals, and overlays that commonly appear on websites.
 */
public class PopupUtils {
    
    private WebDriver driver;
    private JavascriptExecutor js;
    
    // Common popup selectors
    private static final String[] POPUP_SELECTORS = {
        "div[class*='popup']",
        "div[class*='modal']",
        "div[class*='overlay']",
        "div[class*='dialog']",
        "div[class*='lightbox']",
        "div[id*='popup']",
        "div[id*='modal']",
        "div[id*='overlay']",
        "div[id*='dialog']",
        "div[id*='lightbox']",
        ".popup",
        ".modal",
        ".overlay",
        ".dialog",
        ".lightbox"
    };
    
    // Common close button selectors
    private static final String[] CLOSE_BUTTON_SELECTORS = {
        "button[class*='close']",
        "button[class*='dismiss']",
        "button[class*='cancel']",
        "span[class*='close']",
        "span[class*='dismiss']",
        "a[class*='close']",
        "a[class*='dismiss']",
        ".close",
        ".dismiss",
        ".cancel",
        "[aria-label='Close']",
        "[title='Close']",
        "×",
        "✕",
        "✖"
    };
    
    public PopupUtils(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }
    
    /**
     * Check if any popup is visible
     */
    public boolean isPopupVisible() {
        for (String selector : POPUP_SELECTORS) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        return false;
    }
    
    /**
     * Close popup if visible
     */
    public boolean closePopup() {
        if (!isPopupVisible()) {
            return true; // No popup to close
        }
        
        // Try to find and click close button
        for (String selector : CLOSE_BUTTON_SELECTORS) {
            try {
                List<WebElement> closeButtons = driver.findElements(By.cssSelector(selector));
                for (WebElement button : closeButtons) {
                    if (button.isDisplayed()) {
                        try {
                            button.click();
                            WaitUtils.waitForSeconds(1);
                            if (!isPopupVisible()) {
                                System.out.println("Popup closed successfully using selector: " + selector);
                                return true;
                            }
                        } catch (Exception e) {
                            // Try JavaScript click
                            try {
                                js.executeScript("arguments[0].click();", button);
                                WaitUtils.waitForSeconds(1);
                                if (!isPopupVisible()) {
                                    System.out.println("Popup closed successfully using JavaScript click: " + selector);
                                    return true;
                                }
                            } catch (Exception jsEx) {
                                // Continue to next button
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        
        // Try pressing Escape key
        try {
            driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            WaitUtils.waitForSeconds(1);
            if (!isPopupVisible()) {
                System.out.println("Popup closed successfully using Escape key");
                return true;
            }
        } catch (Exception e) {
            // Continue to next method
        }
        
        // Try clicking outside popup
        try {
            js.executeScript("document.body.click();");
            WaitUtils.waitForSeconds(1);
            if (!isPopupVisible()) {
                System.out.println("Popup closed successfully by clicking outside");
                return true;
            }
        } catch (Exception e) {
            // Continue to next method
        }
        
        System.out.println("Failed to close popup");
        return false;
    }
    
    /**
     * Close popup with specific selector
     */
    public boolean closePopup(String popupSelector, String closeButtonSelector) {
        try {
            WebElement popup = driver.findElement(By.cssSelector(popupSelector));
            if (popup.isDisplayed()) {
                WebElement closeButton = popup.findElement(By.cssSelector(closeButtonSelector));
                closeButton.click();
                WaitUtils.waitForSeconds(1);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Failed to close popup with specific selectors: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Close popup by clicking on specific element
     */
    public boolean closePopupByClicking(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            if (element.isDisplayed()) {
                element.click();
                WaitUtils.waitForSeconds(1);
                return !isPopupVisible();
            }
        } catch (Exception e) {
            System.err.println("Failed to close popup by clicking element: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Close popup using JavaScript
     */
    public boolean closePopupWithJavaScript(String popupSelector) {
        try {
            String script = "var popup = document.querySelector('" + popupSelector + "'); " +
                           "if (popup) { popup.style.display = 'none'; popup.remove(); }";
            js.executeScript(script);
            WaitUtils.waitForSeconds(1);
            return !isPopupVisible();
        } catch (Exception e) {
            System.err.println("Failed to close popup with JavaScript: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Accept alert if present
     */
    public boolean acceptAlert() {
        try {
            driver.switchTo().alert().accept();
            System.out.println("Alert accepted successfully");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Dismiss alert if present
     */
    public boolean dismissAlert() {
        try {
            driver.switchTo().alert().dismiss();
            System.out.println("Alert dismissed successfully");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get alert text if present
     */
    public String getAlertText() {
        try {
            return driver.switchTo().alert().getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Send text to alert if present
     */
    public boolean sendTextToAlert(String text) {
        try {
            driver.switchTo().alert().sendKeys(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Switch to frame by index
     */
    public boolean switchToFrame(int index) {
        try {
            driver.switchTo().frame(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Switch to frame by name or ID
     */
    public boolean switchToFrame(String nameOrId) {
        try {
            driver.switchTo().frame(nameOrId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Switch to default content
     */
    public boolean switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for popup to appear
     */
    public boolean waitForPopup(int timeoutSeconds) {
        try {
            for (int i = 0; i < timeoutSeconds; i++) {
                if (isPopupVisible()) {
                    return true;
                }
                WaitUtils.waitForSeconds(1);
            }
        } catch (Exception e) {
            // Ignore exceptions
        }
        return false;
    }
    
    /**
     * Wait for popup to disappear
     */
    public boolean waitForPopupToDisappear(int timeoutSeconds) {
        try {
            for (int i = 0; i < timeoutSeconds; i++) {
                if (!isPopupVisible()) {
                    return true;
                }
                WaitUtils.waitForSeconds(1);
            }
        } catch (Exception e) {
            // Ignore exceptions
        }
        return false;
    }
    
    /**
     * Get popup count
     */
    public int getPopupCount() {
        int count = 0;
        for (String selector : POPUP_SELECTORS) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        count++;
                    }
                }
            } catch (Exception e) {
                // Continue to next selector
            }
        }
        return count;
    }
    
    /**
     * Check if specific popup is visible
     */
    public boolean isSpecificPopupVisible(String popupSelector) {
        try {
            WebElement popup = driver.findElement(By.cssSelector(popupSelector));
            return popup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get popup text content
     */
    public String getPopupText(String popupSelector) {
        try {
            WebElement popup = driver.findElement(By.cssSelector(popupSelector));
            return popup.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public static void closePopups() {
        // Implementation for closing popups
        // This would typically close any modal dialogs, cookie banners, etc.
    }
    
    public static void closePopups(WebDriver driver) {
        closePopups();
    }
} 