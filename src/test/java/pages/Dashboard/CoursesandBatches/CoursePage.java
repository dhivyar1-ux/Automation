package pages.Dashboard.CoursesandBatches;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoursePage {

    WebDriver driver;

    public CoursePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateCourse() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/course/new']"))).click();
    }
    
    public boolean isCourseAvailable(String courseName) {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(courseName);

    	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]//following::button")));
    	searchButton.click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noCourseMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noCourseMsg.isEmpty();
    }
    
    public void goToCourse(String courseName) {
    	
    	if (isCourseAvailable(courseName)) {
    		
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
		            By.xpath("//div[@class='table-responsive']//tbody//tr[1]//td//child::a")
		    ))).click();
		}
    }
    
    public String getValueByLabel(String label) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
        String value;
        if (label.equals("Name")) {
        	value = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h2[@data-cy='courseDetailsHeading'])[1]"))).getText();
        } else if (label.equals("Status")) {
        	value = driver.findElement(By.xpath("(//strong[contains(text(),'Status')]/parent::div/following-sibling::div)[1]")).getText();
        } else if (label.equals("Fees")) {
        	value = driver.findElement(By.xpath("//span[@class='Feesamount']")).getText().trim();
        } else if (label.equals("Validity Days")) {
            List<WebElement> elements = driver.findElements(By.xpath("(//strong[text()='" + label + "']/following-sibling::div)[1]"));
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            	value = "true";
            } else {
            	value = "false";
            }
        } else if (label.equals("Lab Configuration")) {
        	List<WebElement> labs = driver.findElements(By.xpath("(//h6[text()='Lab Configuration']//following-sibling::a)[1]"));
        	if (labs.isEmpty()) {
        		value = "";
        	} else {
        		value = labs.get(0).getText().trim();
        	}
        } else if (label.equals("McqTests") || label.equals("Programming Tests") || label.equals("Feedback Template")) {
        	List<WebElement> temp = driver.findElements(By.xpath("(//h6[text()='" + label + ":']//following-sibling::a)[1]"));
        	if (temp.isEmpty()) {
        		value = "";
        	} else {
        		value = temp.get(0).getText().trim();
        	}
        } else {
        	value = driver.findElement(By.xpath("(//strong[text()='" + label + "']/following-sibling::div)[1]")).getText();
        }
        
        return value;
    }
    
    public Map<String, String> getCourseDetails(String courseName) {
    	
    	Map<String, String> courseDetails = new HashMap<>();
    	
    	CoursePage coursePage = new CoursePage(driver);
		coursePage.goToCourse(courseName);
		
		courseDetails.put("name",coursePage.getValueByLabel("Name"));		
	   	courseDetails.put("type",coursePage.getValueByLabel("Type"));	    
	   	courseDetails.put("duration",coursePage.getValueByLabel("Duration"));
	   	courseDetails.put("placementAssistance",coursePage.getValueByLabel("Placement Assistance"));
	   	courseDetails.put("planApproved",coursePage.getValueByLabel("Plan Approved"));
	   	courseDetails.put("status",coursePage.getValueByLabel("Status"));
	   	courseDetails.put("comments",coursePage.getValueByLabel("Comments"));
	   	courseDetails.put("validityDays",coursePage.getValueByLabel("Validity Days"));	   
	   	courseDetails.put("level",coursePage.getValueByLabel("Level"));
	   	courseDetails.put("category",coursePage.getValueByLabel("Category"));
	   	courseDetails.put("fees",coursePage.getValueByLabel("Fees").substring(0,coursePage.getValueByLabel("Fees").length() - 2));
	   	courseDetails.put("labConfig",coursePage.getValueByLabel("Lab Configuration"));
	   	courseDetails.put("mcqTest",coursePage.getValueByLabel("McqTests"));
	   	courseDetails.put("progTest",coursePage.getValueByLabel("Programming Tests"));
	   	courseDetails.put("feedbackTemplate",coursePage.getValueByLabel("Feedback Template"));

	    //Need to add for reminder days,coins,image,course desc
	   	
	   	return courseDetails;
	   	
    }
}

