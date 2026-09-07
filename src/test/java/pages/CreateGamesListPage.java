package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utils.DataStore;

public class CreateGamesListPage extends BasePage {

    public CreateGamesListPage() { super(); }

    public void createGamesList(String name,
			String type, 
			String uploadImage, 
			String description, 
			String courseLevel,
			String category,
			String planApproved,
			String interactionTemplate,
			String interactionTemplateLevel,
			String interactionTemplateCategory,
			String practiceTask) {

    	By g_name = By.id("games-list-name");
        By g_type = By.id("games-list-gamesType");
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\" + uploadImage;
        By g_uploadImage = By.id("courseImage");
        By g_description = By.id("GameList");
        By g_courseLevel = By.id("course-level");
        By g_category = By.id("category");
        By g_planApproved = By.id("course-planApproved");    
        
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(g_name)).sendKeys(name);
        
        Select dropdownType = new Select(driver.findElement(g_type));
        dropdownType.selectByValue(type);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(g_uploadImage)).sendKeys(filePath);        
        wait.until(ExpectedConditions.visibilityOfElementLocated(g_description)).sendKeys(description);
        
        Select dropdownCourseLevel = new Select(driver.findElement(g_courseLevel));
        dropdownCourseLevel.selectByValue(courseLevel);
        
        Select dropdownCourseCategory = new Select(driver.findElement(g_category));
        dropdownCourseCategory.selectByValue(category);
        
        //disabled
        //driver.findElement(g_planApproved).sendKeys(planApproved);
 
        WebElement searchInteractionTemp = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='searchInteractionTemplate']")));        
        searchInteractionTemp.sendKeys("*");        
                
        //Select dropdownLevel = new Select(driver.findElement(By.xpath("((//input[@name='searchInteractionTemplate'])[1]//parent::div//child::select)[1]")));
        //dropdownLevel.selectByValue(interactionTemplateLevel);
                
        //Select dropdownCategory = new Select(driver.findElement(By.xpath("((//input[@name='searchInteractionTemplate'])[1]//parent::div//child::select)[2]")));
        //dropdownCategory.selectByValue(interactionTemplateCategory);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("((//div[@class='input-group'])[1]//child::button)[1]"))).click(); 
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='table-responsive']//child::label[text()='Select Interaction Template']//following::div//*[contains(text(),'"+interactionTemplate+"')]"))).click();
        
        WebElement searchPracticeTask = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='searchPracticeTask']")));        
        searchPracticeTask.sendKeys("*"); 
        searchPracticeTask.sendKeys(Keys.ENTER);   
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='table-responsive']//child::label[text()='Select Practice Task']//following::div//*[contains(text(),'"+practiceTask+"')]"))).click();
        
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        
        //driver.findElement(backBtn).click();

        //store expected data
        DataStore.gamesListName = name; 
    	DataStore.gamesListType = type;
    	DataStore.gamesListImage = uploadImage; 
    	DataStore.gamesListDescription = description; 
    	DataStore.gamesListLevel = courseLevel; 
    	DataStore.gamesListCategory = category; 
    	DataStore.gamesListPlanApproved = planApproved; 
    	DataStore.gamesListInteractionTemplate = interactionTemplate;
    	DataStore.gamesListInteractionTemplateLevel = interactionTemplateLevel;
    	DataStore.gamesListInteractionTemplateCategory = interactionTemplateCategory;
    	DataStore.gamesListPracticeTask = practiceTask;

        wait3Seconds();
    }
}


