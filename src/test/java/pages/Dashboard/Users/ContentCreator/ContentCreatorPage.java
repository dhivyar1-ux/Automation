package pages.Dashboard.Users.ContentCreator;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class ContentCreatorPage extends BasePage{

    public ContentCreatorPage() { super(); }

    public void clickCreateContentCreator() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/content-creator/new']"))).click();
    }
    
    public boolean isContentCreatorAvailable(String contentCreatorName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
			By.xpath("//input[contains(@class,'search-input')][1]")));
    	searchInput.sendKeys(contentCreatorName);
    	
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//input[contains(@class,'search-input')][1]//following::button")));
    	searchButton.click();

    	//workaround when backbutton is clicked
    	//WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class,'search-input')][1]//following::button")));
    	//searchButton.click();
    	
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
    
    public void goToContentCreator(String contentCreatorName) {
    	
    	if (isContentCreatorAvailable(contentCreatorName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td[contains(text(),'"+contentCreatorName+"')]//following::td[@class='text-end']//child::a[1]"))).click();
    	}
    }
    
    public String getValueByLabel(String label) {
    	By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    }
}


