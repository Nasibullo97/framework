package com.ziprecruiter.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TimeoutException;

public class ElementUtils {
    
    public static WebElement findElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }
    
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isElementVisible(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static String getText(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        try {
            return driver.findElement(locator).getAttribute(attribute);
        } catch (Exception e) {
            return "";
        }
    }
    
    public static void click(WebDriver driver, By locator) {
        try {
            WaitUtils.waitForClickable(driver, locator);
            driver.findElement(locator).click();
        } catch (Exception e) {
            // Fallback to JavaScript click
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }
    
    public static void clearAndType(WebDriver driver, By locator, String text) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", driver.findElement(locator));
            js.executeScript("arguments[0].value = arguments[1];", driver.findElement(locator), text);
        }
    }
    
    public static void type(WebDriver driver, By locator, String text) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            driver.findElement(locator).sendKeys(text);
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", driver.findElement(locator), text);
        }
    }
    
    public static void clear(WebDriver driver, By locator) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            driver.findElement(locator).clear();
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", driver.findElement(locator));
        }
    }
    
    public static void hover(WebDriver driver, By locator) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(locator)).perform();
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", 
                           driver.findElement(locator));
        }
    }
    
    public static void scrollToElement(WebDriver driver, By locator) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
        } catch (Exception e) {
            // Ignore scroll errors
        }
    }
    
    public static void scrollToTop(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 0);");
        } catch (Exception e) {
            // Ignore scroll errors
        }
    }
    
    public static void scrollToBottom(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e) {
            // Ignore scroll errors
        }
    }
    
    public static boolean isElementSelected(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void selectByVisibleText(WebDriver driver, By locator, String text) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            org.openqa.selenium.support.ui.Select select = 
                new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
            select.selectByVisibleText(text);
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", driver.findElement(locator), text);
        }
    }
    
    public static void selectByValue(WebDriver driver, By locator, String value) {
        try {
            WaitUtils.waitForVisible(driver, locator);
            org.openqa.selenium.support.ui.Select select = 
                new org.openqa.selenium.support.ui.Select(driver.findElement(locator));
            select.selectByValue(value);
        } catch (Exception e) {
            // Fallback to JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", driver.findElement(locator), value);
        }
    }
} 