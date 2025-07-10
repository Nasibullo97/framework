package com.ziprecruiter.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

/**
 * ElementUtils - Utility class for element interactions
 * 
 * This class provides static methods for common element operations
 * including finding, clicking, typing, and other interactions.
 */
public class ElementUtils {
    
    /**
     * Find element by locator
     */
    public static WebElement findElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }
    
    /**
     * Find elements by locator
     */
    public static List<WebElement> findElements(WebDriver driver, By locator) {
        return driver.findElements(locator);
    }
    
    /**
     * Check if element is present in DOM
     */
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Check if element is visible
     */
    public static boolean isElementVisible(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     */
    public static boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    /**
     * Click element
     */
    public static void click(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            element.click();
        } catch (Exception e) {
            // Fallback to JavaScript click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(locator);
            js.executeScript("arguments[0].click();", element);
        }
    }
    
    /**
     * Click element with JavaScript
     */
    public static void clickWithJavaScript(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].click();", element);
    }
    
    /**
     * Type text into element
     */
    public static void typeText(WebDriver driver, By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Clear and type text into element
     */
    public static void clearAndType(WebDriver driver, By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    public static String getText(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getText();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return "";
        }
    }
    
    /**
     * Get attribute value from element
     */
    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getAttribute(attribute);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return "";
        }
    }
    
    /**
     * Get CSS value from element
     */
    public static String getCssValue(WebDriver driver, By locator, String propertyName) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getCssValue(propertyName);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return "";
        }
    }
    
    /**
     * Scroll to element
     */
    public static void scrollToElement(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Scroll to element with offset
     */
    public static void scrollToElement(WebDriver driver, By locator, int offset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -" + offset + ");", element);
    }
    
    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    
    /**
     * Scroll to top of page
     */
    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
    
    /**
     * Hover over element
     */
    public static void hoverOverElement(WebDriver driver, By locator) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element).perform();
    }
    
    /**
     * Double click element
     */
    public static void doubleClick(WebDriver driver, By locator) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(locator);
        actions.doubleClick(element).perform();
    }
    
    /**
     * Right click element
     */
    public static void rightClick(WebDriver driver, By locator) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(locator);
        actions.contextClick(element).perform();
    }
    
    /**
     * Drag and drop element
     */
    public static void dragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
        Actions actions = new Actions(driver);
        WebElement source = driver.findElement(sourceLocator);
        WebElement target = driver.findElement(targetLocator);
        actions.dragAndDrop(source, target).perform();
    }
    
    /**
     * Select option by visible text
     */
    public static void selectByVisibleText(WebDriver driver, By locator, String text) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
        select.selectByVisibleText(text);
    }
    
    /**
     * Select option by value
     */
    public static void selectByValue(WebDriver driver, By locator, String value) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
        select.selectByValue(value);
    }
    
    /**
     * Select option by index
     */
    public static void selectByIndex(WebDriver driver, By locator, int index) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
        select.selectByIndex(index);
    }
    
    /**
     * Get selected option text
     */
    public static String getSelectedOptionText(WebDriver driver, By locator) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
        return select.getFirstSelectedOption().getText();
    }
    
    /**
     * Get selected option value
     */
    public static String getSelectedOptionValue(WebDriver driver, By locator) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
        return select.getFirstSelectedOption().getAttribute("value");
    }
    
    /**
     * Check if checkbox is selected
     */
    public static boolean isCheckboxSelected(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    /**
     * Check checkbox
     */
    public static void checkCheckbox(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        if (!element.isSelected()) {
            element.click();
        }
    }
    
    /**
     * Uncheck checkbox
     */
    public static void uncheckCheckbox(WebDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        if (element.isSelected()) {
            element.click();
        }
    }
    
    /**
     * Get element size (width and height)
     */
    public static org.openqa.selenium.Dimension getElementSize(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getSize();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return new org.openqa.selenium.Dimension(0, 0);
        }
    }
    
    /**
     * Get element location
     */
    public static org.openqa.selenium.Point getElementLocation(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getLocation();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return new org.openqa.selenium.Point(0, 0);
        }
    }
    
    /**
     * Execute JavaScript on element
     */
    public static Object executeJavaScriptOnElement(WebDriver driver, By locator, String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        return js.executeScript(script, element);
    }
    
    /**
     * Highlight element
     */
    public static void highlightElement(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }
    
    /**
     * Remove highlight from element
     */
    public static void removeHighlight(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].style.border=''", element);
    }
    
    /**
     * Get element count
     */
    public static int getElementCount(WebDriver driver, By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Wait for element to be present and return it
     */
    public static WebElement waitForElement(WebDriver driver, By locator, int timeoutSeconds) {
        return WaitUtils.waitForPresence(driver, locator, timeoutSeconds) ? 
               driver.findElement(locator) : null;
    }
    
    /**
     * Wait for element with default timeout
     */
    public static WebElement waitForElement(WebDriver driver, By locator) {
        return waitForElement(driver, locator, 10);
    }
} 