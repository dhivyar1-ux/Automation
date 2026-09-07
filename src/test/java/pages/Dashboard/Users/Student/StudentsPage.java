package pages.Dashboard.Users.Student;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class StudentsPage extends BasePage {

    public StudentsPage() { super(); }

    public void clickCreateStudent() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/student/new']"))).click();
    }
    
    public boolean isStudentAvailable(String studentName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(studentName);

    	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='search'][1]//following::button")));
    	searchButton.click();
    	
    	//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@name='search'][1]")))).sendKeys(studentName);
    	//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@name='search'][1]//following::button")))).click();

		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noStudentMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noStudentMsg.isEmpty();
    }
    
    public void goToStudent(String studentName) {
		if (!isStudentAvailable(studentName)) {
			System.out.println("Student not found: " + studentName);
			return;
		}

		// Escape a possible single‑quote
		String safeName = studentName.replace("'", "\\'");

		// XPath that uniquely points to the name link inside the searchable table
		String xpath = "//td/a[normalize-space(.) = '" + safeName + "']";

		List<WebElement> matches = driver.findElements(By.xpath(xpath));
		for (WebElement el : matches) {
			System.out.println("  text='" + el.getText() + "'  displayed=" + el.isDisplayed());
		}

		// Wait for the exact link to be visible & clickable, then click it
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
	}
    
    public String getValueByLabel(String label) {
		WebElement element = wait.until(
			ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span")
			)
		);

		return element.getText();
    }
}

