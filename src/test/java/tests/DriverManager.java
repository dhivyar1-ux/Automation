package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverFactory;

public class DriverManager {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void initialize() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}

