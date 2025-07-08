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

@Listeners(com.ziprecruiter.base.BaseTest.ScreenshotListener.class)
public class BaseTest {
    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(org.testng.ITestContext context) {
        String browser = System.getProperty("browser");
        if (browser == null) {
            browser = context.getCurrentXmlTest().getParameter("browser");
        }
        if (browser == null) {
            browser = "chrome"; // default
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
                    } catch (IOException e) {
                        System.out.println("[Screenshot failed]: " + e.getMessage());
                    }
                }
            }
        }
        // Other ITestListener methods can be left empty
    }
} 