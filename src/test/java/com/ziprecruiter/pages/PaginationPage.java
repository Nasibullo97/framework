package com.ziprecruiter.pages;

import com.ziprecruiter.base.BasePage;
import com.ziprecruiter.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class PaginationPage extends BasePage {
    
    // Pagination page locators
    private By nextPageButton = By.cssSelector(".next-page, .pagination-next, [data-testid='next']");
    private By previousPageButton = By.cssSelector(".prev-page, .pagination-prev, [data-testid='prev']");
    private By pageNumbers = By.cssSelector(".page-number, .pagination-item, [data-testid='page']");
    private By currentPageIndicator = By.cssSelector(".current-page, .active, [data-testid='current']");
    private By firstPageButton = By.cssSelector(".first-page, .pagination-first, [data-testid='first']");
    private By lastPageButton = By.cssSelector(".last-page, .pagination-last, [data-testid='last']");
    private By pageInfo = By.cssSelector(".page-info, .pagination-info, [data-testid='info']");
    private By itemsPerPageDropdown = By.cssSelector(".items-per-page, select[name='perPage'], #perPage");
    private By totalPages = By.cssSelector(".total-pages, .pagination-total, [data-testid='total']");
    private By loadMoreButton = By.cssSelector(".load-more, .show-more, [data-testid='load-more']");
    private By infiniteScrollTrigger = By.cssSelector(".infinite-scroll, .scroll-trigger");
    
    public PaginationPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(nextPageButton) || isElementDisplayed(loadMoreButton);
    }
    
    public void clickNextPage() {
        if (isNextPageAvailable()) {
            clickElement(nextPageButton);
        }
    }
    
    public void clickPreviousPage() {
        if (isPreviousPageAvailable()) {
            clickElement(previousPageButton);
        }
    }
    
    public void clickFirstPage() {
        if (isElementDisplayed(firstPageButton)) {
            clickElement(firstPageButton);
        }
    }
    
    public void clickLastPage() {
        if (isElementDisplayed(lastPageButton)) {
            clickElement(lastPageButton);
        }
    }
    
    public void goToPage(int pageNumber) {
        List<WebElement> pageNumberElements = driver.findElements(pageNumbers);
        for (WebElement pageElement : pageNumberElements) {
            if (pageElement.getText().equals(String.valueOf(pageNumber))) {
                pageElement.click();
                break;
            }
        }
    }
    
    public int getCurrentPageNumber() {
        if (isElementDisplayed(currentPageIndicator)) {
            String currentPageText = getText(currentPageIndicator);
            try {
                return Integer.parseInt(currentPageText);
            } catch (NumberFormatException e) {
                // Try to extract number from text like "Page 2 of 10"
                String[] parts = currentPageText.split("\\s+");
                for (String part : parts) {
                    if (part.matches("\\d+")) {
                        return Integer.parseInt(part);
                    }
                }
            }
        }
        return 1; // Default to page 1
    }
    
    public int getTotalPages() {
        if (isElementDisplayed(totalPages)) {
            String totalPagesText = getText(totalPages);
            try {
                return Integer.parseInt(totalPagesText);
            } catch (NumberFormatException e) {
                // Try to extract number from text like "Page 2 of 10"
                String[] parts = totalPagesText.split("\\s+");
                for (String part : parts) {
                    if (part.matches("\\d+")) {
                        return Integer.parseInt(part);
                    }
                }
            }
        }
        return 1; // Default to 1 page
    }
    
    public boolean isNextPageAvailable() {
        return isElementDisplayed(nextPageButton) && ElementUtils.isElementEnabled(driver, nextPageButton);
    }
    
    public boolean isPreviousPageAvailable() {
        return isElementDisplayed(previousPageButton) && ElementUtils.isElementEnabled(driver, previousPageButton);
    }
    
    public void setItemsPerPage(int itemsPerPage) {
        if (isElementDisplayed(itemsPerPageDropdown)) {
            clickElement(itemsPerPageDropdown);
            By option = By.xpath("//option[@value='" + itemsPerPage + "']");
            clickElement(option);
        }
    }
    
    public void clickLoadMore() {
        if (isElementDisplayed(loadMoreButton)) {
            clickElement(loadMoreButton);
        }
    }
    
    public void scrollToBottom() {
        // Scroll to trigger infinite scroll if available
        if (isElementDisplayed(infiniteScrollTrigger)) {
            // JavaScript scroll to bottom
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }
    
    public String getPageInfo() {
        return getText(pageInfo);
    }
    
    public List<String> getAvailablePageNumbers() {
        List<WebElement> pageNumberElements = driver.findElements(pageNumbers);
        return pageNumberElements.stream()
                .map(element -> element.getText())
                .toList();
    }
    
    public boolean isPageNumberVisible(int pageNumber) {
        List<String> availablePages = getAvailablePageNumbers();
        return availablePages.contains(String.valueOf(pageNumber));
    }
    
    public void navigateToPage(int targetPage) {
        int currentPage = getCurrentPageNumber();
        int totalPages = getTotalPages();
        
        if (targetPage < 1 || targetPage > totalPages) {
            throw new IllegalArgumentException("Page number out of range: " + targetPage);
        }
        
        if (targetPage == 1) {
            clickFirstPage();
        } else if (targetPage == totalPages) {
            clickLastPage();
        } else if (targetPage < currentPage) {
            // Navigate backwards
            while (getCurrentPageNumber() > targetPage && isPreviousPageAvailable()) {
                clickPreviousPage();
            }
        } else if (targetPage > currentPage) {
            // Navigate forwards
            while (getCurrentPageNumber() < targetPage && isNextPageAvailable()) {
                clickNextPage();
            }
        }
        
        // Final check - go directly to page if available
        if (getCurrentPageNumber() != targetPage && isPageNumberVisible(targetPage)) {
            goToPage(targetPage);
        }
    }
    
    public void waitForPageToLoad() {
        // Wait for page content to load after navigation
        try {
            Thread.sleep(1000); // Give time for page to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public boolean hasPagination() {
        return isElementDisplayed(nextPageButton) || isElementDisplayed(loadMoreButton);
    }
    
    public boolean isInfiniteScroll() {
        return isElementDisplayed(infiniteScrollTrigger);
    }
    
    public boolean hasNextPage() {
        return isNextPageAvailable();
    }
    
    public void navigateToNextPage() {
        clickNextPage();
    }
    
    public boolean isPaginationPageLoaded() {
        return isPageLoaded();
    }
} 