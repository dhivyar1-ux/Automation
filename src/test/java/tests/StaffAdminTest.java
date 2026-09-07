package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Administration.CreateDepartmentPage;
import pages.Dashboard.Administration.DepartmentPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.StaffAdmin.CreateStaffAdminPage;
import pages.Dashboard.Users.StaffAdmin.StaffAdminDashboardPage;
import pages.Dashboard.Users.StaffAdmin.StaffAdminPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class StaffAdminTest extends BaseTest{
	//covers 1. Admin login 2. Create a new staff admin and verify the added details 3. Added staff admin should be able to successfully login
	
	private StaffAdminPage usersStaffAdminPage;        
    private LoginPage login;    
    private DashboardPage dashboard;
    private CreateStaffAdminPage createStaffAdminPage;
	private StaffAdminDashboardPage staffAdminDashboardPage;
    private LoginModalPage loginModalPage;
    public DepartmentPage departmentPage;
    public CreateDepartmentPage createDepartmentPage;

    private PageFactory pf;    
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
        usersStaffAdminPage = pf.usersStaffAdminPage();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        createStaffAdminPage = pf.createStaffAdminPage();
        staffAdminDashboardPage = pf.staffAdminDashboardPage();
        loginModalPage = pf.loginModalPage();
        departmentPage = pf.departmentPage();
        createDepartmentPage = pf.createDepartmentPage();
    }

	 public void verifyStaffAdminDetailsInGrid() {
		
	   	String name = usersStaffAdminPage.getValueByLabel("Name");
	   	String department = usersStaffAdminPage.getValueByLabel("Department");
	   	String comments = usersStaffAdminPage.getValueByLabel("Comments");;

        softAssert.assertEquals(name,DataStore.sa_name);
        softAssert.assertEquals(department,DataStore.sa_department);
        softAssert.assertEquals(comments,DataStore.sa_comments);
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);        
        
	}

    @Test(dependsOnMethods = "userLogin",dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateStaffAdminAndVerifyDetails(
    		String username,
			String firstName, 
			String lastName, 
			String email, 
			String password,
			String skills,
			String comments,
			String department) {
        
    	dashboard.goToPage("staff-admin");

    	usersStaffAdminPage.clickCreateStaffAdmin();

        // Create staff admin
    	createStaffAdminPage.createStaffAdmin(username,
    			firstName, 
    			lastName, 
    			email, 
    			password,
    			skills,
    			comments,
    			department);
        
        //provided corresponding department already exists, 1) Click Department/Branches Tab 2) Click that department
        
        dashboard.goToPage("department");  

        departmentPage.goToEditDepartment(department);

        createDepartmentPage.selectDepartmentHead(DataStore.sa_name+" ("+DataStore.sa_username+")");

        createDepartmentPage.clickSave();

        dashboard.goToPage("staff-admin");       
        
		usersStaffAdminPage.goToStaffAdmin(DataStore.sa_name);

        // VERIFY
        verifyStaffAdminDetailsInGrid();
    }

    @Test(dependsOnMethods = "CreateStaffAdminAndVerifyDetails")
    public void verifyLogout() {
        dashboard.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters({"staffAdminName", "staffAdminPassword"})
    @Test(dependsOnMethods = "verifyLogout")
	public void staffAdminLoginCheck(@Optional("autotestsadmin1") String staffAdminName, @Optional("1234") String staffAdminPassword) {

        //Added Student login
        login.login(staffAdminName, staffAdminPassword,true);
		
        // Check #1 – Welcome banner
        softAssert.assertEquals(staffAdminDashboardPage.getWelcomeUsername(),staffAdminName,"Welcome banner username mismatch!");

        // Check #2 – Account menu
        softAssert.assertEquals(staffAdminDashboardPage.getAccountMenuUsername(),staffAdminName,"Account menu username mismatch!");

        staffAdminDashboardPage.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");		
    }

    @Parameters("staffAdminName")
    @Test(dependsOnMethods = "staffAdminLoginCheck")
	public void finalTest(@Optional("autotestsadmin1") String staffAdminName) {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(staffAdminName,"staffadmin");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteUserFromElastic(staffAdminName,"staffadmin");

        softAssert.assertAll();
    }
}

