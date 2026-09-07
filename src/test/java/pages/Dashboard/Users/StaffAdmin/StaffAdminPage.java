package pages.Dashboard.Users.StaffAdmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class StaffAdminPage extends BasePage {
    public StaffAdminPage() { super(); }

    public void clickCreateStaffAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/staff-admin/new']"))).click();
    }
    
    public boolean isStaffAdminNameAvailable(String staffAdminName) {
    	WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class,'search-input')][1]")));
    	searchInput.sendKeys(staffAdminName);

    	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class,'search-input')][1]//following::button")));
    	searchButton.click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noStaffAdminMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noStaffAdminMsg.isEmpty();
    }
    
    public void goToStaffAdmin(String staffAdminName) {
    	
    	if (isStaffAdminNameAvailable(staffAdminName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//child::a[contains(text(),'"+staffAdminName+"')]"))).click();
        	wait3Seconds();
		}
    }
    
    public String getValueByLabel(String label) {
    	By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    }
}


