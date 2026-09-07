package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
	public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
        	
        	// 1. Create ChromeOptions object
            ChromeOptions options = new ChromeOptions();
            
            // 2. Add the --incognito switch
            options.addArguments("--incognito");
            
            // 3. Pass options to the ChromeDriver
            driver = new ChromeDriver(options);

            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return driver;
    }
}
