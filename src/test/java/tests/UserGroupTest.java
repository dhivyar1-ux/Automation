package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.UserGroup.CreateUserGroupPage;
import pages.Dashboard.Users.UserGroup.UserGroupPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class UserGroupTest extends BaseTest{
	//covers 1. Admin login 2. Create a new usergroup and verify the added details
		
	private UserGroupPage userGroupPage;
	private CreateUserGroupPage createUserGroupPage; 
    private LoginPage login;    
    private DashboardPage dashboard;
    private LoginModalPage loginModalPage;

    private PageFactory pf;    
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory();
		userGroupPage = pf.userGroupPage();
		createUserGroupPage = pf.createUserGroupPage();
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        loginModalPage = pf.loginModalPage();
    }

	public void verifyUserGroupDetailsInGrid() {
		
	   	String name = userGroupPage.getValueByLabel("Name");
	   	String description = userGroupPage.getValueByLabel("Description");
	   	String studentsList = userGroupPage.getValueByLabel("Student List");
		
        softAssert.assertEquals(name,DataStore.ug_groupname);
        softAssert.assertEquals(description,DataStore.ug_description);
		
		String result = studentsList.replaceAll("\\([^)]*\\)", "");

        softAssert.assertEquals(studentsList,result);
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);
    
	}

    @Test(dependsOnMethods = "userLogin", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateUserGroupAndVerifyDetails(
    		String groupname,
			String description, 
			String studentsList) {

    	dashboard.goToPage("usergroup");

    	userGroupPage.clickCreateUserGroup();

        // Create user group
    	createUserGroupPage.createUserGroup(groupname,
    			description, 
    			studentsList);   
		
		dashboard.goToPage("usergroup");

		userGroupPage.goToUserGroup(DataStore.ug_groupname);

        // VERIFY
        verifyUserGroupDetailsInGrid();
    }    

	@Test(dependsOnMethods = "CreateUserGroupAndVerifyDetails")
    public void verifyLogout() {

        dashboard.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

	@Test(dependsOnMethods = "verifyLogout")
	public void finalTest() {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(DataStore.ug_groupname,"user_group");     
        
        // 2. Delete record from Elasticsearch index
		System.out.println("DataStore.ug_groupname:"+DataStore.ug_groupname);
        ElasticSearchCleanupUtil.deleteUserFromElastic(DataStore.ug_groupname,"usergroup");

        softAssert.assertAll();
    }
}

