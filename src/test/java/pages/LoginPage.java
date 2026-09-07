package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.DataStore;

public class LoginPage extends BasePage {

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.xpath("//button[normalize-space()='Login']");
    private final By submitBtn = By.xpath("//button[@type='submit']");

    public LoginPage() { super(); }

    public void enterUsername(String user) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
        System.out.println(username);
    }

    public void enterPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public void clickReLogin() {
        driver.findElement(submitBtn).click();
    }

    public void login(String user, String pass, boolean reLogin) {
        
        enterUsername(user);
        enterPassword(pass);
        clickReLogin();
    }

    public void login(String user, String pass) {
    	
    	enterUsername(user);
        enterPassword(pass);
        clickLogin();
        
		DataStore.loggedInUsername = user;
		DataStore.loggedInPassword = pass;
    }
}
