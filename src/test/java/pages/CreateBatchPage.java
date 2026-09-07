package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateBatchPage {

    WebDriver driver;

    public CreateBatchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void createBatch(String type,
			String name, 
			String startTime, 
			String endTime, 
			String currentLag,
			String status,
			String comments,
			String meetingLink,
			String facultyAccess,
			String category,
			String studentList,
			String facultyList,
			String course) throws InterruptedException {

    	By c_name = By.id("batch-batchName");
        By c_currentLag = By.id("batch-currentLag");
        By c_comments = By.id("batch-comments");
        By c_meetingLink = By.id("Meeting Link");
        By c_facultyAccess = By.id("facultyAccess");
                
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropDownTypeElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("batch-type")));
        Select dropdownType = new Select(dropDownTypeElement);
        dropdownType.selectByVisibleText(type);     
    	
        driver.findElement(c_name).sendKeys(name);
        
        WebElement dropDownStartTimeElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("startTime")));
        Select dropdownStartTime = new Select(dropDownStartTimeElement);
        dropdownStartTime.selectByVisibleText(startTime); 
        
        WebElement dropDownEndTimeElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("endTime")));
        Select dropdownEndTime = new Select(dropDownEndTimeElement);
        dropdownEndTime.selectByVisibleText(endTime);  

        driver.findElement(c_currentLag).sendKeys(currentLag);
        
        WebElement dropDownStatusElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("status")));
        Select dropdownStatus = new Select(dropDownStatusElement);
        dropdownStatus.selectByVisibleText(status);          
        
        driver.findElement(c_comments).sendKeys(comments);
        driver.findElement(c_meetingLink).sendKeys(meetingLink);
        if (facultyAccess.equals("TRUE")) {
        	driver.findElement(c_facultyAccess).click();
        }
        
        if (category.equals("Student")) {
        	driver.findElement(By.id("studentRadio")).click();
        } else if (category.equals("UserGroup")) {
        	driver.findElement(By.id("batchRadio")).click();
        }
        
        WebElement searchStudent = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                		By.xpath("//input[@name='searchStudent']")
                )
            );
            
        searchStudent.sendKeys("*");
        searchStudent.sendKeys(Keys.ENTER);
            
        WebElement optionList = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='student']")));
     	
     	for (int index = 0; index < 20; index++) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", optionList);
			//Thread.sleep(500);
		}
     	String[] students = studentList.split(",");
           
        for (String student:students) {
         	String xpathExpression = "//select[@id='student']//option[starts-with(normalize-space(), '" + student + " (')]";
         	
           	WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

           	String fullValue = option.getAttribute("value");

           	Select dropdown = new Select(driver.findElement(By.id("student")));
           	dropdown.selectByValue(fullValue);
        }
            
        WebElement searchFaculty = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                		By.xpath("//input[@name='searchFaculty']")
                )
            );
            
        searchFaculty.sendKeys("*");
        searchFaculty.sendKeys(Keys.ENTER);
            
        String[] faculties = facultyList.split(",");
           
        for (String faculty:faculties) {
         	String xpathExpression = "//select[@id='faculty']//option[starts-with(normalize-space(), '" + faculty + " (')]";

           	WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

           	String fullValue = option.getAttribute("value");

           	Select dropdown = new Select(driver.findElement(By.id("faculty")));
           	dropdown.selectByValue(fullValue);
        }
      
        WebElement searchCourse = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                		By.xpath("//input[@name='searchCourse']")
                )
            );
            
        searchCourse.sendKeys("*");
        searchCourse.sendKeys(Keys.ENTER);
           
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+course+"')]"))).click();
           
        //wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        driver.findElement(backBtn).click();
    }
}

