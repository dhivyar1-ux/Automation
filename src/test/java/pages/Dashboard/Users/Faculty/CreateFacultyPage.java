package pages.Dashboard.Users.Faculty;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class CreateFacultyPage extends BasePage {

    public CreateFacultyPage() { super(); }

    public void createFaculty(String username,
    						String firstName, 
    						String lastName, 
    						String email, 
    						String password,
    						String skills,
    						String comments,
    						String department) {

    	By f_userName = By.id("username");
        By f_firstName = By.id("firstName");
        By f_lastName = By.id("lastName");
        By f_email = By.id("email");
        By f_password = By.id("firstPassword");
        By f_confirmpassword = By.id("secondPassword");
        By f_skills = By.id("faculty-skills");
        By f_comments = By.id("faculty-comments");
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");

        driver.findElement(f_userName).sendKeys(username);
        driver.findElement(f_firstName).sendKeys(firstName);
        driver.findElement(f_lastName).sendKeys(lastName);
        driver.findElement(f_email).sendKeys(email);
        driver.findElement(f_password).sendKeys(password);
        driver.findElement(f_confirmpassword).sendKeys(password);
        driver.findElement(f_skills).sendKeys(skills);
        driver.findElement(f_comments).sendKeys(comments);
        
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#student-department .dropdown-toggle"))).click();        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='student-department']//button[contains(@value, '" + department + "')]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        //driver.findElement(backBtn).click();
        
        //store expected data
        DataStore.f_name = firstName+" "+lastName;
        DataStore.f_skills = skills;
        DataStore.f_comments = comments;

        wait3Seconds();
    }
}


