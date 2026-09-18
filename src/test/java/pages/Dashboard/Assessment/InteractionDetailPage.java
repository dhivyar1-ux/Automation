package pages.Dashboard.Assessment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.BasePage;
import utils.DataStore;

import org.testng.asserts.SoftAssert;

public class InteractionDetailPage extends BasePage {

    /* -------------------------------------------------
   New locators for the MCQ section (desktop only)
   ------------------------------------------------- */

	// Header “MCQ Interaction Questions”
	private final By mcqHeader = By.xpath(
			"(//div[@id='content-container' and not(ancestor::*[contains(@class,'content-container-mobile')])]"
		+ "//div[@class='interactionInputfield1 card']//h5)[1]");

	// Total‑questions element (the second <h5>)
	private final By mcqTotal = By.xpath(
			"(//div[@id='content-container' and not(ancestor::*[contains(@class,'content-container-mobile')])]"
		+ "//div[@class='interactionInputfield1 card']//h5)[2]");

    // “Add MCQ Interaction Question” fields
    private final By questionIdField      = By.id("mcq-interaction-question-questionId");
    private final By questionField        = By.id("mcq-interaction-question-question");
    private final By choice1Field         = By.id("mcq-interaction-question-choice1");
    private final By reason1Field         = By.id("mcq-interaction-question-reason1");
    private final By choice2Field         = By.id("mcq-interaction-question-choice2");
    private final By reason2Field         = By.id("mcq-interaction-question-reason2");
    private final By choice3Field         = By.id("mcq-interaction-question-choice3");
    private final By reason3Field         = By.id("mcq-interaction-question-reason3");
    private final By choice4Field         = By.id("mcq-interaction-question-choice4");
    private final By reason4Field         = By.id("mcq-interaction-question-reason4");
    private final By correctChoiceSelect  = By.id("mcq-interaction-question-correctChoice");
	private final By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
    private final By backBtn = By.id("cancel-save");

	public InteractionDetailPage() { super(); }

    /**
	 * Returns the displayed value for a given label (Name, Version, Interaction Type)
	 * that appears in the Interaction Template details card.
	 *
	 * @param label the exact label text as shown on the UI (e.g. "Name")
	 * @return the value string (e.g. "samplemcqinterauto")
	 */
	public String getDetailValue(String label) {
		// Build an XPath that reliably finds the value for the supplied label.
		String xpath = "(//div[@id='content-container'])[1]"
					+ "//strong[contains(.,'" + label + "')]"
					+ "/ancestor::div[1]"
					+ "/following-sibling::div[1]"
					+ "//div[@class='valueDesign1']";

		// Wait until the element is visible and then read its text.
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
		).getText();
	}

    /* -------------------------------------------------
       MCQ‑section helpers
       ------------------------------------------------- */

    /** Returns true if the MCQ Interaction Questions card is present. */
    public boolean isMcqSectionDisplayed() {
        List<WebElement> headers = driver.findElements(mcqHeader);
        return !headers.isEmpty() && headers.get(0).isDisplayed();
    }

    /** Returns the exact header text – should be “MCQ Interaction Questions”. */
    public String getMcqHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(mcqHeader)).getText();
    }

    /** Returns the integer value shown after “Total Questions:”. */
    public int getMcqTotalQuestions() {
        String txt = wait.until(ExpectedConditions.visibilityOfElementLocated(mcqTotal)).getText(); // e.g. “Total Questions: 0”
        return Integer.parseInt(txt.replaceAll("[^0-9]", ""));
    }

    /* -------------------------------------------------
       NEW: “Add MCQ Interaction Question” field getters
       ------------------------------------------------- */

    public String getQuestionId()      { return wait.until(ExpectedConditions.visibilityOfElementLocated(questionIdField)).getAttribute("value"); }
    public String getQuestionText()    { return wait.until(ExpectedConditions.visibilityOfElementLocated(questionField)).getAttribute("value"); }
    public String getChoice1()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(choice1Field)).getAttribute("value"); }
    public String getReason1()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(reason1Field)).getAttribute("value"); }
    public String getChoice2()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(choice2Field)).getAttribute("value"); }
    public String getReason2()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(reason2Field)).getAttribute("value"); }
    public String getChoice3()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(choice3Field)).getAttribute("value"); }
    public String getReason3()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(reason3Field)).getAttribute("value"); }
    public String getChoice4()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(choice4Field)).getAttribute("value"); }
    public String getReason4()         { return wait.until(ExpectedConditions.visibilityOfElementLocated(reason4Field)).getAttribute("value"); }
    public String getCorrectChoice()   {
        WebElement sel = wait.until(ExpectedConditions.visibilityOfElementLocated(correctChoiceSelect));
        return sel.getAttribute("value");   // empty string if nothing selected
    }

    /** Convenience method that asserts every “Add MCQ Interaction Question” field is **not** empty. */
    public void newInteractionCheck(SoftAssert soft) {
		String interaction_type = "";
        switch (DataStore.interactionTemplateType) {
            case "McqInteraction":
                interaction_type = "MCQ Interaction Questions";
                break;
        }
        soft.assertEquals(getMcqHeaderText(), interaction_type, "Interaction Type check failed");
        soft.assertEquals(getMcqTotalQuestions(), 0, "Interaction Total questions check failed");
        soft.assertEquals(getQuestionId(),"1","QuestionId should be 1");

        soft.assertTrue(getQuestionText().isEmpty(),    "Question text should be empty");
        soft.assertTrue(getChoice1().isEmpty(),        "Choice 1 should be empty");
        soft.assertTrue(getReason1().isEmpty(),        "Reason 1 should be empty");
        soft.assertTrue(getChoice2().isEmpty(),        "Choice 2 should be empty");
        soft.assertTrue(getReason2().isEmpty(),        "Reason 2 should be empty");
        soft.assertTrue(getChoice3().isEmpty(),        "Choice 3 should be empty");
        soft.assertTrue(getReason3().isEmpty(),        "Reason 3 should be empty");
        soft.assertTrue(getChoice4().isEmpty(),        "Choice 4 should be empty");
        soft.assertTrue(getReason4().isEmpty(),        "Reason 4 should be empty");
        soft.assertTrue(getCorrectChoice().isEmpty(),  "Correct Choice should noy be selected");
    }

	public void fillMcqQuestion(Map<String, Object> q) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(questionField))
			.sendKeys(q.get("question").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(choice1Field))
			.sendKeys(q.get("choice1").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(reason1Field))
			.sendKeys(q.get("reason1").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(choice2Field))
			.sendKeys(q.get("choice2").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(reason2Field))
			.sendKeys(q.get("reason2").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(choice3Field))
			.sendKeys(q.get("choice3").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(reason3Field))
			.sendKeys(q.get("reason3").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(choice4Field))
			.sendKeys(q.get("choice4").toString());

		wait.until(ExpectedConditions.visibilityOfElementLocated(reason4Field))
			.sendKeys(q.get("reason4").toString());

		new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(correctChoiceSelect)))
			.selectByValue(q.get("correctChoice").toString());
		
        DataStore.addMcqQuestionMap(q);

		wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

		wait3Seconds();
	}

    /**
     * Reads **all** MCQ fields that belong to a specific question card
     * (identified by its displayed number, e.g. “1”, “2”, …) and returns them
     * as a <code>Map&lt;String,Object&gt;</code>.
     *
     * The keys are exactly the same as the ones you use when you store a
     * question in {@link utils.DataStore#addMcqQuestionMap(java.util.Map)}.
     *
     * @param questionNumber the number shown in the UI (the text inside
     *                       <code>div.questionNumber</code>)
     * @return map containing: questionId, question, choice1, reason1,
     *         choice2, reason2, choice3, reason3, choice4, reason4,
     *         correctChoice
     */
    public Map<String, Object> getMcqValuesFromCard(int questionNumber) {
        Map<String, Object> map = new HashMap<>();

        // -----------------------------------------------------------------
        // Build an XPath that points to the *card* that contains the given
        // question number.  All the inner fields are then located relative
        // to that card.
        // -----------------------------------------------------------------
        String cardXpath = "//div[@class='questionsContainer']"
                + "//div[contains(@class,'questionNumber') and normalize-space(.)='"
                + questionNumber + "']"
                + "/ancestor::div[contains(@class,'interactionInputfield')]";

        // Question ID – the value that appears after “Question ID:”
        String qIdXpath = cardXpath + "//p[strong[contains(.,'Question ID:')]]/div";
        // Question text – the text after “Question 1:”
        String qTextXpath = cardXpath + "//div[@class='questionText']";
        // Choice / Reason pairs – we locate them by the heading text
        String choice1Xpath = cardXpath + "//p[strong[contains(.,'Choice 1:')]]/div";
        String reason1Xpath = cardXpath + "//p[strong[contains(.,'Reason 1:')]]/div";
        String choice2Xpath = cardXpath + "//p[strong[contains(.,'Choice 2:')]]/div";
        String reason2Xpath = cardXpath + "//p[strong[contains(.,'Reason 2:')]]/div";
        String choice3Xpath = cardXpath + "//p[strong[contains(.,'Choice 3:')]]/div";
        String reason3Xpath = cardXpath + "//p[strong[contains(.,'Reason 3:')]]/div";
        String choice4Xpath = cardXpath + "//p[strong[contains(.,'Choice 4:')]]/div";
        String reason4Xpath = cardXpath + "//p[strong[contains(.,'Reason 4:')]]/div";
        // Correct choice – the text after “Correct Choice:”
        String correctChoiceXpath = cardXpath + "//p[strong[contains(.,'Correct Choice:')]]";

        // -----------------------------------------------------------------
        // Helper that safely extracts the text (empty string if element not found)
        // -----------------------------------------------------------------
        java.util.function.Function<String, String> txt = xpath -> {
            try {
                WebElement el = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                return el.getText().trim();
            } catch (Exception e) {
                return "";   // element missing – treat as empty
            }
        };

        map.put("questionId",    txt.apply(qIdXpath));
        map.put("question",      txt.apply(qTextXpath));
        map.put("choice1",       txt.apply(choice1Xpath));
        map.put("reason1",       txt.apply(reason1Xpath));
        map.put("choice2",       txt.apply(choice2Xpath));
        map.put("reason2",       txt.apply(reason2Xpath));
        map.put("choice3",       txt.apply(choice3Xpath));
        map.put("reason3",       txt.apply(reason3Xpath));
        map.put("choice4",       txt.apply(choice4Xpath));
        map.put("reason4",       txt.apply(reason4Xpath));

        // “Correct Choice:” is a <p> that contains the value after the colon.
        // We strip the label part and keep only the number.
        String rawCorrect = txt.apply(correctChoiceXpath);
        String correct = rawCorrect.replaceAll(".*Correct Choice:\\s*", "").trim();
        map.put("correctChoice", correct);

        return map;
    }
}