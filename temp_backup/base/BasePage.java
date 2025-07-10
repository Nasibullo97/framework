package com.ziprecruiter.base;

import com.ziprecruiter.utils.WaitUtils;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {
    protected WebDriver driver;
    
    // Common locators that might be used across pages
    protected By logo = By.cssSelector(".logo, .brand, [data-testid='logo']");
    protected By searchBox = By.cssSelector("input[name='search'], input[placeholder*='search'], .search-input");
    protected By locationBox = By.cssSelector("input[name='location'], input[placeholder*='location'], .location-input");
    protected By searchButton = By.cssSelector("button[type='submit'], .search-button, [data-testid='search']");
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Common page actions
    protected WebElement waitForElement(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }
    
    protected WebElement waitForClickable(By locator) {
        return WaitUtils.waitForClickable(driver, locator);
    }
    
    protected void clickElement(By locator) {
        WebElement element = waitForClickable(locator);
        ElementUtils.click(element);
    }
    
    protected void typeText(By locator, String text) {
        WebElement element = waitForElement(locator);
        ElementUtils.type(element, text);
    }
    
    protected String getText(By locator) {
        WebElement element = waitForElement(locator);
        return ElementUtils.getText(element);
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return ElementUtils.isDisplayed(waitForElement(locator));
        } catch (Exception e) {
            return false;
        }
    }
    
    // Navigation methods
    public void clickLogo() {
        clickElement(logo);
    }
    
    public void searchJobs(String jobTitle, String location) {
        typeText(searchBox, jobTitle);
        typeText(locationBox, location);
        clickElement(searchButton);
    }
    
    // Abstract method that each page must implement
    public abstract boolean isPageLoaded();
} 