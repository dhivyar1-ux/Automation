package pages.Dashboard.Users.StaffAdmin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class CreateStaffAdminPage extends BasePage{

    public CreateStaffAdminPage() { super(); }

    public void createStaffAdmin(String username,
    						String firstName, 
    						String lastName, 
    						String email, 
    						String password,
    						String skills,
    						String comments,
    						String department) {

    	By sa_userName = By.id("username");
        By sa_firstName = By.id("firstName");
        By sa_lastName = By.id("lastName");
        By sa_email = By.id("email");
        By sa_password = By.id("firstPassword");
        By sa_confirmpassword = By.id("secondPassword");
        By sa_skills = By.id("skills");
        By sa_comments = By.id("comments");
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.cssSelector("a[href='/staff-admin']");
        
        driver.findElement(sa_userName).sendKeys(username);
        driver.findElement(sa_firstName).sendKeys(firstName);
        driver.findElement(sa_lastName).sendKeys(lastName);
        driver.findElement(sa_email).sendKeys(email);
        driver.findElement(sa_password).sendKeys(password);
        driver.findElement(sa_confirmpassword).sendKeys(password);
        driver.findElement(sa_skills).sendKeys(skills);
        driver.findElement(sa_comments).sendKeys(comments);
        
        //Department is disabled.
        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#student-department button"))).click();        
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+department+"')]"))).click();
        
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        //driver.findElement(backBtn).click();
        
        //store expected data
        DataStore.sa_username = username;
        DataStore.sa_name = firstName+" "+lastName;
        DataStore.sa_department = department;
        DataStore.sa_comments = comments;

        wait3Seconds();
    }
}


