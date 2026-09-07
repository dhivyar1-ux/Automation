package pages.Dashboard.Games;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class GamesPage extends BasePage {
    public GamesPage() { super();}

    public void clickCreateGamesList() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/games-list/new']"))).click();
    }
    
    public boolean isGamesListAvailable(String gamesListName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(gamesListName);
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='search'][1]//following::button)[1]"))).click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noGamesListMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noGamesListMsg.isEmpty();
    }
    
    public void goToGamesList(String gamesListName) {
    	
    	if (isGamesListAvailable(gamesListName)) {
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//a[contains(text(),'"+gamesListName+"')]//following::td[@class='text-end']//child::a[1]"))).click();
    	}
    }
    
    public void enrollToGamesList(String gamesListName,String studentName,String facultyName) {
    	
    	if (isGamesListAvailable(gamesListName)) {
    		//click enroll button
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//a[contains(text(),'"+gamesListName+"')]//following::td[@class='text-end']//child::a[contains(text(),'Enroll')]"))).click();
    	
        	//select student radio button
        	By s_category = By.xpath("//input[@type='radio' and @name='studentOruserGroup' and @value='student']");
        	wait.until(ExpectedConditions.elementToBeClickable(s_category)).click();
        	
        	//search the student
        	WebElement searchStudent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='searchStudent'])[1]")));
            
        	searchStudent.sendKeys("*");
        	searchStudent.sendKeys(Keys.ENTER);
            
        	 WebElement optionList = wait.until(
                     ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='menu']")));
          	
          	for (int index = 0; index < 20; index++) {
     			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", optionList);
     			//Thread.sleep(500);
     		}
          	
            //select the student
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+studentName+"')]"))).click(); 
    	
            //search the faculty
        	WebElement searchFaculty = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='searchFaculty'])[1]")));
            
        	searchFaculty.sendKeys("*");
        	searchFaculty.sendKeys(Keys.ENTER);
            
            //select the student
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+facultyName+"')]"))).click();
            
            By submitBtn = By.xpath("//button[contains(normalize-space(),'Confirm')]");
            By backBtn = By.id("cancel-save");
            
            wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
            driver.findElement(backBtn).click();
        }
    }
    
    public String getValueByLabel(String label) {
    	if (label.equals("Interaction Templates") || label.equals("Practice Task")) {
    		By content = By.xpath("(//b[text()='"+ label +"']/parent::div/following-sibling::div)[1]//child::a");
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	} else {
    		By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	}
    }
    
}


