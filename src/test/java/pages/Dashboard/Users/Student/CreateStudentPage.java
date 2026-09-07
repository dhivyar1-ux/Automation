package pages.Dashboard.Users.Student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.BasePage;
import utils.DataStore;

public class CreateStudentPage extends BasePage{
    public CreateStudentPage(WebDriver driver) { super(); }

    public void createStudent(String username,
    						String firstName, 
    						String lastName, 
    						String email, 
    						String password,
    						String contactNumber,
    						String location,
    						String comments,
    						String category,
    						String collegeName,
    						String collegeStartYear,
    						String collegeEndYear,                            
    						String department,
    						String companyName,
    						String role,
    						String address,
    						String parentOrGuardianName,
    						String parentOrGuardianContact,
    						String currentCity,
    						String courseLevel) {

    	By s_userName = By.id("username");
        By s_firstName = By.id("firstName");
        By s_lastName = By.id("lastName");
        By s_email = By.id("email");
        By s_password = By.id("firstPassword");
        By s_confirmpassword = By.id("secondPassword");
        By s_contactNumber = By.id("student-contactNumber");
        By s_location = By.id("student-location");
        By s_comments = By.id("student-comments");
        By s_category = By.xpath("//input[@type='radio' and @id='studentOrEmployee' and @value='"+category+"']");
        By s_collegeName = By.id("student-collegeName");
        By s_collegeStartYear = By.id("student-collegeStartYear");
        By s_collegeEndYear = By.id("student-collegeEndYear");
        By s_companyName = By.id("student-companyName");
        By s_role = By.id("student-role");
        By s_address = By.id("student-address");
        By s_parentOrGuardianName = By.id("student-parentOrGuardianName");
        By s_parentOrGuardianContact = By.id("student-parentOrGuardianContact");
        By s_currentCity = By.id("student-currentCity");
        By s_courseLevel = By.id("course-level");
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");

        driver.findElement(s_userName).sendKeys(username);
        driver.findElement(s_firstName).sendKeys(firstName);
        driver.findElement(s_lastName).sendKeys(lastName);
        driver.findElement(s_email).sendKeys(email);
        driver.findElement(s_password).sendKeys(password);
        driver.findElement(s_confirmpassword).sendKeys(password);
        driver.findElement(s_contactNumber).sendKeys(contactNumber);
        driver.findElement(s_location).sendKeys(location);
        driver.findElement(s_comments).sendKeys(comments);
        driver.findElement(s_category).click();
        driver.findElement(s_collegeName).sendKeys(collegeName);
        driver.findElement(s_collegeStartYear).sendKeys(collegeStartYear);
        driver.findElement(s_collegeEndYear).sendKeys(collegeEndYear);
        if (category.equals("employee")) {
        	driver.findElement(s_companyName).sendKeys(companyName);
        	driver.findElement(s_role).sendKeys(role);
        } else {
        	
        	if (wait.until(ExpectedConditions.invisibilityOfElementLocated(s_companyName)) && wait.until(ExpectedConditions.invisibilityOfElementLocated(s_role))) {
        		System.out.println("Company and role are removed");
        	}
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#student-department .dropdown-toggle"))).click();        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='student-department']//button[contains(@value, '" + department + "')]"))).click();
        	
        driver.findElement(s_address).sendKeys(address);
        driver.findElement(s_parentOrGuardianName).sendKeys(parentOrGuardianName);
        driver.findElement(s_parentOrGuardianContact).sendKeys(parentOrGuardianContact);
        driver.findElement(s_currentCity).sendKeys(currentCity);
        
        Select dropdown = new Select(driver.findElement(s_courseLevel));
        // Pick an option by visible text
        dropdown.selectByValue(courseLevel);
        
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        //driver.findElement(backBtn).click();

        wait3Seconds();

         //store expected data
        DataStore.s_name = firstName+" "+lastName;
        DataStore.s_contactNumber = contactNumber;
        DataStore.s_location = location;
        DataStore.s_comments = comments;
        DataStore.s_courseLevel = courseLevel;
        DataStore.s_category = category;
        DataStore.s_collegeName = collegeName;
        DataStore.s_collegeStartYear = collegeStartYear;
        DataStore.s_collegeEndYear = collegeEndYear;
        DataStore.s_department = department;
        DataStore.s_address = address;
        DataStore.s_parentOrGuardianName = parentOrGuardianName;
        DataStore.s_parentOrGuardianContact = parentOrGuardianContact;
        DataStore.s_currentCity = currentCity;
    }
}

