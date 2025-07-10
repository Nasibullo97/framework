package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestDataFactory;
import com.ziprecruiter.utils.WaitUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Integration Testing")
@Feature("End-to-End User Workflows")
public class IntegrationTests extends BaseTest {
    
    @Test
    @Story("Complete job search workflow")
    @Description("Test complete job search from home page to application")
    public void testCompleteJobSearchWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        JobDetailsPage jobDetailsPage = new JobDetailsPage(driver);
        ApplicationFlowPage applicationPage = new ApplicationFlowPage(driver);
        
        // Step 1: Navigate to home page and search
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Software Engineer", "New York");
        
        // Step 2: Verify search results
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results should load");
        Assert.assertTrue(searchResultsPage.getJobCount() > 0, "Should have job results");
        
        // Step 3: Click on first job
        searchResultsPage.clickFirstJob();
        
        // Step 4: Verify job details page
        Assert.assertTrue(jobDetailsPage.isJobDetailsPageLoaded(), "Job details should load");
        Assert.assertNotNull(jobDetailsPage.getJobTitle(), "Job title should be displayed");
        
        // Step 5: Start application process
        jobDetailsPage.clickApplyButton();
        
        // Step 6: Verify application flow
        Assert.assertTrue(applicationPage.isApplicationPageLoaded(), "Application page should load");
    }
    
    @Test
    @Story("User registration and profile setup")
    @Description("Test complete user registration and profile creation workflow")
    public void testUserRegistrationAndProfileSetup() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        
        // Step 1: Register new user
        driver.get("https://www.ziprecruiter.com/register");
        String email = "test" + System.currentTimeMillis() + "@example.com";
        registrationPage.registerUser(email, "Password123!", "John", "Doe");
        
        // Step 2: Verify registration success
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), "Registration should succeed");
        
        // Step 3: Navigate to profile page
        driver.get("https://www.ziprecruiter.com/profile");
        
        // Step 4: Update profile information
        profilePage.updateProfile("John", "Doe", "1234567890", "New York", "Experienced software engineer");
        
        // Step 5: Verify profile update
        Assert.assertTrue(profilePage.isProfileUpdated(), "Profile should be updated successfully");
    }
    
    @Test
    @Story("Job save and notification workflow")
    @Description("Test saving jobs and setting up notifications")
    public void testJobSaveAndNotificationWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        JobDetailsPage jobDetailsPage = new JobDetailsPage(driver);
        SavedJobsPage savedJobsPage = new SavedJobsPage(driver);
        NotificationsPage notificationsPage = new NotificationsPage(driver);
        
        // Step 1: Search for jobs
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Data Scientist", "San Francisco");
        
        // Step 2: Save a job
        searchResultsPage.clickFirstJob();
        jobDetailsPage.saveJob();
        
        // Step 3: Verify job is saved
        Assert.assertTrue(jobDetailsPage.isJobSaved(), "Job should be saved successfully");
        
        // Step 4: Navigate to saved jobs
        driver.get("https://www.ziprecruiter.com/saved-jobs");
        
        // Step 5: Verify saved job appears
        Assert.assertTrue(savedJobsPage.hasSavedJobs(), "Saved job should appear in list");
        
        // Step 6: Set up job alerts
        driver.get("https://www.ziprecruiter.com/notifications");
        notificationsPage.createJobAlert("Software Engineer", "New York", "daily");
        
        // Step 7: Verify alert is created
        Assert.assertTrue(notificationsPage.isAlertCreated(), "Job alert should be created");
    }
    
    @Test
    @Story("Advanced search with filters")
    @Description("Test advanced search functionality with multiple filters")
    public void testAdvancedSearchWithFilters() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        FiltersPage filtersPage = new FiltersPage(driver);
        PaginationPage paginationPage = new PaginationPage(driver);
        
        // Step 1: Perform initial search
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Product Manager", "Los Angeles");
        
        // Step 2: Apply salary filter
        filtersPage.setSalaryRange("80000", "150000");
        filtersPage.setJobType("Full-time");
        filtersPage.setExperienceLevel("Mid-level");
        filtersPage.applyFilters();
        
        // Step 3: Verify filtered results
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Filtered results should load");
        
        // Step 4: Navigate through pagination
        if (paginationPage.hasNextPage()) {
            paginationPage.navigateToNextPage();
            Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Next page should load");
        }
        
        // Step 5: Clear filters
        filtersPage.clearAllFilters();
        Assert.assertTrue(filtersPage.areFiltersCleared(), "Filters should be cleared");
    }
    
    @Test
    @Story("Cross-browser compatibility workflow")
    @Description("Test complete workflow across different browsers")
    public void testCrossBrowserCompatibilityWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        // Step 1: Test basic functionality
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Page should load in current browser");
        
        // Step 2: Test search functionality
        homePage.searchJobs("QA Engineer", "Austin");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search should work in current browser");
        
        // Step 3: Test JavaScript functionality
        String title = driver.getTitle();
        Assert.assertNotNull(title, "JavaScript should work in current browser");
        
        // Step 4: Test responsive design
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        Assert.assertTrue(homePage.isPageLoaded(), "Page should be responsive");
        
        // Step 5: Restore window size
        driver.manage().window().maximize();
    }
    
    @Test
    @Story("Accessibility compliance workflow")
    @Description("Test complete workflow with accessibility requirements")
    public void testAccessibilityComplianceWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        
        // Step 1: Test keyboard navigation
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(accessibilityPage.canNavigateWithTab(), "Should support keyboard navigation");
        
        // Step 2: Test search with keyboard
        homePage.searchJobsWithKeyboard("UX Designer", "Seattle");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search should work with keyboard");
        
        // Step 3: Test screen reader compatibility
        Assert.assertTrue(accessibilityPage.hasFormLabels(), "Should have form labels for screen readers");
        Assert.assertTrue(accessibilityPage.hasImageAltText(), "Should have image alt text");
        
        // Step 4: Test color contrast
        Assert.assertTrue(accessibilityPage.hasProperColorContrast(), "Should have proper color contrast");
        
        // Step 5: Test heading hierarchy
        Assert.assertTrue(accessibilityPage.hasProperHeadingHierarchy(), "Should have proper heading hierarchy");
    }
    
    @Test
    @Story("Performance and load testing workflow")
    @Description("Test complete workflow under performance conditions")
    public void testPerformanceAndLoadWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        long startTime = System.currentTimeMillis();
        
        // Step 1: Load home page
        driver.get("https://www.ziprecruiter.com/");
        WaitUtils.waitForPageLoad(driver);
        
        long homePageLoadTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(homePageLoadTime < 5000, "Home page should load within 5 seconds");
        
        // Step 2: Perform search
        long searchStartTime = System.currentTimeMillis();
        homePage.searchJobs("DevOps Engineer", "Chicago");
        WaitUtils.waitForPageLoad(driver);
        
        long searchLoadTime = System.currentTimeMillis() - searchStartTime;
        Assert.assertTrue(searchLoadTime < 8000, "Search results should load within 8 seconds");
        
        // Step 3: Verify results
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results should load correctly");
        
        // Step 4: Test memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        Assert.assertTrue(usedMemory < maxMemory * 0.8, "Memory usage should be reasonable");
    }
    
    @Test
    @Story("Error handling and recovery workflow")
    @Description("Test complete workflow with error scenarios")
    public void testErrorHandlingAndRecoveryWorkflow() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        // Step 1: Test with invalid search
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("", "New York");
        // Should handle gracefully
        
        // Step 2: Test with no results search
        homePage.searchJobs("VerySpecificJobTitleThatDoesNotExist", "New York");
        Assert.assertTrue(searchResultsPage.isNoResultsPage(), "Should show no results page");
        
        // Step 3: Test recovery with valid search
        homePage.searchJobs("Software Engineer", "New York");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Should recover with valid search");
        
        // Step 4: Test page refresh recovery
        driver.navigate().refresh();
        WaitUtils.waitForPageLoad(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Should recover after page refresh");
    }
} 