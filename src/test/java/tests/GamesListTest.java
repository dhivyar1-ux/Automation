package tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.CreateGamesListPage;
import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Assessment.InteractionTemplatePage;
import pages.Dashboard.Games.GamesPage;
import pages.Dashboard.Reports.GameListReportPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorDashboardPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorPage;
import pages.Dashboard.Users.ContentCreator.CreateContentCreatorPage;
import pages.Dashboard.Users.Student.StudentDashboardPage;
import utils.DataStore;
import utils.ExcelUtils;
import utils.PageFactory;

public class GamesListTest extends BaseTest{
	//covers 1. Admin login 2. Create a new Game List and verify the added details
	 
    	CreateGamesListPage createGamesListPage = new CreateGamesListPage(driver);

	private GamesPage gamesPage;    
    private CreateContentCreatorPage createContentCreatorPage;
    private ContentCreatorDashboardPage contentCreatorDashboardPage;   
    private LoginPage login;    
    private DashboardPage dashboard;
    private LoginModalPage loginModalPage;

    private PageFactory pf;    
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
		gamesPage = pf.gamesPage();
        loginModalPage = pf.loginModalPage();
    }

	public void verifyGamesListDetailsInGrid() {
		
	   	String name = gamesPage.getValueByLabel("Name");
	   	String type = gamesPage.getValueByLabel("Games Type");
	   	String level = gamesPage.getValueByLabel("Level");
	   	String category = gamesPage.getValueByLabel("Category");
	   	String status = gamesPage.getValueByLabel("Status");
	   	String interactionTemplate = gamesPage.getValueByLabel("Interaction Templates");
	   	String practiceTask = gamesPage.getValueByLabel("Practice Task");
	    	
        softAssert.assertEquals(name,DataStore.gamesListName,"gamesListName check failed");
        softAssert.assertEquals(type,DataStore.gamesListType,"gamesListType check failed");
        softAssert.assertEquals(level,DataStore.gamesListLevel,"gamesListLevel check failed");
        softAssert.assertEquals(category,DataStore.gamesListCategory,"gamesListCategory check failed");
        softAssert.assertEquals(status,DataStore.gamesListPlanApproved,"gamesListPlanApproved check failed");        
        softAssert.assertEquals(interactionTemplate,DataStore.gamesListInteractionTemplate,"gamesListInteractionTemplate check failed");        
        softAssert.assertEquals(practiceTask,DataStore.gamesListPracticeTask,"gamesListPracticeTask check failed");        
        
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);
        
	}

    @Test(dependsOnMethods = "userLogin",dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateGamesListAndVerifyDetails(
    		String name,
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
        
    	dashboard.goToPage("gamesList");

    	gamesPage.clickCreateGamesList();

        // Create games list
    	createGamesListPage.createGamesList(name,
    			type, 
    			uploadImage, 
    			description, 
    			courseLevel,
    			category,
    			planApproved,
    			interactionTemplate,
    			interactionTemplateLevel,
    			interactionTemplateCategory,
    			practiceTask);
    	
			
		gamesPage.goToGamesList(DataStore.gamesListName);

        // VERIFY
        verifyGamesListDetailsInGrid();      
    }
    
    @Test(dependsOnMethods = "CreateGamesListAndVerifyDetails")
    @Parameters({"gameName", "studentName", "facultyName"})
	public void enrollGamesListAndVerifyDetails(String gameName, String studentName, String facultyName) {

    	dashboard.goToPage("gamesList");
    		 
    	gamesPage.enrollToGamesList(gameName,studentName,facultyName);
    }
    
    @Test
    @Parameters({"gameListName", "studentName", "studentPassword", "gameName", "gameType", "attemptNo", "date", "wrong", "right", "status"})
	public void verifyGamesEnrollment(String gameListName, 
			String studentName, 
			String studentPassword,
			String gameName,
			String gameType,
			String attemptNo,
			String date,
			String wrong,
			String right,
			String status
			) {

 /*   	//go to dashboard interaction template
    	DashboardPage dashboard = new DashboardPage(driver);
    	dashboard.goToPage("interac-template");
    	
    	//go to specific interaction template game and get questions and corresponding correct answer
    	InteractionTemplatePage it = new InteractionTemplatePage(driver);
    	it.goToInteractionTemplate(gameName);
    	
    	dashboard.logout();
        
        //Student login
        LoginPage login = new LoginPage(driver);
        login.login(studentName, studentPassword, true);
		
        //play the specific game
		StudentDashboardPage studentDashboardPage = new StudentDashboardPage(driver);
		List<String> quesList = studentDashboardPage.playSpecificGameList(gameListName,gameName);
		
		SoftAssert softAssert = new SoftAssert();
		
		for(int i = 0;i< quesList.size();i++) {
			softAssert.assertEquals(quesList.get(i),((List<String[]>)DataStore.get("questionset")).get(i)[0],"question check failed");
		}
		studentDashboardPage.logout();
*/		
		//admin login to check the report
		//userLogin((String)DataStore.get("loggedInUsername"),(String)DataStore.get("loggedInPassword"));
		
		DashboardPage dashboard = new DashboardPage(driver);
    	dashboard.goToPage("game-list-report");
    	
    	GameListReportPage gameListReportPage = new GameListReportPage(driver);
    	Map<String, String> actualReport = gameListReportPage.getReportForGameListAndStudent(gameListName,studentName);
    	
    	SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualReport.get("studentName"),studentName,"studentName check failed");
        softAssert.assertEquals(actualReport.get("gameListName"),gameListName,"gameListName check failed");
        softAssert.assertEquals(actualReport.get("gameName"),gameName,"gameName check failed");
        softAssert.assertEquals(actualReport.get("gameType"),gameType,"gameType check failed");
        softAssert.assertEquals(actualReport.get("attemptNo"),attemptNo,"attemptNo check failed");
        softAssert.assertEquals(actualReport.get("date"),date,"date check failed");
        softAssert.assertEquals(actualReport.get("wrong"),wrong,"wrong check failed");
        softAssert.assertEquals(actualReport.get("right"),right,"right check failed");
        softAssert.assertEquals(actualReport.get("status"),status,"status check failed");
    }

    @Test
    @Parameters({"gameName","gameType"})
	public void verifyGames(String gameName,String gameType) throws InterruptedException {

    		//go to Dashboard -> Assessment -> Interaction Template
    		DashboardPage dashboard = new DashboardPage(driver);
    		dashboard.goToPage("interac-template");
    	
    		//go to specific interaction template game and get questions and corresponding correct answer
    		InteractionTemplatePage it = new InteractionTemplatePage(driver);
    		it.getSpecificInteractionTemplate(gameName,gameType);
    	
    		dashboard.goToPage("interac-template");
    		it.previewSpecificInteractionTemplate(gameName,gameType);
    }
}

