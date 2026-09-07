package pages.Dashboard.Users.ContentCreator;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class ContentCreatorDashboardPage extends BasePage {

	    public ContentCreatorDashboardPage() { super(); }

	    // Welcome banner username
	    By welcomeUser = By.cssSelector(".welcome-content h4 span");

	    // Account menu username (under #root)
	    By accountMenuUser = By.cssSelector(".account-menu-container span");
	    
	    public String getWelcomeUsername() {
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser));
	    	
	    	return driver.findElement(welcomeUser).getText();
	    }

	    public String getAccountMenuUsername() {
	        return driver.findElement(accountMenuUser).getText().trim();
	    }

	    public void logout() {

	        // Click account menu
	        driver.findElement(accountMenuUser).click();

	        // Logout option (adjust text if needed)
	        By logoutBtn = By.xpath("//a[@href='/logout']");
	        
	        // wait and click logout
	        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
	    }    	    
}
