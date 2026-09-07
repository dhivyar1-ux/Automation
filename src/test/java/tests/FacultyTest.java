package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.Faculty.CreateFacultyPage;
import pages.Dashboard.Users.Faculty.FacultyDashboardPage;
import pages.Dashboard.Users.Faculty.FacultyPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class FacultyTest extends BaseTest {
	//covers 1. Admin login 2. Create a new faculty and verify the added details 3. Added faculty should be able to successfully login
	private FacultyPage dashboardFacultyPage;
    private DashboardPage dashboard;
    private LoginPage login;
    private CreateFacultyPage createFaculty;
    private FacultyDashboardPage facultyDashboardPage;
    private LoginModalPage loginModalPage;

    private PageFactory pf;    
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
        dashboardFacultyPage = pf.dashboardFacultyPage();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        createFaculty = pf.createFaculty();
        facultyDashboardPage = pf.facultyDashboardPage();
        loginModalPage = pf.loginModalPage();
    }

	public void verifyFacultyDetailsInGrid() {
		
	   	String name = dashboardFacultyPage.getValueByLabel("Name");
	   	String skills = dashboardFacultyPage.getValueByLabel("Skills");
	   	String comments = dashboardFacultyPage.getValueByLabel("Comments");	    	

        softAssert.assertEquals(name,DataStore.f_name);
        softAssert.assertEquals(skills,DataStore.f_skills);
        softAssert.assertEquals(comments,DataStore.f_comments);
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {
        login.login(user, pwd);
	}

    @Test(dependsOnMethods = "userLogin", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateFacultyAndVerifyDetails(
    		String username,
			String firstName, 
			String lastName, 
			String email, 
			String password,
			String skills,
			String comments,
			String department) {
        
    	dashboard.goToPage("faculty");

    	dashboardFacultyPage.clickCreateFaculty();

        // Create faculty
        createFaculty.createFaculty(username,
    			firstName, 
    			lastName, 
    			email, 
    			password,
    			skills,
    			comments,
    			department);
        
        dashboard.goToPage("faculty");
       
		dashboardFacultyPage.goToFaculty(DataStore.f_name);

        // VERIFY
        verifyFacultyDetailsInGrid();
    }

    @Test(dependsOnMethods = "CreateFacultyAndVerifyDetails")
    public void verifyLogout() {
        dashboard.logout();
        
        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters({"facultyName", "facultyPassword"})
    @Test(dependsOnMethods = "verifyLogout")
	public void facultyLoginCheck(@Optional("autotestfac1") String facultyName, @Optional("1234") String facultyPassword) {

        //Added Student login
        login.login(facultyName, facultyPassword,true);
		
        // Check #1 – Welcome banner
        softAssert.assertEquals(facultyDashboardPage.getWelcomeUsername(),facultyName,"Welcome banner username mismatch!");

        // Check #2 – Account menu
        softAssert.assertEquals(facultyDashboardPage.getAccountMenuUsername(),facultyName,"Account menu username mismatch!");

        facultyDashboardPage.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");	
    }    

    @Parameters("facultyName")
    @Test(dependsOnMethods = "facultyLoginCheck")
	public void finalTest(@Optional("autotestfac1") String facultyName) {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(facultyName,"faculty");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteUserFromElastic(facultyName,"faculty");

        softAssert.assertAll();
    }
}

