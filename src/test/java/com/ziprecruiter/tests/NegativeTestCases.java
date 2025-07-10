package com.ziprecruiter.tests;

import com.ziprecruiter.base.BaseTest;
import com.ziprecruiter.pages.*;
import com.ziprecruiter.data.TestDataFactory;
import com.ziprecruiter.utils.WaitUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

@Epic("Negative Testing")
@Feature("Edge Cases and Error Handling")
public class NegativeTestCases extends BaseTest {
    
    @Test
    @Story("Invalid login attempts")
    @Description("Test various invalid login scenarios")
    public void testInvalidLoginScenarios() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Test with empty credentials
        driver.get("https://www.ziprecruiter.com/login");
        loginPage.login("", "");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Should show error for empty credentials");
        
        // Test with invalid email format
        loginPage.login("invalid-email", "password123");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Should show error for invalid email");
        
        // Test with XSS attempt
        loginPage.login("<script>alert('xss')</script>", "password123");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Should handle XSS attempts");
    }
    
    @Test
    @Story("Registration validation")
    @Description("Test registration form validation")
    public void testRegistrationValidation() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        driver.get("https://www.ziprecruiter.com/register");
        
        // Test with invalid email
        registrationPage.registerUser("invalid-email", "password123", "John", "Doe");
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), "Should show error for invalid email");
        
        // Test with weak password
        registrationPage.registerUser("test@example.com", "123", "John", "Doe");
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), "Should show error for weak password");
        
        // Test with empty required fields
        registrationPage.registerUser("", "", "", "");
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(), "Should show error for empty fields");
    }
    
    @Test
    @Story("Search with invalid inputs")
    @Description("Test search functionality with edge cases")
    public void testInvalidSearchInputs() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with empty search
        homePage.searchJobs("", "New York");
        // Should handle gracefully or show appropriate message
        
        // Test with very long search term
        String longSearchTerm = "a".repeat(1000);
        homePage.searchJobs(longSearchTerm, "New York");
        // Should handle gracefully
        
        // Test with special characters
        homePage.searchJobs("!@#$%^&*()", "New York");
        // Should handle gracefully
    }
    
    @Test
    @Story("Profile update validation")
    @Description("Test profile update with invalid data")
    public void testProfileUpdateValidation() {
        ProfilePage profilePage = new ProfilePage(driver);
        
        driver.get("https://www.ziprecruiter.com/profile");
        
        // Test with invalid phone number
        profilePage.updateProfile("John", "Doe", "invalid-phone", "New York", "Bio");
        Assert.assertTrue(profilePage.isErrorMessageDisplayed(), "Should show error for invalid phone");
        
        // Test with XSS in bio
        profilePage.updateProfile("John", "Doe", "1234567890", "New York", "<script>alert('xss')</script>");
        // Should sanitize or show error
    }
    
    @Test
    @Story("Job application edge cases")
    @Description("Test job application with invalid scenarios")
    public void testJobApplicationEdgeCases() {
        ApplicationFlowPage applicationPage = new ApplicationFlowPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs/apply");
        
        // Test without selecting resume
        applicationPage.completeApplication("", "Cover letter", new String[]{"Answer 1"});
        Assert.assertTrue(applicationPage.hasErrors(), "Should show error for missing resume");
        
        // Test with empty cover letter
        applicationPage.completeApplication("Resume.pdf", "", new String[]{"Answer 1"});
        // Should handle gracefully
    }
    
    @Test
    @Story("Filter validation")
    @Description("Test filter functionality with invalid inputs")
    public void testFilterValidation() {
        FiltersPage filtersPage = new FiltersPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs");
        
        // Test with invalid salary range
        filtersPage.setSalaryRange("abc", "xyz");
        filtersPage.applyFilters();
        Assert.assertTrue(filtersPage.hasActiveFilters(), "Should handle invalid salary gracefully");
        
        // Test with negative salary
        filtersPage.setSalaryRange("-1000", "-500");
        filtersPage.applyFilters();
        // Should handle gracefully
    }
    
    @Test
    @Story("Pagination edge cases")
    @Description("Test pagination with boundary conditions")
    public void testPaginationEdgeCases() {
        PaginationPage paginationPage = new PaginationPage(driver);
        
        driver.get("https://www.ziprecruiter.com/jobs");
        
        // Test navigation to invalid page
        try {
            paginationPage.navigateToPage(999);
            // Should handle gracefully
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
        
        // Test with zero items per page
        paginationPage.setItemsPerPage(0);
        // Should handle gracefully
    }
    
    @Test
    @Story("Accessibility compliance")
    @Description("Test accessibility requirements")
    public void testAccessibilityCompliance() {
        AccessibilityPage accessibilityPage = new AccessibilityPage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test keyboard navigation
        Assert.assertTrue(accessibilityPage.canNavigateWithTab(), "Should support keyboard navigation");
        
        // Test heading hierarchy
        Assert.assertTrue(accessibilityPage.hasProperHeadingHierarchy(), "Should have proper heading hierarchy");
        
        // Test form labels
        Assert.assertTrue(accessibilityPage.hasFormLabels(), "Should have form labels");
        
        // Test image alt text
        Assert.assertTrue(accessibilityPage.hasImageAltText(), "Should have image alt text");
    }
    
    @Test
    @Story("Network error handling")
    @Description("Test behavior under network issues")
    public void testNetworkErrorHandling() {
        // This would require network simulation tools
        // For now, test timeout scenarios
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test with very short timeout
        try {
            WaitUtils.waitForVisible(driver, By.cssSelector(".non-existent-element"), 1);
        } catch (Exception e) {
            // Expected timeout
        }
    }
    
    @Test
    @Story("Browser compatibility")
    @Description("Test cross-browser compatibility")
    public void testBrowserCompatibility() {
        HomePage homePage = new HomePage(driver);
        
        driver.get("https://www.ziprecruiter.com/");
        
        // Test basic functionality across browsers
        Assert.assertTrue(homePage.isPageLoaded(), "Page should load in current browser");
        
        // Test JavaScript execution
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should be available");
    }
} 