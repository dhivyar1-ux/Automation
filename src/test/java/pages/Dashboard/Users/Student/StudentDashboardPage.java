package pages.Dashboard.Users.Student;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class StudentDashboardPage extends BasePage {

	    public StudentDashboardPage() { super(); }

	    // Welcome banner username
	    By welcomeUser = By.cssSelector("#home-heading span");

	    // Account menu username (under #root)
	    By accountMenuUser = By.cssSelector(".account-menu-container span");
	    
	    public String getWelcomeUsername() {
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser));
	    	
	    	String username = driver.findElement(welcomeUser).getText();
	        return username.substring(0, username.length()-1).trim();
	    }

	    public String getAccountMenuUsername() {
			wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuUser));
	        return driver.findElement(accountMenuUser).getText().trim();
	    }

		public List<String> playSpecificGameList(String gameListName,String gameName) {
	    	
	    	//Games under Dashboard
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()='Games'])[1]"))).click();
	        
	        //My Games link
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@href='/online-games-enrollment'])[1]"))).click();
	        
	        //Go to Games of specific games list
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='table-responsive']"
	        		+ "//child::table//child::tbody//child::tr//child::td[text()='"+gameListName+"'])[1]"
	        		+ "//following-sibling::td[@class='text-end']//child::a//child::span[text()='Go to Games']"))).click();
	        
	        //select specific game
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'Games')]"
	        		+ "//parent::div//following-sibling::div//child::h4[text()='"+gameName+"']"))).click();
	        
	        List<String> quesList = new ArrayList<>();
	        
	        for (String[] pair : (List<String[]>)DataStore.get("questionset")) {
	        	quesList.add(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='question-header']//child::p[@class='question-text'])[1]"))).getText());
	        	String ans = pair[1];
	        		        	
	        	//select correct answer
	        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='options-container'])[1]//child::button["+ans+"]"))).click();
	        }
	        return quesList;
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
