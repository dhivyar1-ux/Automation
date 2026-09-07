package pages.Dashboard.Users.AppAdmin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class AppAdminDashboardPage extends BasePage {

    public AppAdminDashboardPage() {
        super();
    }

    // Welcome banner username
    By welcomeUser = By.cssSelector(".welcome-content h4 span");

    // Account menu username (under #root)
    By accountMenuUser = By.cssSelector(".account-menu-container span");
    
    public String getWelcomeUsername() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser));
        return driver.findElement(welcomeUser).getText().trim();
    }

    public String getAccountMenuUsername() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuUser));
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
    
    public void goToStudentsPage() {
    	By usersTab = By.xpath("//div[contains(@class,'sidebar')]//span[normalize-space()='Users']");
        By studentsTab = By.cssSelector("a[href='/student']");
        
        wait.until(ExpectedConditions.elementToBeClickable(usersTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(studentsTab)).click();
    }
}

