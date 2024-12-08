package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */

    @Test
    public void testCase01() throws InterruptedException {
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);

        // Close login popup
        Wrappers.closeLoginPopup(driver);

        // Search for "Washing Machine"
        Wrappers.search(driver, "Washing Machine");

        // Sort by popularity
        Wrappers.sortBy(driver, "Popularity");

        // Get the count of items with rating <= 4 stars
        List<WebElement> ratings = Wrappers.getRatings(driver, 4);
        System.out.println("Count of items with rating <= 4 stars: " + ratings.size()); 
        Wrappers.getRatings(driver, 4);

        Wrappers.printProductDetailsForLowRatings(driver, 4);

        // for (WebElement rating : ratings) {
        //     String productName = rating.findElement(By.xpath("//span[@class='Y1HWO0']")).getText();
        //     System.out.println("Product: " + productName + ", Rating: " + rating.getText());
        // }

    }

   @Test
    public void testCase02() throws InterruptedException {
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);

        // Close login popup
        Wrappers.closeLoginPopup(driver);

        // Search for "iPhone"
        Wrappers.search(driver, "iPhone");

        // Get titles and discount % for items with more than 17% discount
        System.out.println("Fetching iphone discounts > 17");

        // Get titles and discount % for items with more than 17% discount
        Wrappers.printItemsWithDiscount(driver, 17);
    }

    @Test
    public void testCase03() throws InterruptedException {
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);

        // Close login popup
        Wrappers.closeLoginPopup(driver);

        // Search for "Coffee Mug"  
        Wrappers.search(driver, "Coffee Mug");

        // Filter by 4 stars and above
        Wrappers.filterByRating(driver, 4);

        Wrappers.printReviewCounts(driver);

        // Print the titles and image URLs of the top 5 items with highest reviews
        Wrappers.printTop5ItemsWithMostReviews(driver);
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}