package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ApplicationFlowPage extends BasePage {
    
    // Application flow locators
    private By resumeDropdown = By.cssSelector(".resume-select, select[name='resume'], #resume");
    private By coverLetterField = By.cssSelector("textarea[name='coverLetter'], #coverLetter, .cover-letter");
    private By additionalQuestions = By.cssSelector(".question, .application-question");
    private By questionInputs = By.cssSelector(".question input, .question textarea");
    private By submitButton = By.cssSelector("button[type='submit'], .submit-application, [data-testid='submit']");
    private By cancelButton = By.cssSelector(".cancel-application, .back-button, [data-testid='cancel']");
    private By progressIndicator = By.cssSelector(".progress-bar, .application-progress");
    private By successMessage = By.cssSelector(".success-message, .application-success, .alert-success");
    private By errorMessage = By.cssSelector(".error-message, .application-error, .alert-danger");
    private By uploadResumeButton = By.cssSelector(".upload-resume, input[type='file'], #uploadResume");
    private By reviewApplicationButton = By.cssSelector(".review-application, [data-testid='review']");
    private By firstNameField = By.cssSelector("input[name='firstName'], #firstName, .first-name");
    private By lastNameField = By.cssSelector("input[name='lastName'], #lastName, .last-name");
    private By emailField = By.cssSelector("input[name='email'], #email, .email");
    private By phoneField = By.cssSelector("input[name='phone'], #phone, .phone");
    private By resumeUpload = By.cssSelector("input[type='file'], #resumeUpload, .resume-upload");
    
    public ApplicationFlowPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(submitButton) || isElementDisplayed(resumeDropdown);
    }
    
    public boolean isApplicationPageLoaded() {
        return isPageLoaded();
    }
    
    public void fillApplicationForm(String firstName, String lastName, String email, String phone, String resumePath) {
        typeText(firstNameField, firstName);
        typeText(lastNameField, lastName);
        typeText(emailField, email);
        typeText(phoneField, phone);
        
        if (resumePath != null && !resumePath.isEmpty()) {
            WebElement resumeInput = waitForElement(resumeUpload);
            resumeInput.sendKeys(resumePath);
        }
    }
    
    public void submitApplication() {
        clickElement(submitButton);
    }
    
    public void selectResume(String resumeName) {
        // Select from dropdown or click to upload
        if (isElementDisplayed(resumeDropdown)) {
            clickElement(resumeDropdown);
            By resumeOption = By.xpath("//option[contains(text(),'" + resumeName + "')]");
            clickElement(resumeOption);
        }
    }
    
    public void uploadNewResume(String filePath) {
        if (isElementDisplayed(uploadResumeButton)) {
            WebElement fileInput = waitForElement(uploadResumeButton);
            fileInput.sendKeys(filePath);
        }
    }
    
    public void enterCoverLetter(String coverLetter) {
        typeText(coverLetterField, coverLetter);
    }
    
    public void answerQuestion(int questionIndex, String answer) {
        List<WebElement> questions = driver.findElements(additionalQuestions);
        if (questionIndex < questions.size()) {
            WebElement question = questions.get(questionIndex);
            WebElement input = question.findElement(By.cssSelector("input, textarea"));
            input.clear();
            input.sendKeys(answer);
        }
    }
    
    public void answerAllQuestions(String[] answers) {
        List<WebElement> questionInputs = driver.findElements(this.questionInputs);
        for (int i = 0; i < Math.min(answers.length, questionInputs.size()); i++) {
            WebElement input = questionInputs.get(i);
            input.clear();
            input.sendKeys(answers[i]);
        }
    }
    
    public void clickSubmit() {
        clickElement(submitButton);
    }
    
    public void clickCancel() {
        clickElement(cancelButton);
    }
    
    public void clickReview() {
        clickElement(reviewApplicationButton);
    }
    
    public boolean isApplicationSubmitted() {
        return isElementDisplayed(successMessage);
    }
    
    public boolean hasErrors() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public int getProgressPercentage() {
        if (isElementDisplayed(progressIndicator)) {
            String progressText = getText(progressIndicator);
            // Extract percentage from text like "50%" or "Step 2 of 4"
            if (progressText.contains("%")) {
                return Integer.parseInt(progressText.replaceAll("[^0-9]", ""));
            }
        }
        return 0;
    }
    
    public int getQuestionCount() {
        List<WebElement> questions = driver.findElements(additionalQuestions);
        return questions.size();
    }
    
    public void completeApplication(String resumeName, String coverLetter, String[] answers) {
        selectResume(resumeName);
        enterCoverLetter(coverLetter);
        answerAllQuestions(answers);
        clickSubmit();
    }
    
    public void waitForApplicationToProcess() {
        // Wait for either success or error message
        try {
            waitForElement(successMessage);
        } catch (Exception e) {
            waitForElement(errorMessage);
        }
    }
} 