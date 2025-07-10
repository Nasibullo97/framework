package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class NotificationsPage extends BasePage {
    
    // Notifications page locators
    private By notificationsList = By.cssSelector(".notification, .alert, [data-testid='notification']");
    private By notificationTitle = By.cssSelector(".notification-title, .alert-title, h4");
    private By notificationMessage = By.cssSelector(".notification-message, .alert-message, p");
    private By notificationTime = By.cssSelector(".notification-time, .timestamp, .time");
    private By markAsReadButton = By.cssSelector(".mark-read, .read-button, [data-testid='mark-read']");
    private By deleteButton = By.cssSelector(".delete-notification, .remove, [data-testid='delete']");
    private By markAllReadButton = By.cssSelector(".mark-all-read, .read-all, [data-testid='read-all']");
    private By clearAllButton = By.cssSelector(".clear-all, .delete-all, [data-testid='clear-all']");
    private By unreadIndicator = By.cssSelector(".unread, .unread-indicator, .new");
    private By emptyState = By.cssSelector(".empty-notifications, .no-notifications, [data-testid='empty']");
    private By filterDropdown = By.cssSelector(".notification-filter, select[name='filter'], #filter");
    private By searchNotifications = By.cssSelector("input[placeholder*='search'], .search-notifications, #search");
    private By notificationCount = By.cssSelector(".notification-count, .count, [data-testid='count']");
    private By settingsButton = By.cssSelector(".notification-settings, .settings, [data-testid='settings']");
    private By createAlertButton = By.cssSelector(".create-alert, .new-alert, [data-testid='create-alert']");
    private By alertJobTitleField = By.cssSelector("input[name='jobTitle'], #jobTitle, .alert-job-title");
    private By alertLocationField = By.cssSelector("input[name='location'], #location, .alert-location");
    private By saveAlertButton = By.cssSelector(".save-alert, button[type='submit'], [data-testid='save-alert']");
    private By successMessage = By.cssSelector(".success-message, .alert-success, [data-testid='success']");
    
    public NotificationsPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(notificationsList) || isElementDisplayed(emptyState);
    }
    
    public int getNotificationsCount() {
        List<WebElement> notifications = driver.findElements(notificationsList);
        return notifications.size();
    }
    
    public boolean hasNotifications() {
        return getNotificationsCount() > 0;
    }
    
    public boolean isEmpty() {
        return isElementDisplayed(emptyState);
    }
    
    public void clickNotification(int index) {
        List<WebElement> notifications = driver.findElements(notificationsList);
        if (index < notifications.size()) {
            notifications.get(index).click();
        }
    }
    
    public void markAsRead(int index) {
        List<WebElement> markButtons = driver.findElements(markAsReadButton);
        if (index < markButtons.size()) {
            markButtons.get(index).click();
        }
    }
    
    public void deleteNotification(int index) {
        List<WebElement> deleteButtons = driver.findElements(deleteButton);
        if (index < deleteButtons.size()) {
            deleteButtons.get(index).click();
        }
    }
    
    public void markAllAsRead() {
        clickElement(markAllReadButton);
    }
    
    public void clearAllNotifications() {
        clickElement(clearAllButton);
    }
    
    public String getNotificationTitle(int index) {
        List<WebElement> titles = driver.findElements(notificationTitle);
        if (index < titles.size()) {
            return titles.get(index).getText();
        }
        return "";
    }
    
    public String getNotificationMessage(int index) {
        List<WebElement> messages = driver.findElements(notificationMessage);
        if (index < messages.size()) {
            return messages.get(index).getText();
        }
        return "";
    }
    
    public String getNotificationTime(int index) {
        List<WebElement> times = driver.findElements(notificationTime);
        if (index < times.size()) {
            return times.get(index).getText();
        }
        return "";
    }
    
    public boolean isUnread(int index) {
        List<WebElement> notifications = driver.findElements(notificationsList);
        if (index < notifications.size()) {
            WebElement notification = notifications.get(index);
            return notification.findElements(unreadIndicator).size() > 0;
        }
        return false;
    }
    
    public int getUnreadCount() {
        List<WebElement> unreadNotifications = driver.findElements(unreadIndicator);
        return unreadNotifications.size();
    }
    
    public void filterBy(String filterOption) {
        if (isElementDisplayed(filterDropdown)) {
            clickElement(filterDropdown);
            By option = By.xpath("//option[contains(text(),'" + filterOption + "')]");
            clickElement(option);
        }
    }
    
    public void searchNotifications(String searchTerm) {
        typeText(searchNotifications, searchTerm);
    }
    
    public void clickSettings() {
        clickElement(settingsButton);
    }
    
    public String getNotificationCountText() {
        return getText(notificationCount);
    }
    
    public List<String> getAllNotificationTitles() {
        List<WebElement> titles = driver.findElements(notificationTitle);
        return titles.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public List<String> getAllNotificationMessages() {
        List<WebElement> messages = driver.findElements(notificationMessage);
        return messages.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public void waitForNotificationsToLoad() {
        try {
            waitForElement(notificationsList);
        } catch (Exception e) {
            // If no notifications, wait for empty state
            waitForElement(emptyState);
        }
    }
    
    public void createJobAlert(String jobTitle, String location, String frequency) {
        if (isElementDisplayed(createAlertButton)) {
            clickElement(createAlertButton);
            typeText(alertJobTitleField, jobTitle);
            typeText(alertLocationField, location);
            selectAlertFrequency(frequency);
            clickElement(saveAlertButton);
        }
    }
    
    public void selectAlertFrequency(String frequency) {
        By frequencyDropdown = By.cssSelector("select[name='frequency'], #frequency, .alert-frequency");
        if (isElementDisplayed(frequencyDropdown)) {
            clickElement(frequencyDropdown);
            By option = By.xpath("//option[contains(text(),'" + frequency + "')]");
            clickElement(option);
        }
    }
    
    public boolean isAlertCreated() {
        return isElementDisplayed(successMessage);
    }
    
    public boolean isNotificationsPageLoaded() {
        return isPageLoaded();
    }
    
    public boolean isNotificationPresent(String title) {
        List<String> titles = getAllNotificationTitles();
        return titles.stream().anyMatch(t -> t.contains(title));
    }
    
    public int getNotificationIndexByTitle(String title) {
        List<String> titles = getAllNotificationTitles();
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).contains(title)) {
                return i;
            }
        }
        return -1;
    }
    
    public void markNotificationsAsReadByTitle(String title) {
        int index = getNotificationIndexByTitle(title);
        if (index >= 0) {
            markAsRead(index);
        }
    }
} 