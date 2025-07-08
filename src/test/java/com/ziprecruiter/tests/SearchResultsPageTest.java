package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.HomePage;
import com.ziprecruiter.pages.SearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SearchResultsPageTest extends BaseTest {
    @Test
    public void testJobSearchReturnsResults() {
        driver.get("https://www.ziprecruiter.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Wait for search fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("location")));
        // Enter search term and location
        driver.findElement(By.name("search")).sendKeys("Software Engineer");
        driver.findElement(By.name("location")).clear();
        driver.findElement(By.name("location")).sendKeys("New York, NY");
        // Submit search
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Wait for job cards or a 'no results' message
        List<By> jobCardLocators = Arrays.asList(
            By.cssSelector(".job_content"),
            By.cssSelector(".jobCard"),
            By.cssSelector(".job-result-card"),
            By.cssSelector("[data-testid*='job']"),
            By.cssSelector("article"),
            By.cssSelector("li.job")
        );
        By noResultsMsg = By.xpath("//*[contains(text(),'no jobs found') or contains(text(),'No jobs found') or contains(text(),'no results') or contains(text(),'No results')]");
        boolean found = false;
        for (By locator : jobCardLocators) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                found = true;
                break;
            } catch (Exception ignored) {}
        }
        if (!found) {
            // Check for a 'no results' message
            List<WebElement> noResults = driver.findElements(noResultsMsg);
            if (!noResults.isEmpty()) {
                System.out.println("No jobs found for the search query. 'No results' message detected.");
                Assert.fail("No jobs found for the search query.");
            } else {
                System.out.println("No job results or 'no results' message found after search. Printing page source for debugging:");
                System.out.println(driver.getPageSource());
                Assert.fail("Job results not found after search");
            }
        }
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        int jobCount = resultsPage.getJobCount();
        System.out.println("Number of jobs found: " + jobCount);
        Assert.assertTrue(jobCount > 0, "There should be at least one job result.");
    }
}