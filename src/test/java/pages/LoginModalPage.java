package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginModalPage extends BasePage {
    public LoginModalPage() { super(); }

    public String getSignInText() {
        WebElement signInElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#login-title h5.modal-title")
                )
        );
        return signInElement.getText();
    }
}
