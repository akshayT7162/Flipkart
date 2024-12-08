package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void closeLoginPopup(ChromeDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement closeButton = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
        } catch (Exception e) {
            System.out.println("Login popup did not appear.");
        }
    }

    public static void search(ChromeDriver driver, String query) {
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    public static void sortBy(ChromeDriver driver, String sortOption) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement sortDropdown = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Sort By']")));
        sortDropdown.click();

        WebElement sortByOption = driver.findElement(By.xpath("//div[contains(text(),'" + sortOption + "')]"));
        sortByOption.click();
        Thread.sleep(2000);
    }

    public static List<WebElement> getRatings(ChromeDriver driver, int maxRating) {
       // return driver.findElements(By.xpath("//div[@class='XQDdHH' and number(text()) <= " + maxRating + "]"));

        List<WebElement> ratings = driver.findElements(By.xpath("//div[@class='XQDdHH']"));
        List<WebElement> lowRatedItems = new ArrayList<>();

        for (WebElement ratingElement : ratings) {
            try {
                String ratingText = ratingElement.getText();
                double ratingValue = Double.parseDouble(ratingText);
                if (ratingValue <= maxRating) {
                    lowRatedItems.add(ratingElement);
                }
            } catch (Exception e) {
                // Handle case where rating is not available or is malformed
            }
        }
        return lowRatedItems;

    }

    public static void printProductDetailsForLowRatings(ChromeDriver driver, int maxRating) {
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='_1AtVbE']"));
        
        for (WebElement item : items) {
            try {
                WebElement ratingElement = item.findElement(By.xpath(".//div[@class='XQDdHH']"));
                String ratingText = ratingElement.getText();
                double ratingValue = Double.parseDouble(ratingText);
                
                if (ratingValue <= maxRating) {
                    String productTitle = item.findElement(By.xpath(".//a[contains(@class,'IRpwTa')]")).getText();
                    System.out.println("Product: " + productTitle + ", Rating: " + ratingText);
                }
            } catch (Exception e) {
                // Skip elements that don't have a rating
            }
        }
    }

    public static void printItemsWithDiscount(ChromeDriver driver, int minDiscount) {

        List<WebElement> items = driver.findElements(By.xpath("//div[@class='UkUFwK']"));

        for (WebElement item : items) {
            try {
                String discountText = item.findElement(By.xpath("//div[@class='UkUFwK']")).getText();
                String discount = discountText.replaceAll("[^0-9]", "");
                if (Integer.parseInt(discount) > minDiscount) {
                    String title = item.findElement(By.xpath(".//a[contains(@class,'IRpwTa']")).getText();
                    System.out.println("Title: " + title + ", Discount: " + discount + "%");
                }
            } catch (Exception e) {
                // Skip elements without discount or title
            }
        }
    }

    public static void filterByRating(ChromeDriver driver, int minRating) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement ratingFilter = wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[text()='Customer Ratings']//parent::div//parent::section")));

        WebElement minRatingOption = ratingFilter
                .findElement(By.xpath(
                        ".//div[@class='ewzVkT _3DvUAf' and contains(@title,'& above')]"));
        minRatingOption.click();
        Thread.sleep(2000);
    }

    public static void printReviewCounts(ChromeDriver driver) {
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='_1AtVbE']"));

        for (WebElement item : items) {
            try {
                String title = item.findElement(By.xpath(".//a[contains(@class,'IRpwTa')]")).getText();
                String reviewCount = item.findElement(By.xpath(".//span[@class='Wphh3N']")).getText();
                System.out.println("Title: " + title + ", Review Count: " + reviewCount);
            } catch (Exception e) {
                // Skip elements without title or review count
            }
        }
    }

    public static void printTop5ItemsWithMostReviews(ChromeDriver driver) {
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='_1AtVbE']"));
        int count = 0;
        for (WebElement item : items) {
            try {
                String title = item.findElement(By.xpath(".//a[@class='IRpwTa']")).getText();
                String imageUrl = item.findElement(By.xpath(".//img[@class='_396cs4']")).getAttribute("src");
                String reviews = item.findElement(By.xpath(".//span[@class='_2_R_DZ']")).getText();
                System.out.println("Title: " + title + ", Image URL: " + imageUrl + ", Review: " + reviews);
                count++;
                if (count == 5)
                    break;
            } catch (Exception e) {
                // Skip elements without title or image
            }
        }
    }
}
