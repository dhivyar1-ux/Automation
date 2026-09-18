package pages.Dashboard.Assessment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class InteractionTemplatePage extends BasePage {
    public InteractionTemplatePage() { super(); }

	public void clickCreateInteractionTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/interaction-template/new']"))).click();
    }
    
    public boolean isInteractionTemplateAvailable(String interactionTemplateName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(interactionTemplateName);
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='search'][1]//following::button)[1]"))).click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noInteractionTemplateMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noInteractionTemplateMsg.isEmpty();
    }
    
	public void viewSpecificInteractionTemplate(String interactionTemplateName) {
    	
    	if (isInteractionTemplateAvailable(interactionTemplateName)) {
    		//collect the questions,answers,correct choice
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//div[@class='table-responsive']//tbody//tr//td//a[contains(text(),'"+interactionTemplateName+"')]//following::td[@class='text-end'])[1]//child::a//child::span[text()='View']"))).click();
		}
	}    
}


