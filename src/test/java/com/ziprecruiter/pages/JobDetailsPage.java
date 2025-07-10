package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class JobDetailsPage extends BasePage {
    
    // Job details page locators
    private By jobTitle = By.cssSelector(".job-title, h1, [data-testid='job-title']");
    private By companyName = By.cssSelector(".company-name, .employer-name, [data-testid='company']");
    private By jobLocation = By.cssSelector(".job-location, .location, [data-testid='location']");
    private By jobSalary = By.cssSelector(".salary, .compensation, [data-testid='salary']");
    private By jobDescription = By.cssSelector(".job-description, .description, [data-testid='description']");
    private By applyButton = By.cssSelector(".apply-button, button:contains('Apply'), [data-testid='apply']");
    private By saveJobButton = By.cssSelector(".save-job, .bookmark, [data-testid='save']");
    private By shareJobButton = By.cssSelector(".share-job, .share, [data-testid='share']");
    private By companyLogo = By.cssSelector(".company-logo, img[alt*='logo']");
    private By requirementsList = By.cssSelector(".requirements li, .qualifications li");
    private By benefitsList = By.cssSelector(".benefits li, .perks li");
    private By backToListButton = By.cssSelector(".back-to-list, .back-button, [data-testid='back']");
    private By similarJobs = By.cssSelector(".similar-jobs .job-card, .related-jobs .job-item");
    
    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(jobTitle) && isElementDisplayed(applyButton);
    }
    
    public String getJobTitle() {
        return getText(jobTitle);
    }
    
    public String getCompanyName() {
        return getText(companyName);
    }
    
    public String getJobLocation() {
        return getText(jobLocation);
    }
    
    public String getJobSalary() {
        return getText(jobSalary);
    }
    
    public String getJobDescription() {
        return getText(jobDescription);
    }
    
    public void clickApply() {
        clickElement(applyButton);
    }
    
    public void clickSaveJob() {
        clickElement(saveJobButton);
    }
    
    public void clickShareJob() {
        clickElement(shareJobButton);
    }
    
    public void clickBackToList() {
        clickElement(backToListButton);
    }
    
    public boolean isJobSaved() {
        // Check if save button shows "Saved" state
        String buttonText = getText(saveJobButton);
        return buttonText.toLowerCase().contains("saved") || buttonText.toLowerCase().contains("bookmarked");
    }
    
    public List<String> getRequirements() {
        List<WebElement> requirements = driver.findElements(requirementsList);
        return requirements.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public List<String> getBenefits() {
        List<WebElement> benefits = driver.findElements(benefitsList);
        return benefits.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public int getSimilarJobsCount() {
        List<WebElement> similarJobsList = driver.findElements(similarJobs);
        return similarJobsList.size();
    }
    
    public void clickSimilarJob(int index) {
        List<WebElement> similarJobsList = driver.findElements(similarJobs);
        if (index < similarJobsList.size()) {
            similarJobsList.get(index).click();
        }
    }
    
    public boolean isApplyButtonEnabled() {
        return isElementDisplayed(applyButton) && ElementUtils.isElementEnabled(driver, applyButton);
    }
    
    public void waitForJobDetailsToLoad() {
        waitForElement(jobTitle);
        waitForElement(jobDescription);
    }

    public boolean isJobDetailsPageLoaded() {
        return isPageLoaded();
    }
    
    public void clickApplyButton() {
        if (isElementDisplayed(applyButton)) {
            clickElement(applyButton);
        }
    }
    
    public void saveJob() {
        if (isElementDisplayed(saveJobButton)) {
            clickElement(saveJobButton);
        }
    }
} 