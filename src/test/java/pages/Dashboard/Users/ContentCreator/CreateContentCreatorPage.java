package pages.Dashboard.Users.ContentCreator;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class CreateContentCreatorPage extends BasePage{

    public CreateContentCreatorPage() { super(); }

    public void createContentCreator(String username,
    						String firstName, 
    						String lastName, 
    						String email, 
    						String password,
    						String comments,
    						String skills) {

    	By cc_userName = By.id("username");
        By cc_firstName = By.id("content-creator-firstName");
        By cc_lastName = By.id("content-creator-lastName");
        By cc_email = By.id("content-creator-email");
        By cc_password = By.id("firstPassword");
        By cc_confirmpassword = By.id("secondPassword");
        By cc_comments = By.id("content-creator-comments");
        By cc_skills = By.id("content-creator-skills");
        
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.cssSelector("a[href='/content-creator']");
        
        driver.findElement(cc_userName).sendKeys(username);
        driver.findElement(cc_firstName).sendKeys(firstName);
        driver.findElement(cc_lastName).sendKeys(lastName);
        driver.findElement(cc_email).sendKeys(email);
        driver.findElement(cc_password).sendKeys(password);
        driver.findElement(cc_confirmpassword).sendKeys(password);
        driver.findElement(cc_comments).sendKeys(comments);
        driver.findElement(cc_skills).sendKeys(skills);       
        
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        
        //driver.findElement(backBtn).click();

        wait3Seconds();
        
         //store expected data
        DataStore.cc_name = firstName+" "+lastName;
        DataStore.cc_login = username;
        DataStore.cc_password = password;
        DataStore.cc_email = email;
        DataStore.cc_comments = comments;
        DataStore.cc_skills = skills;   
    }
}


