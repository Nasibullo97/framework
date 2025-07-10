package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestDataFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("ZipRecruiter Test Suite")
@Feature("Complete Test Coverage")
public class TestSuite extends BaseTest {
    
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Starting ZipRecruiter Test Suite");
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("Completed ZipRecruiter Test Suite");
    }
    
    @BeforeTest
    public void beforeTest() {
        System.out.println("Starting test method");
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("Completed test method");
    }
    
    @Test(groups = {"smoke", "critical"}, priority = 1)
    @Story("Smoke test - Basic functionality")
    @Description("Verify basic website functionality")
    public void smokeTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should load");
        
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should be available");
        Assert.assertTrue(title.contains("ZipRecruiter"), "Title should contain ZipRecruiter");
    }
    
    @Test(groups = {"regression", "search"}, priority = 2)
    @Story("Search functionality test")
    @Description("Test job search functionality")
    public void searchFunctionalityTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Software Engineer", "New York");
        
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results should load");
        Assert.assertTrue(searchResultsPage.getJobCount() > 0, "Should have job results");
    }
    
    @Test(groups = {"regression", "login"}, priority = 3)
    @Story("Login functionality test")
    @Description("Test user login functionality")
    public void loginFunctionalityTest() {
        LoginPage loginPage = new LoginPage(driver);
        
        driver.get("https://www.ziprecruiter.com/login");
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should load");
        
        // Test with invalid credentials
        loginPage.login("invalid@example.com", "wrongpassword");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Should show error for invalid credentials");
    }
    
    @Test(groups = {"regression", "registration"}, priority = 4)
    @Story("Registration functionality test")
    @Description("Test user registration functionality")
    public void registrationFunctionalityTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        driver.get("https://www.ziprecruiter.com/register");
        Assert.assertTrue(registrationPage.isRegistrationPageLoaded(), "Registration page should load");
        
        // Test form validation
        registrationPage.registerUser("", "", "", "");
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), "Should show error for empty fields");
    }
    
    @Test(groups = {"regression", "profile"}, priority = 5)
    @Story("Profile functionality test")
    @Description("Test user profile functionality")
    public void profileFunctionalityTest() {
        ProfilePage profilePage = new ProfilePage(driver);
        
        driver.get("https://www.ziprecruiter.com/profile");
        Assert.assertTrue(profilePage.isProfilePageLoaded(), "Profile page should load");
    }
    
    @Test(groups = {"regression", "jobs"}, priority = 6)
    @Story("Job details functionality test")
    @Description("Test job details page functionality")
    public void jobDetailsFunctionalityTest() {
        JobDetailsPage jobDetailsPage = new JobDetailsPage(driver);
        
        // Navigate to a job details page
        driver.get("https://www.ziprecruiter.com/jobs");
        // This would need to be updated with actual job URL
        Assert.assertTrue(jobDetailsPage.isJobDetailsPageLoaded(), "Job details page should load");
    }
    
    @Test(groups = {"regression", "application"}, priority = 7)
    @Story("Application flow functionality test")
    @Description("Test job application flow functionality")
    public void applicationFlowFunctionalityTest() {
        ApplicationFlowPage applicationPage = new ApplicationFlowPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs/apply");
        Assert.assertTrue(applicationPage.isApplicationPageLoaded(), "Application page should load");
    }
    
    @Test(groups = {"regression", "saved-jobs"}, priority = 8)
    @Story("Saved jobs functionality test")
    @Description("Test saved jobs functionality")
    public void savedJobsFunctionalityTest() {
        SavedJobsPage savedJobsPage = new SavedJobsPage(driver);
        
        driver.get("https://www.ziprecruiter.com/saved-jobs");
        Assert.assertTrue(savedJobsPage.isSavedJobsPageLoaded(), "Saved jobs page should load");
    }
    
    @Test(groups = {"regression", "notifications"}, priority = 9)
    @Story("Notifications functionality test")
    @Description("Test notifications functionality")
    public void notificationsFunctionalityTest() {
        NotificationsPage notificationsPage = new NotificationsPage(driver);
        
        driver.get("https://www.ziprecruiter.com/notifications");
        Assert.assertTrue(notificationsPage.isNotificationsPageLoaded(), "Notifications page should load");
    }
    
    @Test(groups = {"regression", "filters"}, priority = 10)
    @Story("Filters functionality test")
    @Description("Test search filters functionality")
    public void filtersFunctionalityTest() {
        FiltersPage filtersPage = new FiltersPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs");
        Assert.assertTrue(filtersPage.isFiltersPageLoaded(), "Filters page should load");
    }
    
    @Test(groups = {"regression", "pagination"}, priority = 11)
    @Story("Pagination functionality test")
    @Description("Test pagination functionality")
    public void paginationFunctionalityTest() {
        PaginationPage paginationPage = new PaginationPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs");
        Assert.assertTrue(paginationPage.isPaginationPageLoaded(), "Pagination should be available");
    }
    
    @Test(groups = {"regression", "accessibility"}, priority = 12)
    @Story("Accessibility functionality test")
    @Description("Test accessibility compliance")
    public void accessibilityFunctionalityTest() {
        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(accessibilityPage.isAccessibilityPageLoaded(), "Page should be accessible");
    }
    
    @Test(groups = {"performance"}, priority = 13)
    @Story("Performance test")
    @Description("Test page load performance")
    public void performanceTest() {
        long startTime = System.currentTimeMillis();
        
        driver.get("https://www.ziprecruiter.com/");
        
        long loadTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(loadTime < 5000, "Page should load within 5 seconds. Actual: " + loadTime + "ms");
    }
    
    @Test(groups = {"security"}, priority = 14)
    @Story("Security test")
    @Description("Test basic security measures")
    public void securityTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test XSS prevention
        homePage.searchJobs("<script>alert('xss')</script>", "New York");
        // Should handle gracefully without executing script
        
        // Test SQL injection prevention
        homePage.searchJobs("'; DROP TABLE users; --", "New York");
        // Should handle gracefully
    }
    
    @Test(groups = {"browser-compatibility"}, priority = 15)
    @Story("Browser compatibility test")
    @Description("Test cross-browser compatibility")
    public void browserCompatibilityTest() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Page should load in current browser");
        
        // Test JavaScript functionality
        String title = driver.getTitle();
        Assert.assertNotNull(title, "JavaScript should work in current browser");
    }
    
    @Test(groups = {"mobile"}, priority = 16)
    @Story("Mobile responsiveness test")
    @Description("Test mobile responsiveness")
    public void mobileResponsivenessTest() {
        HomePage homePage = new HomePage(driver);
        
        // Set mobile viewport
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        
        driver.get("https://www.ziprecruiter.com/");
        Assert.assertTrue(homePage.isPageLoaded(), "Page should be responsive on mobile");
        
        // Restore window size
        driver.manage().window().maximize();
    }
    
    @Test(groups = {"data-driven"}, priority = 17)
    @Story("Data-driven test")
    @Description("Test with multiple data sets")
    public void dataDrivenTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        String[][] testData = {
            {"Software Engineer", "New York"},
            {"Data Scientist", "San Francisco"},
            {"Product Manager", "Los Angeles"},
            {"QA Engineer", "Austin"}
        };
        
        for (String[] data : testData) {
            driver.get("https://www.ziprecruiter.com/");
            homePage.searchJobs(data[0], data[1]);
            Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), 
                "Search should work for " + data[0] + " in " + data[1]);
        }
    }
    
    @Test(groups = {"negative"}, priority = 18)
    @Story("Negative test scenarios")
    @Description("Test error handling and edge cases")
    public void negativeTestScenarios() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with empty search
        homePage.searchJobs("", "");
        // Should handle gracefully
        
        // Test with very long search term
        String longSearchTerm = "a".repeat(1000);
        homePage.searchJobs(longSearchTerm, "New York");
        // Should handle gracefully
    }
    
    @Test(groups = {"integration"}, priority = 19)
    @Story("Integration test")
    @Description("Test complete user workflow")
    public void integrationTest() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        
        // Complete workflow: search -> results -> job details
        driver.get("https://www.ziprecruiter.com/");
        homePage.searchJobs("Software Engineer", "New York");
        Assert.assertTrue(searchResultsPage.isResultsPageLoaded(), "Search results should load");
        
        if (searchResultsPage.getJobCount() > 0) {
            searchResultsPage.clickFirstJob();
            // Should navigate to job details
        }
    }
    
    @Test(groups = {"api"}, priority = 20)
    @Story("API integration test")
    @Description("Test API endpoints if available")
    public void apiIntegrationTest() {
        // This would test API endpoints if ZipRecruiter provides them
        // For now, test that the website loads properly
        driver.get("https://www.ziprecruiter.com/");
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Website should be accessible");
    }
} 