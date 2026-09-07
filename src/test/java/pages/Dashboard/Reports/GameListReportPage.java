package pages.Dashboard.Reports;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GameListReportPage {

    WebDriver driver;

    public GameListReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public Map<String, String> getReportForGameListAndStudent(String gameListName,String studentName) {
    	
    	//search the gamename in the input box
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(gameListName);
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='search'][1]//following::button)[1]"))).click();
    	
    	//select the specific game list name
    	driver.findElement(By.id("student")).click();
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@label='Select Game']"
    			+ "/child::div[@role='menu']/child::button[contains(text(),'"+gameListName+"')]"))).click();
    	
    	Map<String, String> report = new HashMap<>();
    	
    	report.put("studentName",wait.until(ExpectedConditions.elementToBeClickable(
    			By.xpath("(//div[@class='table-responsive'])[1]//tbody//tr[@class='align-middle']//td[1]"))).getText());
    	report.put("gameListName",wait.until(ExpectedConditions.elementToBeClickable(
    			By.xpath("(//div[@class='table-responsive'])[1]//tbody//tr//td[2]"))).getText());
    	
    	//select the specific student and click on Get Report
    	WebElement dropDownTypeElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("course")));
        Select dropdownType = new Select(dropDownTypeElement);
        dropdownType.selectByContainsVisibleText(studentName);
        
        //collect the report
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Get Report'])[1]"))).click();
        
        List<WebElement> attemptReport = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class='table-responsive'])[1]"
    			+ "//tbody//tr[@class='align-middle']/following-sibling::tr//child::table//child::tbody//child::td")));
    	report.put("gameName",attemptReport.get(0).getText());
    	report.put("gameType",attemptReport.get(1).getText());
    	report.put("attemptNo",attemptReport.get(2).getText());
    	report.put("date",attemptReport.get(3).getText());
    	report.put("wrong",attemptReport.get(4).getText());
    	report.put("right",attemptReport.get(5).getText());
    	report.put("status",attemptReport.get(6).getText());
    	return report;
    }
    
    public String getValueByLabel(String label) {
    	if (label.equals("Interaction Templates") || label.equals("Practice Task")) {
    		By content = By.xpath("(//b[text()='"+ label +"']/parent::div/following-sibling::div)[1]//child::a");
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	} else {
    		By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	}
    }
    
}


