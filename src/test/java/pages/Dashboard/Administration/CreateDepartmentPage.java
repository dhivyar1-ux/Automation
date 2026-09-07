package pages.Dashboard.Administration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

/**
 * Page object for the **Create / Edit Department** screen.
 *
 * The UI contains a *search* box for the Department‑Head (Staff‑Admin) and a
 * list of matching names that appears after typing.  The method
 * {@link #selectDepartmentHead(String)} types the supplied name, waits for the
 * result list, and clicks the entry that exactly matches the argument.
 */
public class CreateDepartmentPage extends BasePage {

    public CreateDepartmentPage() { super(); }

    private By staffAdminSearchInput = By.name("searchStaffAdmin");

    private String resultItemXPathTemplate =
            "//div[@id='StaffAdmin']/following::ul//a[normalize-space(text())='%s']";

    private By saveButton = By.id("save-entity");

    // button that triggers the search (the magnifying‑glass button)
    private By searchButton = By.xpath("//input[@name='searchStaffAdmin']/following-sibling::button");

    // the drop‑down toggle that shows the list of staff admins
    private By staffAdminDropdownToggle = By.id("staff-admin");

    // the list container that becomes visible after the toggle is clicked
    private By staffAdminDropdownMenu = By.cssSelector("#staff-admin .DropdownMenu1");

    /**
     * Types the given {@code headName} into the “Search Department Head” box
     * and clicks the matching result.
     *
     * @param headName the exact name that appears in the result list
     *                 (e.g. “staffadmin 1”)
     */
    public void selectDepartmentHead(String headName) {
        //Click the search box (makes it active)
        WebElement searchBox = wait.until(
                ExpectedConditions.elementToBeClickable(staffAdminSearchInput));
        searchBox.clear();                 // ensure no leftover text
        searchBox.sendKeys(headName);      // type the name we are looking for

        // ---- Click the magnifying‑glass button ----
        WebElement btnSearch = wait.until(
                ExpectedConditions.elementToBeClickable(searchButton));
        btnSearch.click();

        // ---- Open the drop‑down (if it isn’t already open) ----
        WebElement toggle = wait.until(
                ExpectedConditions.elementToBeClickable(staffAdminDropdownToggle));
        toggle.click();

        // ---- Build an XPath for the exact option ----
        String optionXPath = String.format(
                "//div[@id='staff-admin']//button[contains(@class,'dropdown-item') and normalize-space(text())='%s']",
                headName);

        // ---- Wait for the option to be clickable and click it ----
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(optionXPath)));
        option.click();
        
        //short pause
        wait3Seconds();
    }

    /**
     * Clicks the **Save** button on the Create / Edit Department page.
     * The method waits until the button is clickable before performing the click.
     */
    public void clickSave() {
        // Wait for the button to be ready and click it
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(saveButton));
        btn.click();

        wait3Seconds();
    }
}