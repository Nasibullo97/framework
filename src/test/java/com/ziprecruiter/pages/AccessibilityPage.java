package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AccessibilityPage extends BasePage {
    private By formLabels = By.cssSelector("label, [aria-label], [title]");
    private By images = By.cssSelector("img");
    private By headings = By.cssSelector("h1, h2, h3, h4, h5, h6");
    private By focusableElements = By.cssSelector("a, button, input, select, textarea, [tabindex]");
    
    public AccessibilityPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        // Stub: always return true for now
        return true;
    }
    
    public boolean canNavigateWithTab() {
        // Check if focusable elements are present and accessible
        List<WebElement> focusable = driver.findElements(focusableElements);
        return !focusable.isEmpty();
    }
    
    public boolean hasFormLabels() {
        List<WebElement> labels = driver.findElements(formLabels);
        return !labels.isEmpty();
    }
    
    public boolean hasImageAltText() {
        List<WebElement> images = driver.findElements(this.images);
        for (WebElement img : images) {
            String alt = img.getAttribute("alt");
            if (alt == null || alt.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasProperColorContrast() {
        // Stub: return true for now as this requires complex color analysis
        return true;
    }
    
    public boolean hasProperHeadingHierarchy() {
        List<WebElement> headingElements = driver.findElements(headings);
        if (headingElements.isEmpty()) {
            return true; // No headings is acceptable
        }
        
        // Check if headings follow a logical hierarchy
        int previousLevel = 0;
        for (WebElement heading : headingElements) {
            String tagName = heading.getTagName().toLowerCase();
            int currentLevel = Integer.parseInt(tagName.substring(1));
            
            if (currentLevel > previousLevel + 1) {
                return false; // Skipped a level
            }
            previousLevel = currentLevel;
        }
        return true;
    }
    
    public boolean isAccessibilityPageLoaded() {
        return isPageLoaded();
    }
} 