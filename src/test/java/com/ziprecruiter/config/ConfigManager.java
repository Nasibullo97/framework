package com.ziprecruiter.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://www.ziprecruiter.com");
    }

    public static String getLoginUrl() {
        return properties.getProperty("login.url", "https://www.ziprecruiter.com/login");
    }

    public static String getSearchUrl() {
        return properties.getProperty("search.url", "https://www.ziprecruiter.com");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "20"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }

    public static String getDefaultBrowser() {
        return properties.getProperty("default.browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static String getTestEmail() {
        return properties.getProperty("test.email", "test@example.com");
    }

    public static String getTestPassword() {
        return properties.getProperty("test.password", "testpassword");
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }

    public static String getScreenshotDirectory() {
        return properties.getProperty("screenshot.directory", "screenshots");
    }
    
    public static void loadConfig(String configFile) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
} 