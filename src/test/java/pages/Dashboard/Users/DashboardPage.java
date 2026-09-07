package pages.Dashboard.Users;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class DashboardPage extends BasePage {

    public DashboardPage() { super(); }

    // Welcome banner username
    By welcomeUser = By.cssSelector(".welcome-content h4 span");

    // Account menu username (under #root)
    By accountMenuUser = By.cssSelector(".account-menu-container span");
    
    public String getWelcomeUsername() {
		WebElement el = wait.until(
			ExpectedConditions.visibilityOfElementLocated(welcomeUser));
		return el.getText().trim();
    }

    public String getAccountMenuUsername() {
		WebElement el = wait.until(
			ExpectedConditions.visibilityOfElementLocated(accountMenuUser));
		return el.getText().trim();
    }

    public void logout() {
        // Click account menu
    	wait.until(ExpectedConditions.elementToBeClickable(accountMenuUser)).click();

        // Logout option (adjust text if needed)
        By logoutBtn = By.xpath("//a[@href='/logout']");
        
        // wait and click logout
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }    
    
    public void goToPage(String tab) {
    	
    	if (tab.equals("home")) {
    		By homeTab = By.xpath("//a//span[contains(text(),'Home')]");
    		wait.until(ExpectedConditions.elementToBeClickable(homeTab)).click();
    	} else if(tab.equals("course") || tab.equals("batch")) {
    		By coursesAndBatchesTab = By.xpath("//span[normalize-space()='Courses and Batches']");
    		wait.until(ExpectedConditions.elementToBeClickable(coursesAndBatchesTab)).click();   
    		if (tab.equals("course")) {
    			By courseTab = By.cssSelector("a[href='/course']");
    			wait.until(ExpectedConditions.elementToBeClickable(courseTab)).click();  
    		} else if(tab.equals("batch")) {
    			By batchTab = By.cssSelector("a[href='/batch']");
    			wait.until(ExpectedConditions.elementToBeClickable(batchTab)).click();
    		}
    	} else if (tab.equals("gamesList")) {
    		By gamesTab = By.xpath("//span[normalize-space()='Games']");
    		wait.until(ExpectedConditions.elementToBeClickable(gamesTab)).click();   
    		if (tab.equals("gamesList")) {
    			By gamesListTab = By.cssSelector("a[href='/games-list']");
    			wait.until(ExpectedConditions.elementToBeClickable(gamesListTab)).click();  
    		}
    	} else if (tab.equals("app-admin") || tab.equals("faculty") || tab.equals("staff-admin") || tab.equals("content-creator") || tab.equals("student") || tab.equals("usergroup")) {
    		By usersTab = By.xpath("//div[contains(@class,'sidebar')]//span[normalize-space()='Users']");
			
			// Locator for the “Student” sub‑item (adjust if the text differs)
			By studentSubItem = By.xpath("//div[@id='aside-container']//div[contains(@class,'sidebar')]//a[@href='/student']");

			List<WebElement> elems = driver.findElements(studentSubItem);
			boolean studentVisible = false;

			if (!elems.isEmpty()) {
				// The element exists in the DOM – now check whether it is actually displayed
				studentVisible = elems.get(0).isDisplayed();
			}
			
			// 2️⃣  Click the parent only when needed
			if (!studentVisible) {
				wait.until(ExpectedConditions.elementToBeClickable(usersTab)).click();
			}
			
    		if(tab.equals("usergroup")) {    		
    			By userTypeTab = By.xpath("//div[contains(@class, 'menu-item') and contains(., 'Users Group')]");  
    			wait.until(ExpectedConditions.elementToBeClickable(userTypeTab)).click();
    		} else {
    			By userTypeTab = By.cssSelector("a[href='/"+tab+"']");    		
    			wait.until(ExpectedConditions.elementToBeClickable(userTypeTab)).click();
    		}    		
    	} else if (tab.equals("interac-template")) {
    		By assessTab = By.xpath("//span[normalize-space()='Assessment']");
    		WebElement panel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[contains(text(),'Assessment')]//parent::div)[1]//following-sibling::div")));
    		String className = panel.getAttribute("class");
    		if ("collapse".equals(className)) {
    			wait.until(ExpectedConditions.elementToBeClickable(assessTab)).click();  
    		}    		 
    		if (tab.equals("interac-template")) {
    			By interacTempTab = By.cssSelector("a[href='/interaction-template']");
    			wait.until(ExpectedConditions.elementToBeClickable(interacTempTab)).click();  
    		}
    	} else if (tab.equals("game-list-report")) {
    		By reportsTab = By.xpath("//span[normalize-space()='Reports']");
    		wait.until(ExpectedConditions.elementToBeClickable(reportsTab)).click();   
    		if (tab.equals("game-list-report")) {
    			By gamelistReportTab = By.cssSelector("a[href='/gamelist-report']");
    			wait.until(ExpectedConditions.elementToBeClickable(gamelistReportTab)).click();  
    		}
    	} else if (tab.equals("department")) {
    		By adminTab = By.xpath("//span[normalize-space()='Administration']");
    		wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();   
    		if (tab.equals("department")) {
    			By deptTab = By.cssSelector("a[href='/admin/department']");
    			wait.until(ExpectedConditions.elementToBeClickable(deptTab)).click();  
    		}
    	}    
    }
}
