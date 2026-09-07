package pages.Dashboard.CoursesandBatches;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BatchPage {

    WebDriver driver;

    public BatchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateBatch() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/batch/new']"))).click();
    }
    
    public boolean isBatchAvailable(String courseName) {
    	
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
		
		List<WebElement> noBatchMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noBatchMsg.isEmpty();
    }
    
    public void goToBatch(String batchName) {
    	
    	if (isBatchAvailable(batchName)) {
    		
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
		            By.xpath("//div[@class='table-responsive']//tbody//tr[1]//td//child::a")
		    ))).click();
		}
    }
    
    public String getValueByLabel(String label) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
        String value;
 /*       if (label.equals("Name")) {
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
        	value = driver.findElement(By.xpath("(//h6[text()='Lab Configuration']//following-sibling::a)[1]")).getText().trim();
        } else if (label.equals("McqTests") || label.equals("Programming Tests") || label.equals("Feedback Template")) {
        	value = driver.findElement(By.xpath("(//h6[text()='" + label + ":']//following-sibling::a)[1]")).getText().trim();
        } else {
        	value = driver.findElement(By.xpath("(//span[contains((//span[contains(.,'"+label+"')]//parent::strong//parent::div//following-sibling::div)[1]text(),'" + label + "')]/parent::div/following-sibling::div)[1]")).getText();
        }*/
        if (label.equals("Course") || label.equals("Meeting Link")) {
        	value = driver.findElement(By.xpath("(//strong[contains(.,'"+label+"')]/parent::div//following-sibling::div)[1]")).getText();
        } else if (label.equals("Students")) {
        	List<WebElement> students = driver.findElements(By.xpath("(//dt[@class='section-title' and contains(text(),'Students')])[1]"
        			+ "//following-sibling::div//child::span"));
        	List<String> slist = new ArrayList<>();
        	for (WebElement student:students) {
        		slist.add(student.findElement(By.tagName("a")).getText());
        	}
        	value = String.join(",", slist);
        } else if (label.equals("Faculty")) {
        	List<WebElement> faculties = driver.findElements(By.xpath("(//dt[@class='section-title' and contains(text(),'Faculty')])[1]"
        			+ "//following-sibling::dd//child::span"));
        	List<String> flist = new ArrayList<>();
        	for (WebElement faculty:faculties) {        		
        		flist.add(faculty.findElement(By.tagName("a")).getText());
        	}
        	value = String.join(",", flist);
        } else if (label.equals("overallMCQTest")) {	
        	value = driver.findElement(By.xpath("(//dt[@class='session-plans-title' and contains(text(),'Overall Mcq and Programming Test')]"
        			+ "//following::div//child::div[@id='enableMCQTest'])[1]//child::a")).getText();
        } else if (label.equals("overallProgTest")) {	
        	value = driver.findElement(By.xpath("(//dt[@class='session-plans-title' and contains(text(),'Overall Mcq and Programming Test')]"
        			+ "//following::div//child::div[@id='enableTest'])[1]//child::a")).getText();
        } else {
        	value = driver.findElement(By.xpath("(//span[contains(.,'"+label+"')]//parent::strong//parent::div//following-sibling::div)[1]")).getText();
        }
        
        return value;
    }
}

