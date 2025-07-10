package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

public class SearchResultsPage extends BasePage {
    private List<By> jobCardLocators = Arrays.asList(
        By.cssSelector(".job_content"),
        By.cssSelector(".jobCard"),
        By.cssSelector(".job-result-card"),
        By.cssSelector("[data-testid*='job']"),
        By.cssSelector("article"),
        By.cssSelector("li.job")
    );
    
    private By noResultsMessage = By.cssSelector(".no-results, .empty-state, [data-testid='no-results']");
    private By firstJobCard = By.cssSelector(".job-card:first-child, .job-content:first-child, article:first-child");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(jobCardLocators.get(0)) || isElementDisplayed(noResultsMessage);
    }

    public int getJobCount() {
        int total = 0;
        for (By locator : jobCardLocators) {
            List<WebElement> jobs = driver.findElements(locator);
            total += jobs.size();
        }
        return total;
    }
    
    public boolean isResultsPageLoaded() {
        return isPageLoaded();
    }
    
    public void clickFirstJob() {
        if (isElementDisplayed(firstJobCard)) {
            clickElement(firstJobCard);
        } else {
            // Fallback: try to click the first job found with any locator
            for (By locator : jobCardLocators) {
                List<WebElement> jobs = driver.findElements(locator);
                if (!jobs.isEmpty()) {
                    jobs.get(0).click();
                    break;
                }
            }
        }
    }
    
    public boolean isNoResultsPage() {
        return isElementDisplayed(noResultsMessage);
    }
}