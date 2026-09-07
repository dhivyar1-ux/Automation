package pages.Dashboard.Users.UserGroup;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class UserGroupPage extends BasePage {

    public UserGroupPage() { super(); }

    public void clickCreateUserGroup() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/user-group/new']"))).click();
    }
    
    public boolean isUserGroupAvailable(String userGroupName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='search']")));
    	searchInput.sendKeys(userGroupName);
    	
    	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search']/following-sibling::button")));
    	searchButton.click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noContentCreatorMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noContentCreatorMsg.isEmpty();
    }
    
    public void goToUserGroup(String userGroupName) {
    	
    	if (isUserGroupAvailable(userGroupName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//child::a[contains(text(),'"+userGroupName+"')]"))).click();
    	}
    }
    
    public String getValueByLabel(String label) {
    	if (label.equals("Student List")) {
    		By studentLinks = By.xpath(
				"//b[text()='Student List']"
				+"/parent::div"
				+"/following-sibling::div"
				+"/div/div/a");

    		wait.until(ExpectedConditions.visibilityOfElementLocated(studentLinks));
    		List<String> studentNames = new ArrayList<>();
    		List<WebElement> links = driver.findElements(studentLinks);
    		for (WebElement link : links) {
            	studentNames.add(link.getText().trim());
        	}
    		return String.join(",", studentNames);
    	} else {
    		By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
        	return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	}    	
    }
}


