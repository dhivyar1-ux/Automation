package pages.Dashboard.Users.UserGroup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.BasePage;
import utils.DataStore;

public class CreateUserGroupPage extends BasePage {

    public CreateUserGroupPage() { super(); }

    public void createUserGroup(String groupname,
    						String description, 
    						String studentsList) {

    	By ug_groupName = By.id("user-group-name");
        By ug_description = By.id("user-group-description");
                
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");
        
        driver.findElement(ug_groupName).sendKeys(groupname);
        driver.findElement(ug_description).sendKeys(description);
        
        WebElement searchInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(
            		By.xpath("//input[@name='searchStudent']")
            )
        );
        
        searchInput.sendKeys("*");
        searchInput.sendKeys(Keys.ENTER);
        
        String[] students = studentsList.split(",");
       
        for (String student:students) {
        	String xpathExpression = "//select[@id='student']//option[starts-with(normalize-space(), \"" + student + "\")]";

        	WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

        	String fullValue = option.getAttribute("value");

        	Select dropdown = new Select(driver.findElement(By.id("student")));
        	dropdown.selectByValue(fullValue);
        }

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();        
        //wait.until(ExpectedConditions.elementToBeClickable(backBtn)).click();
        
        wait3Seconds();
        
        //store expected data
        DataStore.ug_groupname = groupname;
        DataStore.ug_description = description;
        DataStore.ug_studentslist = studentsList; 
    }
}


