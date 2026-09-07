package pages.Dashboard.Users.AppAdmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class AppAdminPage extends BasePage{

    public AppAdminPage() { super(); }

    public void clickCreateAppAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/app-admin/new']"))).click();
    }
    
    public int isAppAdminAvailable(String appAdminName) {
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")));
    	
    	List<WebElement> nameCells = driver.findElements(By.xpath("//tr[@data-cy='entityTable']/td[@data-label='Name']"));

    	int index = -1;
    	for (int i=0;i < nameCells.size(); i++) {
    	    String actualName = nameCells.get(i).getText().trim();
    	    System.out.println("Found in table: " + actualName);

    	    if (actualName.equalsIgnoreCase(appAdminName)) {
    	        System.out.println("Success: Match found for " + appAdminName);
    	        index = i;
    	        break;
    	    }
    	}
    	
    	return index;    	
    }
    
    public void goToAppAdmin(String appAdminName) {
    	
    	if (isAppAdminAvailable(appAdminName) > -1) {
        	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.linkText(appAdminName)))).click();
			wait3Seconds();
		}
    }
    
    public String getValueByLabel(String label) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span")
        )).getText();
    }
}

