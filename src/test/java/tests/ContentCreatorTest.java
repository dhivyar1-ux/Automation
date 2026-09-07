package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorDashboardPage;
import pages.Dashboard.Users.ContentCreator.ContentCreatorPage;
import pages.Dashboard.Users.ContentCreator.CreateContentCreatorPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class ContentCreatorTest extends BaseTest{
	//covers 1. Admin login 2. Create a new content creator and verify the added details 3. Added content creator should be able to successfully login

    private ContentCreatorPage contentCreatorPage;
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
        contentCreatorPage = pf.contentCreatorPage();
        createContentCreatorPage = pf.createContentCreatorPage();
        contentCreatorDashboardPage = pf.contentCreatorDashboardPage();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        loginModalPage = pf.loginModalPage();
    }

	public void verifyContentCreatorDetailsInGrid() {
		
	   	String name = contentCreatorPage.getValueByLabel("Name");
	   	String login = contentCreatorPage.getValueByLabel("Login");
	   	String email = contentCreatorPage.getValueByLabel("Email");
	   	String comments = contentCreatorPage.getValueByLabel("Comments");
	   	String skills = contentCreatorPage.getValueByLabel("Skills");
	    	
        softAssert.assertEquals(name,DataStore.cc_name);
        softAssert.assertEquals(login,DataStore.cc_login);
        softAssert.assertEquals(email,DataStore.cc_email);
        softAssert.assertEquals(comments,DataStore.cc_comments);
        softAssert.assertEquals(skills,DataStore.cc_skills);
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);
	}

    @Test(dependsOnMethods = "userLogin",dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateContentCreatorAndVerifyDetails(
    		String username,
			String firstName, 
			String lastName, 
			String email, 
			String password,
			String comments,
			String skills) {
        
    	dashboard.goToPage("content-creator");

    	contentCreatorPage.clickCreateContentCreator();

        // Create staff admin
    	createContentCreatorPage.createContentCreator(username,
    			firstName, 
    			lastName, 
    			email, 
    			password,
    			comments,
    			skills);    

        dashboard.goToPage("content-creator");       
        
		contentCreatorPage.goToContentCreator(DataStore.cc_login);

        // VERIFY
        verifyContentCreatorDetailsInGrid();
     }

    @Test(dependsOnMethods = "CreateContentCreatorAndVerifyDetails")
    public void verifyLogout() {

        dashboard.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Test(dependsOnMethods = "verifyLogout")
    public void contentCreatorLoginCheck() {

        //Added Student login
        login.login(DataStore.cc_login, DataStore.cc_password, true);
		
        // Check #1 – Welcome banner
        softAssert.assertEquals(contentCreatorDashboardPage.getWelcomeUsername(),DataStore.cc_login,"Welcome banner username mismatch!");

        // Check #2 – Account menu
        softAssert.assertEquals(contentCreatorDashboardPage.getAccountMenuUsername(),DataStore.cc_login,"Account menu username mismatch!");

        contentCreatorDashboardPage.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");			
    }

    @Test(dependsOnMethods = "contentCreatorLoginCheck")
	public void finalTest() {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(DataStore.cc_login,"content_creator");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteUserFromElastic(DataStore.cc_login,"contentcreator");

        softAssert.assertAll();
    }
}

