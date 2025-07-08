package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.SearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class SearchResultsPageTest extends BaseTest {
    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return new Object[][] {
            {"Software Engineer", "New York, NY"},
            {"QA Analyst", "San Francisco, CA"},
            {"Data Scientist", "Remote"},
            {"GibberishQueryNoResults", "Nowhere"}
        };
    }

    @DataProvider(name = "csvSearchData")
    public Object[][] csvSearchData() throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/search_data.csv"))) {
            String[] nextLine;
            while (true) {
                try {
                    nextLine = reader.readNext();
                } catch (CsvValidationException e) {
                    System.out.println("CSV validation error: " + e.getMessage());
                    break;
                }
                if (nextLine == null) break;
                if (nextLine.length >= 2 && !nextLine[0].startsWith("#")) {
                    data.add(new Object[]{nextLine[0], nextLine[1]});
                }
            }
        } catch (IOException e) {
            System.out.println("CSV file not found or unreadable: " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "searchData")
    public void testJobSearchReturnsResults(String searchTerm, String location) {
        runSearchTest(searchTerm, location);
    }

    @Test(dataProvider = "csvSearchData")
    public void testJobSearchReturnsResults_CSV(String searchTerm, String location) {
        runSearchTest(searchTerm, location);
    }

    // Special characters and no results are already covered in the data providers and CSV.
    // If you want a dedicated test method for special characters, you can add:
    @Test
    public void testSearchWithSpecialCharacters() {
        runSearchTest("@!#Special*Chars", "Los Angeles, CA");
    }
    // For no results:
    @Test
    public void testSearchWithNoResults() {
        runSearchTest("GibberishQueryNoResults", "Nowhere");
    }

    private void runSearchTest(String searchTerm, String location) {
        driver.get("https://www.ziprecruiter.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Wait for search fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("location")));
        // Enter search term and location
        driver.findElement(By.name("search")).sendKeys(searchTerm);
        driver.findElement(By.name("location")).clear();
        driver.findElement(By.name("location")).sendKeys(location);
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
                Assert.fail("No jobs found for the search query: " + searchTerm + ", " + location);
            } else {
                System.out.println("No job results or 'no results' message found after search. Printing page source for debugging:");
                System.out.println(driver.getPageSource());
                Assert.fail("Job results not found after search: " + searchTerm + ", " + location);
            }
        }
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        int jobCount = resultsPage.getJobCount();
        System.out.println("Number of jobs found for [" + searchTerm + ", " + location + "]: " + jobCount);
        Assert.assertTrue(jobCount > 0, "There should be at least one job result for: " + searchTerm + ", " + location);
    }
}