package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FiltersPage extends BasePage {
    
    // Filters page locators
    private By salaryMinField = By.cssSelector("input[name='salaryMin'], #salaryMin, .salary-min");
    private By salaryMaxField = By.cssSelector("input[name='salaryMax'], #salaryMax, .salary-max");
    private By jobTypeCheckboxes = By.cssSelector("input[name='jobType'], .job-type-checkbox");
    private By experienceLevelDropdown = By.cssSelector("select[name='experience'], #experience, .experience-select");
    private By companySizeDropdown = By.cssSelector("select[name='companySize'], #companySize, .company-size-select");
    private By locationField = By.cssSelector("input[name='location'], #location, .location-input");
    private By remoteCheckbox = By.cssSelector("input[name='remote'], #remote, .remote-checkbox");
    private By datePostedDropdown = By.cssSelector("select[name='datePosted'], #datePosted, .date-select");
    private By applyFiltersButton = By.cssSelector(".apply-filters, button[type='submit'], [data-testid='apply']");
    private By clearFiltersButton = By.cssSelector(".clear-filters, .reset, [data-testid='clear']");
    private By activeFilters = By.cssSelector(".active-filter, .filter-tag, [data-testid='active-filter']");
    private By removeFilterButton = By.cssSelector(".remove-filter, .filter-close, [data-testid='remove-filter']");
    private By resultsCount = By.cssSelector(".results-count, .job-count, [data-testid='count']");
    private By filterPanel = By.cssSelector(".filter-panel, .filters-sidebar, [data-testid='filters']");
    
    public FiltersPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(filterPanel) || isElementDisplayed(applyFiltersButton);
    }
    
    public void setSalaryRange(String minSalary, String maxSalary) {
        typeText(salaryMinField, minSalary);
        typeText(salaryMaxField, maxSalary);
    }
    
    public void selectJobType(String jobType) {
        List<WebElement> checkboxes = driver.findElements(jobTypeCheckboxes);
        for (WebElement checkbox : checkboxes) {
            String label = checkbox.findElement(By.xpath("following-sibling::label")).getText();
            if (label.toLowerCase().contains(jobType.toLowerCase())) {
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
                break;
            }
        }
    }
    
    public void selectExperienceLevel(String experience) {
        if (isElementDisplayed(experienceLevelDropdown)) {
            clickElement(experienceLevelDropdown);
            By option = By.xpath("//option[contains(text(),'" + experience + "')]");
            clickElement(option);
        }
    }
    
    public void selectCompanySize(String companySize) {
        if (isElementDisplayed(companySizeDropdown)) {
            clickElement(companySizeDropdown);
            By option = By.xpath("//option[contains(text(),'" + companySize + "')]");
            clickElement(option);
        }
    }
    
    public void setLocation(String location) {
        typeText(locationField, location);
    }
    
    public void toggleRemoteWork(boolean enable) {
        WebElement remoteCheckboxElement = waitForElement(remoteCheckbox);
        if (remoteCheckboxElement.isSelected() != enable) {
            clickElement(remoteCheckbox);
        }
    }
    
    public void selectDatePosted(String dateRange) {
        if (isElementDisplayed(datePostedDropdown)) {
            clickElement(datePostedDropdown);
            By option = By.xpath("//option[contains(text(),'" + dateRange + "')]");
            clickElement(option);
        }
    }
    
    public void applyFilters() {
        clickElement(applyFiltersButton);
    }
    
    public void clearAllFilters() {
        clickElement(clearFiltersButton);
    }
    
    public int getActiveFiltersCount() {
        List<WebElement> activeFiltersList = driver.findElements(activeFilters);
        return activeFiltersList.size();
    }
    
    public void removeFilter(int index) {
        List<WebElement> removeButtons = driver.findElements(removeFilterButton);
        if (index < removeButtons.size()) {
            removeButtons.get(index).click();
        }
    }
    
    public void removeFilterByText(String filterText) {
        List<WebElement> activeFiltersList = driver.findElements(activeFilters);
        for (int i = 0; i < activeFiltersList.size(); i++) {
            if (activeFiltersList.get(i).getText().contains(filterText)) {
                removeFilter(i);
                break;
            }
        }
    }
    
    public String getResultsCount() {
        return getText(resultsCount);
    }
    
    public List<String> getActiveFilters() {
        List<WebElement> activeFiltersList = driver.findElements(activeFilters);
        return activeFiltersList.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public boolean isFilterActive(String filterText) {
        List<String> activeFilters = getActiveFilters();
        return activeFilters.stream().anyMatch(filter -> filter.contains(filterText));
    }
    
    public void waitForFiltersToApply() {
        // Wait for results to update after applying filters
        try {
            Thread.sleep(2000); // Give time for results to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void applyAdvancedFilters(String minSalary, String maxSalary, String jobType, 
                                   String experience, String companySize, String location, 
                                   boolean remote, String datePosted) {
        setSalaryRange(minSalary, maxSalary);
        selectJobType(jobType);
        selectExperienceLevel(experience);
        selectCompanySize(companySize);
        setLocation(location);
        toggleRemoteWork(remote);
        selectDatePosted(datePosted);
        applyFilters();
        waitForFiltersToApply();
    }
    
    public void resetToDefaultFilters() {
        clearAllFilters();
        waitForFiltersToApply();
    }
    
    public boolean hasActiveFilters() {
        return getActiveFiltersCount() > 0;
    }
    
    public void setJobType(String jobType) {
        selectJobType(jobType);
    }
    
    public void setExperienceLevel(String experience) {
        selectExperienceLevel(experience);
    }
    
    public boolean areFiltersCleared() {
        return getActiveFiltersCount() == 0;
    }
    
    public boolean isFiltersPageLoaded() {
        return isPageLoaded();
    }
} 