package com.ziprecruiter.utils;

import org.openqa.selenium.*;
import java.util.List;

public class PopupUtils {
    public static void closeAllPopups(WebDriver driver) {
        try {
            String[] selectors = {
                ".ScreenModalOverlay button, .ScreenModalOverlay [aria-label='close'], .ScreenModalOverlay .close",
                ".modal button.close, .modal [aria-label='close']",
                "button[aria-label='close']",
                ".cookie, .cookie-consent, .cookie-banner button, .cookie-banner [aria-label='close']",
                "#gdpr-banner, #gdpr-banner button, #gdpr-banner [aria-label='close']"
            };
            for (String selector : selectors) {
                List<WebElement> closeButtons = driver.findElements(By.cssSelector(selector));
                for (WebElement btn : closeButtons) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        btn.click();
                        Thread.sleep(500);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
} 