package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.AppAdmin.AppAdminDashboardPage;
import pages.Dashboard.Users.AppAdmin.AppAdminPage;
import pages.Dashboard.Users.AppAdmin.CreateAppAdminPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class AppAdminTest extends BaseTest{
	//covers 1. Admin login 2. Create a new appadmin and verify the added details 3. Added app admin should be able to successfully log in
	private AppAdminPage appAdminPage;
   	private LoginPage login;    
    private DashboardPage dashboard;    
	private AppAdminDashboardPage appAdminDashboardPage;  
    private LoginModalPage loginModalPage;
    private CreateAppAdminPage createAppAdmin;
        
    private SoftAssert softAssert = new SoftAssert();
    private PageFactory pf;

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
        appAdminPage = pf.appAdminPage();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        appAdminDashboardPage = pf.appAdminDashboardPage(); 
        loginModalPage = pf.loginModalPage();
        createAppAdmin = pf.createAppAdminPage();
    }

	public void verifyAppAdminDetailsInGrid() {

	   	String name = appAdminPage.getValueByLabel("Name");
	   	String skills = appAdminPage.getValueByLabel("Skills");
	   	String comments = appAdminPage.getValueByLabel("Comments");
	   	String targets = appAdminPage.getValueByLabel("Targets");
	   	String incentives = appAdminPage.getValueByLabel("Incentives");
	   	String type = appAdminPage.getValueByLabel("Type");

        softAssert.assertEquals(name,DataStore.a_name);
        softAssert.assertEquals(skills,DataStore.a_skills);
        softAssert.assertEquals(comments,DataStore.a_comments);
        softAssert.assertEquals(targets,DataStore.a_targets);
        softAssert.assertEquals(incentives,DataStore.a_incentives);
        softAssert.assertEquals(type,DataStore.a_type);
    }
	 
	@Parameters({"username", "password"})
    @Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);
	}

    @Test(dependsOnMethods = "userLogin", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateAppAdminAndVerifyDetails(
    		String username,
			String firstName, 
			String lastName, 
			String email, 
			String password,
			String skills,
			String comments,
			String targets,
			String incentives,
			String type) {
        
    	dashboard.goToPage("app-admin");

    	appAdminPage.clickCreateAppAdmin();

        // Create student
        createAppAdmin.createAppAdmin(username,
    			firstName, 
    			lastName, 
    			email, 
    			password,
    			skills,
    			comments,
    			targets,
    			incentives,
    			type);
         
        dashboard.goToPage("app-admin");
        
		appAdminPage.goToAppAdmin(DataStore.a_name);

        // VERIFY
        verifyAppAdminDetailsInGrid();
    }
        
    @Test(dependsOnMethods = "CreateAppAdminAndVerifyDetails")
    public void verifyLogout() {
        dashboard.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters({"appAdminName", "appAdminPassword"})
    @Test(dependsOnMethods = "verifyLogout")
	public void appAdminLoginCheck(@Optional("autotestappadmin1") String appAdminName, @Optional("1234") String appAdminPassword) {

        //Added AppAdmin login
        login.login(appAdminName, appAdminPassword,true);
		
        // Check #1 – Welcome banner
        softAssert.assertEquals(appAdminDashboardPage.getWelcomeUsername(),appAdminName,"Welcome banner username mismatch!");

        // Check #2 – Account menu
        softAssert.assertEquals(appAdminDashboardPage.getAccountMenuUsername(),appAdminName,"Account menu username mismatch!");

        appAdminDashboardPage.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters("appAdminName")
    @Test(dependsOnMethods = "appAdminLoginCheck")
	public void finalTest(@Optional("sampleappadmin") String appAdminName) {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(appAdminName,"app_admin");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteUserFromElastic(appAdminName,"app_admin");

        softAssert.assertAll();
    }
}
