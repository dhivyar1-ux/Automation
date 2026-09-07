package pages.Dashboard.Administration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class DepartmentPage extends BasePage {
    public DepartmentPage() { super(); }

    public void clickCreateDepartment() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/admin/department/new']"))).click();
    }
    
    public boolean isDepartmentAvailable(String departmentName) {
    	// 1️⃣ Wait until the table is rendered
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='table-responsive']//table")));

		// 2️⃣ Find all <a> elements inside the first <td> of each row.
		//    The XPath selects: <tbody> → <tr> → first <td> → <a>
		List<WebElement> departmentLinks = driver.findElements(
				By.xpath("//div[@class='table-responsive']//tbody/tr/td[1]/a"));

		// 3️⃣ Iterate over the links and compare the visible text.
		for (WebElement link : departmentLinks) {
			if (departmentName.equals(link.getText().trim())) {
				return true;            // ✅ Department found
			}
		}

		// 4️⃣ No matching row was found.
		return false;
    }
    
    public void goToDepartment(String departmentName) {
    	
    	if (isDepartmentAvailable(departmentName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("//div[@class='table-responsive']//tbody//tr//td//child::a[contains(text(),'"+departmentName+"')]"))).click();
        	wait3Seconds();
		}
    }
    
    public String getValueByLabel(String label) {
    	By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    }

	/**
	 * Clicks the **Edit** button of the row that contains the given department name.
	 *
	 * @param departmentName the exact text shown in the first column (e.g. "Computer Science")
	 */
	public void goToEditDepartment(String departmentName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='table-responsive']//table")));

		//    • Finds the <tr> whose first <td>/<a> text matches the department name
		//    • Then selects the <a> with data‑cy="entityEditButton" inside that row
		String editBtnXPath = String.format(
				"//div[@class='table-responsive']//tbody/tr[td[1]/a[text()='%s']]"
				+ "//a[@data-cy='entityEditButton']",
				departmentName);

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath(editBtnXPath))).click();

		wait3Seconds();
	}
}



