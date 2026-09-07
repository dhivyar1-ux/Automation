package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.DriverManager;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {

        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
    }
    
    protected void wait3Seconds() {
        long start = System.currentTimeMillis();
        new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(d -> System.currentTimeMillis() - start >= 3000L);
    }
}
