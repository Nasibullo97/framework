package com.ziprecruiter.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.qameta.allure.Attachment;

@Listeners(com.ziprecruiter.base.BaseTest.ScreenshotListener.class)
public class BaseTest {
    protected WebDriver driver;

    protected void closePopupsIfPresent() {
        try {
            // Example selectors for popups, modals, overlays, and cookie banners
            String[] selectors = {
                ".ScreenModalOverlay button, .ScreenModalOverlay [aria-label='close'], .ScreenModalOverlay .close",
                ".modal button.close, .modal [aria-label='close']",
                "button[aria-label='close']",
                ".cookie, .cookie-consent, .cookie-banner button, .cookie-banner [aria-label='close']",
                "#gdpr-banner, #gdpr-banner button, #gdpr-banner [aria-label='close']"
            };
            for (String selector : selectors) {
                java.util.List<org.openqa.selenium.WebElement> closeButtons = driver.findElements(org.openqa.selenium.By.cssSelector(selector));
                for (org.openqa.selenium.WebElement btn : closeButtons) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        btn.click();
                        Thread.sleep(500); // Give time for modal to close
                    }
                }
            }
        } catch (Exception ignored) {
            // Ignore if popup is not present or not interactable
        }
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser, org.testng.ITestContext context) {
        if (browser == null || browser.isEmpty()) {
            if (context != null) {
                browser = context.getCurrentXmlTest().getParameter("browser");
            }
        }
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser", "chrome"); // default
        }
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        closePopupsIfPresent();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static class ScreenshotListener implements ITestListener {
        @Override
        public void onTestFailure(ITestResult result) {
            Object currentClass = result.getInstance();
            if (currentClass instanceof BaseTest) {
                WebDriver driver = ((BaseTest) currentClass).driver;
                if (driver != null) {
                    try {
                        File screenshotsDir = new File("screenshots");
                        if (!screenshotsDir.exists()) {
                            screenshotsDir.mkdirs();
                        }
                        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String methodName = result.getMethod().getMethodName();
                        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        String filePath = "screenshots/" + methodName + "_" + timestamp + ".png";
                        Files.copy(srcFile.toPath(), Paths.get(filePath));
                        System.out.println("[Screenshot saved]: " + filePath);
                        // Attach to Allure
                        attachScreenshotToAllure(Files.readAllBytes(srcFile.toPath()));
                    } catch (IOException e) {
                        System.out.println("[Screenshot failed]: " + e.getMessage());
                    }
                }
            }
        }
        @Attachment(value = "Failure Screenshot", type = "image/png")
        public byte[] attachScreenshotToAllure(byte[] screenshot) {
            return screenshot;
        }
        // Other ITestListener methods can be left empty
    }
} 