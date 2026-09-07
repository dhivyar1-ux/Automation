package pages.Dashboard.Users.AppAdmin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class CreateAppAdminPage extends BasePage {

    public CreateAppAdminPage() { super(); }

    public void createAppAdmin(String username,
			String firstName, 
			String lastName, 
			String email, 
			String password,
			String skills,
			String comments,
			String targets,
			String incentives,
			String type) {

    	By a_userName = By.id("username");
        By a_firstName = By.id("firstName");
        By a_lastName = By.id("lastName");
        By a_email = By.id("email");
        By a_password = By.id("firstPassword");
        By a_confirmpassword = By.id("secondPassword");
        By a_skills = By.id("app-admin-skills");
        By a_comments = By.id("app-admin-comments");
        By a_targets = By.id("app-admin-targets");
        By a_incentives = By.id("app-admin-incentives");
        By a_type = By.id("app-admin-type");
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");

        driver.findElement(a_userName).sendKeys(username);
        driver.findElement(a_firstName).sendKeys(firstName);
        driver.findElement(a_lastName).sendKeys(lastName);
        driver.findElement(a_email).sendKeys(email);
        driver.findElement(a_password).sendKeys(password);
        driver.findElement(a_confirmpassword).sendKeys(password);
        driver.findElement(a_skills).sendKeys(skills);
        driver.findElement(a_comments).sendKeys(comments);
        driver.findElement(a_targets).sendKeys(targets);
        driver.findElement(a_incentives).sendKeys(incentives);
        driver.findElement(a_type).sendKeys(type);
        
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        //driver.findElement(backBtn).click();

        wait3Seconds();

        //store expected data
        DataStore.a_name = firstName+" "+lastName;
        DataStore.a_skills = skills;
        DataStore.a_comments = comments;
        DataStore.a_targets = targets;
        DataStore.a_incentives = incentives;
        DataStore.a_type = type;
    }
}

