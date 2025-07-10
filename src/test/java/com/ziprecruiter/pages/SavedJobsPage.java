package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SavedJobsPage extends BasePage {
    
    // Saved jobs page locators
    private By savedJobsList = By.cssSelector(".saved-job, .bookmarked-job, [data-testid='saved-job']");
    private By jobTitle = By.cssSelector(".job-title, h3, [data-testid='job-title']");
    private By companyName = By.cssSelector(".company-name, .employer, [data-testid='company']");
    private By removeButton = By.cssSelector(".remove-job, .unsave, [data-testid='remove']");
    private By applyButton = By.cssSelector(".apply-button, button:contains('Apply'), [data-testid='apply']");
    private By emptyState = By.cssSelector(".empty-state, .no-saved-jobs, [data-testid='empty']");
    private By sortDropdown = By.cssSelector(".sort-dropdown, select[name='sort'], #sort");
    private By filterButton = By.cssSelector(".filter-button, .filters, [data-testid='filter']");
    private By searchSavedJobs = By.cssSelector("input[placeholder*='search'], .search-saved, #search");
    private By jobCount = By.cssSelector(".job-count, .saved-count, [data-testid='count']");
    private By bulkActions = By.cssSelector(".bulk-actions, .select-all, [data-testid='bulk']");
    private By selectAllCheckbox = By.cssSelector("input[type='checkbox'], .select-all-checkbox");
    
    public SavedJobsPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(savedJobsList) || isElementDisplayed(emptyState);
    }
    
    public int getSavedJobsCount() {
        List<WebElement> savedJobs = driver.findElements(savedJobsList);
        return savedJobs.size();
    }
    
    public boolean hasSavedJobs() {
        return getSavedJobsCount() > 0;
    }
    
    public boolean isEmpty() {
        return isElementDisplayed(emptyState);
    }
    
    public void clickJob(int index) {
        List<WebElement> savedJobs = driver.findElements(savedJobsList);
        if (index < savedJobs.size()) {
            savedJobs.get(index).click();
        }
    }
    
    public void removeJob(int index) {
        List<WebElement> removeButtons = driver.findElements(removeButton);
        if (index < removeButtons.size()) {
            removeButtons.get(index).click();
        }
    }
    
    public void applyToJob(int index) {
        List<WebElement> applyButtons = driver.findElements(applyButton);
        if (index < applyButtons.size()) {
            applyButtons.get(index).click();
        }
    }
    
    public String getJobTitle(int index) {
        List<WebElement> jobTitles = driver.findElements(this.jobTitle);
        if (index < jobTitles.size()) {
            return jobTitles.get(index).getText();
        }
        return "";
    }
    
    public String getCompanyName(int index) {
        List<WebElement> companyNames = driver.findElements(this.companyName);
        if (index < companyNames.size()) {
            return companyNames.get(index).getText();
        }
        return "";
    }
    
    public void sortBy(String sortOption) {
        if (isElementDisplayed(sortDropdown)) {
            clickElement(sortDropdown);
            By option = By.xpath("//option[contains(text(),'" + sortOption + "')]");
            clickElement(option);
        }
    }
    
    public void searchJobs(String searchTerm) {
        typeText(searchSavedJobs, searchTerm);
    }
    
    public void clickFilter() {
        clickElement(filterButton);
    }
    
    public void selectAllJobs() {
        if (isElementDisplayed(selectAllCheckbox)) {
            clickElement(selectAllCheckbox);
        }
    }
    
    public void removeAllSelectedJobs() {
        if (isElementDisplayed(bulkActions)) {
            // Look for bulk remove action
            By bulkRemove = By.cssSelector(".bulk-remove, .remove-selected, [data-testid='bulk-remove']");
            if (isElementDisplayed(bulkRemove)) {
                clickElement(bulkRemove);
            }
        }
    }
    
    public String getJobCountText() {
        return getText(jobCount);
    }
    
    public List<String> getAllJobTitles() {
        List<WebElement> jobTitles = driver.findElements(this.jobTitle);
        return jobTitles.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public List<String> getAllCompanyNames() {
        List<WebElement> companyNames = driver.findElements(this.companyName);
        return companyNames.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public void waitForJobsToLoad() {
        try {
            waitForElement(savedJobsList);
        } catch (Exception e) {
            // If no saved jobs, wait for empty state
            waitForElement(emptyState);
        }
    }
    
    public boolean isJobInList(String jobTitle) {
        List<String> titles = getAllJobTitles();
        return titles.stream().anyMatch(title -> title.contains(jobTitle));
    }
    
    public int getJobIndexByTitle(String jobTitle) {
        List<String> titles = getAllJobTitles();
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).contains(jobTitle)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isSavedJobsPageLoaded() {
        return isPageLoaded();
    }
} 