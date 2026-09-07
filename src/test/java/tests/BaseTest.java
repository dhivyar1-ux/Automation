package tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    @BeforeSuite
    @Parameters({"url"})
    public void setUp(@Optional("http://localhost:9000/") String url) {
    	
    	DriverManager.initialize();

        DriverManager.getDriver().get(url);
    }

    @AfterSuite
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
        }
    }
}
