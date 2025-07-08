package com.ziprecruiter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Arrays;
import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private List<By> jobCardLocators = Arrays.asList(
        By.cssSelector(".job_content"),
        By.cssSelector(".jobCard"),
        By.cssSelector(".job-result-card"),
        By.cssSelector("[data-testid*='job']"),
        By.cssSelector("article"),
        By.cssSelector("li.job")
    );

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getJobCount() {
        int total = 0;
        for (By locator : jobCardLocators) {
            List<WebElement> jobs = driver.findElements(locator);
            total += jobs.size();
        }
        return total;
    }
}