package tests;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Assessment.InteractionTemplatePage;
import pages.Dashboard.Assessment.CreateInteractionPage;
import pages.Dashboard.Assessment.InteractionDetailPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

/**
 * Test for creating a new Interaction Template and verifying its details.
 *
 * The test follows the same structure as other test classes in the project:
 *   • login using parameters (default admin/admin)
 *   • navigate to the Interaction Template list page
 *   • open the create form, fill it, and save
 *   • verify that the saved values appear in the grid
 */
public class InteractionTest extends BaseTest {

    private DashboardPage dashboard;
    private LoginPage login;
    private InteractionTemplatePage interactionTemplatePage;
    private CreateInteractionPage createInteractionPage;
    private InteractionDetailPage interactionDetailPage;
    private PageFactory pf;
    private SoftAssert softAssert = new SoftAssert();

    /**
     * Retrieves the values displayed in the Interaction Template grid and asserts them
     * against the static fields stored in {@link utils.DataStore}. This mirrors the
     * pattern used in {@code FacultyTest.verifyFacultyDetailsInGrid()}.
     */
    public void verifyInteractionTemplateDetailsInGrid() {
        String actualName = interactionDetailPage.getDetailValue("Name");
        String actualVersion = interactionDetailPage.getDetailValue("Version");
        String actualType = interactionDetailPage.getDetailValue("Interaction Type");

        softAssert.assertEquals(actualName, DataStore.interactionTemplateName, "Name check failed");
        softAssert.assertEquals(actualVersion, DataStore.interactionTemplateVersion, "Version check failed");
        softAssert.assertEquals(actualType, DataStore.interactionTemplateType, "Interaction Type check failed");

        interactionDetailPage.newInteractionCheck(softAssert);
    }

    /**
     * Compare the row that is stored in {@link DataStore#getMcqQuestionList()}
     * with the values that are currently displayed on the UI.
     *
     * @param rowIndex zero‑based index of the Excel row (and of the map stored
     *                 in DataStore)
     * @param uiValues map returned by {@link InteractionDetailPage#getCurrentMcqValues()}
     *                 (or getMcqValuesFromCard(...))
     */
    public void validateRow(int rowIndex, Map<String, Object> uiValues) {

        List<Map<String, Object>> storedList = DataStore.getMcqQuestionList();

        if (rowIndex >= storedList.size()) {
            throw new AssertionError(
                String.format("DataStore contains only %d MCQ rows, but test tried to validate row %d",
                            storedList.size(), rowIndex));
        }

        Map<String, Object> stored = storedList.get(rowIndex);

        // Iterate over the *expected* map (the one we stored) and compare
        stored.forEach((key, expectedObj) -> {
            Object actualObj = uiValues.get(key);
            String expected = expectedObj == null ? null : expectedObj.toString();
            String actual   = actualObj   == null ? null : actualObj.toString();

            softAssert.assertEquals(actual, expected,
                    String.format("Row %d – key \"%s\" mismatch (expected: %s, actual: %s)",
                                rowIndex, key, expected, actual));
        });
    }

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
        dashboard = pf.dashboardPage();
        login = pf.loginPage();
        interactionTemplatePage = pf.interactionTemplatePage();
        createInteractionPage = pf.createInteractionPage();
        interactionDetailPage = pf.interactionDetailPage();
    }

    @Parameters({"username", "password"})
    @Test(priority = 0)
    public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {
        login.login(user, pwd);
    }

    @Test(priority = 1, dependsOnMethods = "userLogin", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateInteractionTemplateAndVerify(String name, String version, String interactionType) {
        // Navigate to Interaction Template list page
        dashboard.goToPage("interac-template");

        // Open the create form
        interactionTemplatePage.clickCreateInteractionTemplate();

        // Fill the form and save using the convenience method
        createInteractionPage.createInteraction(name, version, interactionType);

        //lands in Interaction Template Page
        interactionTemplatePage.viewSpecificInteractionTemplate(DataStore.interactionTemplateName);

        // Verify the newly created entry using a dedicated verification method
        verifyInteractionTemplateDetailsInGrid();

    }

    @Test(priority = 2, dependsOnMethods = "CreateInteractionTemplateAndVerify", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateMCQInteractionQuestionsAndVerify(
        String questionId,
        String question,
        String choice1,
        String reason1,
        String choice2,
        String reason2,
        String choice3,
        String reason3,
        String choice4,
        String reason4,
        String correctChoice) {

        // --------------------------------------------------------------
        // Build the map that represents ONE MCQ row
        // --------------------------------------------------------------
        Map<String, Object> qMap = new HashMap<>();

        qMap.put("questionId",    questionId);
        qMap.put("question",      question);
        qMap.put("choice1",       choice1);
        qMap.put("reason1",       reason1);
        qMap.put("choice2",       choice2);
        qMap.put("reason2",       reason2);
        qMap.put("choice3",       choice3);
        qMap.put("reason3",       reason3);
        qMap.put("choice4",       choice4);
        qMap.put("reason4",       reason4);
        qMap.put("correctChoice", correctChoice);

        // --------------------------------------------------------------
        // Use the page‑object to fill the UI and verify the data
        // --------------------------------------------------------------
        InteractionDetailPage page = new InteractionDetailPage();

        page.fillMcqQuestion(qMap);

        System.out.println(DataStore.getMcqQuestionList());

        // --------------------------------------------------------------
        // 3️⃣  Read the values that are now displayed on the page.
        //     (The UI may have reformatted numbers, trimmed spaces, etc.)
        // --------------------------------------------------------------
        Map<String, Object> uiValues = page.getMcqValuesFromCard(Integer.parseInt(questionId));

        // --------------------------------------------------------------
        // 4️⃣  Validate – compare UI values with the map stored in DataStore
        // --------------------------------------------------------------
        int currentRowIndex = DataStore.getMcqQuestionList().size() - 1; // the row we just added
        validateRow(currentRowIndex, uiValues);

    }

    @Test(priority = 3, dependsOnMethods = "CreateMCQInteractionQuestionsAndVerify", alwaysRun = true)
	public void finalTest() {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteDocByName(DataStore.interactionTemplateName,"interaction_template");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteDocFromElastic(DataStore.interactionTemplateName,"interactiontemplate");

        softAssert.assertAll();
    }
}
