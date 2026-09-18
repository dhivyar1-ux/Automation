package pages.Dashboard.Assessment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

/**
 * Page‑object for the “Create Interaction Template” form.
 *
 * The form contains:
 *   • Name (input#interaction-template-name)
 *   • Version (input#interaction-template-version)
 *   • Interaction Type (select#interaction-template-interactionType)
 *   • Cancel button (a#cancel-save)
 *   • Save button (button#save-entity)
 *
 * All actions wait for the element to be clickable/visible before interacting,
 * mirroring the style used in the rest of the code‑base.
 */
public class CreateInteractionPage extends BasePage {

    public CreateInteractionPage() {
        super();
    }

    /* --------------------------------------------------------------------- *
     *  Locators
     * --------------------------------------------------------------------- */
    private final By nameInput          = By.id("interaction-template-name");
    private final By versionInput       = By.id("interaction-template-version");
    private final By interactionTypeSel = By.id("interaction-template-interactionType");
    private final By saveBtn            = By.id("save-entity");
    private final By cancelBtn          = By.id("cancel-save");

    /* --------------------------------------------------------------------- *
     *  Form actions
     * --------------------------------------------------------------------- */

    /** Fill the “Name” field. */
    public CreateInteractionPage setName(String name) {
        WebElement el = wait.until(
                ExpectedConditions.elementToBeClickable(nameInput));
        el.clear();
        el.sendKeys(name);
        return this;
    }

    /** Fill the “Version” field. */
    public CreateInteractionPage setVersion(String version) {
        WebElement el = wait.until(
                ExpectedConditions.elementToBeClickable(versionInput));
        el.clear();
        el.sendKeys(version);
        return this;
    }

    /**
     * Select an interaction type from the drop‑down.
     *
     * @param type one of the option values, e.g. "McqInteraction",
     *             "MatchTheFollowing", "SurveyMCQ", … (exact values from the HTML)
     */
    public CreateInteractionPage selectInteractionType(String type) {
        // Click the <select> to open the dropdown (if the UI requires it)
        WebElement sel = wait.until(
                ExpectedConditions.elementToBeClickable(interactionTypeSel));
        sel.click();

        // Choose the <option> whose value attribute matches the supplied type
        By option = By.xpath(
                "//select[@id='interaction-template-interactionType']/option[@value='" + type + "']");
        WebElement opt = wait.until(
                ExpectedConditions.elementToBeClickable(option));
        opt.click();

        return this;
    }

    /**
     * Convenience method that fills the entire form and saves it.
     * Mirrors the style of {@code createFaculty} in {@code CreateFacultyPage}.
     *
     * @param name            value for the "Name" field
     * @param version         value for the "Version" field
     * @param interactionType value for the "Interaction Type" dropdown
     */
    public void createInteraction(String name, String version, String interactionType) {
        // Fill the fields using the existing helper methods for consistency
        this.setName(name);
        this.setVersion(version);
        this.selectInteractionType(interactionType);
        // Submit the form
        this.clickSave();

        // Store expected data for later verification (used by tests)
        utils.DataStore.interactionTemplateName = name;
        utils.DataStore.interactionTemplateVersion = version;
        utils.DataStore.interactionTemplateType = interactionType;

        // Small pause to allow UI to settle, matching other page objects
        wait3Seconds();
    }

    /** Click the “Save” button to submit the form. */
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    /** Click the “Back/Cancel” link to return to the list page. */
    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }

    /* --------------------------------------------------------------------- *
     *  Helper getters (optional – useful for assertions in tests)
     * --------------------------------------------------------------------- */

    /** Returns the current value of the Name field. */
    public String getName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput))
                .getAttribute("value");
    }

    /** Returns the current value of the Version field. */
    public String getVersion() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(versionInput))
                .getAttribute("value");
    }

    /** Returns the currently selected Interaction Type. */
    public String getSelectedInteractionType() {
        WebElement sel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(interactionTypeSel));
        return sel.findElement(By.cssSelector("option:checked")).getAttribute("value");
    }
}