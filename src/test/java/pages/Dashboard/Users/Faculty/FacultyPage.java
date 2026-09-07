package pages.Dashboard.Users.Faculty;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class FacultyPage extends BasePage {
    public FacultyPage() { super(); }

    public void clickCreateFaculty() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/faculty/new']"))).click();
    }
    
    public boolean isFacultyAvailable(String facultyName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(facultyName);

    	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]//following::button")));
    	searchButton.click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noFacultyMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noFacultyMsg.isEmpty();
    }
    
    public void goToFaculty(String facultyName) {
    	
    	if (isFacultyAvailable(facultyName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//child::a[contains(text(),'"+facultyName+"')]"))).click();
        	wait3Seconds();
    	}
    }
    
    public String getValueByLabel(String label) {
    	By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    }
}


