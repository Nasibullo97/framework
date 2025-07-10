package com.ziprecruiter.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestDataFactory {
    
    // Generate valid test data
    public static String generateValidEmail() {
        return "testuser" + System.currentTimeMillis() + "@example.com";
    }
    
    public static String generateValidPassword() {
        return "TestPassword123!";
    }
    
    public static String generateJobTitle() {
        String[] jobTitles = {
            "Software Engineer", "QA Analyst", "Data Scientist", 
            "Product Manager", "DevOps Engineer", "Frontend Developer"
        };
        return jobTitles[new Random().nextInt(jobTitles.length)];
    }
    
    public static String generateLocation() {
        String[] locations = {
            "New York, NY", "San Francisco, CA", "Austin, TX",
            "Seattle, WA", "Boston, MA", "Remote"
        };
        return locations[new Random().nextInt(locations.length)];
    }
    
    // Generate invalid test data for negative testing
    public static String generateInvalidEmail() {
        String[] invalidEmails = {
            "invalid-email", "@example.com", "test@", 
            "test@.com", "test..test@example.com"
        };
        return invalidEmails[new Random().nextInt(invalidEmails.length)];
    }
    
    public static String generateInvalidPassword() {
        String[] invalidPasswords = {
            "", "123", "password", "PASSWORD", "pass word"
        };
        return invalidPasswords[new Random().nextInt(invalidPasswords.length)];
    }
    
    // Load data from CSV file
    public static List<Map<String, String>> loadDataFromCSV(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();
            if (headers != null) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 0; i < headers.length && i < nextLine.length; i++) {
                        row.put(headers[i], nextLine[i]);
                    }
                    data.add(row);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error loading CSV data: " + e.getMessage());
        }
        return data;
    }
    
    // Create test data object
    public static TestData createTestData() {
        return new TestData(
            generateValidEmail(),
            generateValidPassword(),
            generateJobTitle(),
            generateLocation()
        );
    }
    
    public static TestData createInvalidTestData() {
        return new TestData(
            generateInvalidEmail(),
            generateInvalidPassword(),
            "",
            ""
        );
    }
    
    public static TestData getSearchTestData() {
        return createTestData();
    }
} 