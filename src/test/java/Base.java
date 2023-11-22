import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Base {
        private WebDriver driver;
        private List<String> salaryMedian = new ArrayList<>();

        @BeforeClass
        public void setUp() {
            /** Setup ChromeDriver using WebDriverManager
              * WebDriverManager for managing the WebDriver binaries
              * No need to download every time the Chrome browser is updated.*/

            WebDriverManager.chromedriver().setup();

            // Configure Chrome options if needed
            ChromeOptions options = new ChromeOptions();
            // for example: options.addArguments("--headless");

            // Initialize ChromeDriver
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }

        @Test
        public void testSalaryMedian() {
            // Navigate to the website
            driver.get("https://dou.ua/");

            // Click on the "Зарплаты" tab
            driver.findElement(By.xpath("//a[contains(@href, 'jobs.dou.ua/salaries/')]")).click();

            // Select JuniorQA, MiddleQA, and SeniorQA
            waitForElement(driver, By.xpath("//div[@id='dd-position']")).click();
            selectPosition("Junior QA");
            waitABit(1);
            printMedianSalary("Junior QA");
            driver.findElement(By.xpath("//div[@id='dd-position']")).click();
            selectPosition("Middle QA");
            waitABit(1);
            printMedianSalary("Middle QA");
            driver.findElement(By.xpath("//div[@id='dd-position']")).click();
            selectPosition("Senior QA");
            waitABit(1);
            printMedianSalary("Senior QA");

            // Calculate and print the overall median salary
            double overallMedian = calculateOverallMedian(salaryMedian);
            System.out.println("Overall Median Salary: " + overallMedian);

            // Assert that the overall median is greater than 0
            Assert.assertTrue(overallMedian > 0, "Overall median should be greater than 0");
        }

        private void selectPosition(String position) {
            waitForElement(driver, By.xpath("//div[text()='" + position + "']")).click();
        }

        private void printMedianSalary(String position) {
            String value = waitForElement(driver, By.xpath("//div[@id='median']//span[@class='bc-num-value']")).getText();
            System.out.println(position + " Median Salary: " + value);
            salaryMedian.add(value);
        }

        private double calculateOverallMedian(List<String> list) {
            double medianAverage = list.stream()
                    .mapToDouble( s -> Double.parseDouble( s ) )
                    .average()
                    .orElse(Double.NaN);
            return medianAverage;
        }
    private static WebElement waitForElement(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until((ExpectedCondition<WebElement>) webDriver ->
                webDriver.findElement(by));
    }

    public void waitABit(Integer delayInSeconds) {
        try {
            Thread.sleep(delayInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        @AfterClass
        public void tearDown() {
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        }
}
