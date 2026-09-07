package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginModalPage;
import pages.LoginPage;
import pages.Dashboard.Users.DashboardPage;
import pages.Dashboard.Users.Student.CreateStudentPage;
import pages.Dashboard.Users.Student.StudentDashboardPage;
import pages.Dashboard.Users.Student.StudentsPage;
import utils.DataStore;
import utils.ElasticSearchCleanupUtil;
import utils.ExcelUtils;
import utils.MongoDbUtil;
import utils.PageFactory;

public class StudentTest extends BaseTest{
	//covers 1. Admin login 2. Create a new student and verify the added details 3. Added student should be able to successfully login
    // page objects – **declare only**, initialise later
    private LoginPage login;
    private DashboardPage dashboard;
    private StudentsPage dashboardStudentsPage;
    private StudentDashboardPage studentDashboardPage;
    private LoginModalPage loginModalPage;

    private SoftAssert softAssert = new SoftAssert();
    private PageFactory pf;

    @BeforeClass
    public void setUpPages() {
        pf = new PageFactory(driver);
        login = pf.loginPage();
        dashboard = pf.dashboardPage();
        dashboardStudentsPage = pf.dashboardStudentsPage();
        studentDashboardPage = pf.studentDashboardPage();
        loginModalPage = pf.loginModalPage();
    }

	public void verifyStudentDetailsInGrid() {
		
	   	String name = dashboardStudentsPage.getValueByLabel("Name");
	   	String contactno = dashboardStudentsPage.getValueByLabel("Contact Number");
	   	String location = dashboardStudentsPage.getValueByLabel("Location");
	   	String comments = dashboardStudentsPage.getValueByLabel("Comments");
	   	String level = dashboardStudentsPage.getValueByLabel("Level");
	   	String category = dashboardStudentsPage.getValueByLabel("Student Or Employee");
	   	String collegeName = dashboardStudentsPage.getValueByLabel("College Name");
	   	String collegestartyear = dashboardStudentsPage.getValueByLabel("College Start Year");
	   	String collegeendyear = dashboardStudentsPage.getValueByLabel("College End Year");
	   	String departmentname = dashboardStudentsPage.getValueByLabel("Department Name");
	   	String address = dashboardStudentsPage.getValueByLabel("Address");
	   	String guardianname = dashboardStudentsPage.getValueByLabel("Parent Or Guardian Name");
	   	String guardiannumber = dashboardStudentsPage.getValueByLabel("Parent Or Guardian Number");
	   	String currentcity = dashboardStudentsPage.getValueByLabel("Current City");

        softAssert.assertEquals(name,DataStore.s_name);
        softAssert.assertEquals(contactno,DataStore.s_contactNumber);
        softAssert.assertEquals(location,DataStore.s_location);
        softAssert.assertEquals(comments,DataStore.s_comments);
        softAssert.assertEquals(level,DataStore.s_courseLevel);
        softAssert.assertEquals(category,DataStore.s_category);
        softAssert.assertEquals(collegeName,DataStore.s_collegeName);
        softAssert.assertEquals(collegestartyear,DataStore.s_collegeStartYear);
        softAssert.assertEquals(collegeendyear,DataStore.s_collegeEndYear);
        softAssert.assertEquals(departmentname,DataStore.s_department);
        softAssert.assertEquals(address,DataStore.s_address);
        softAssert.assertEquals(guardianname,DataStore.s_parentOrGuardianName);
        softAssert.assertEquals(guardiannumber,DataStore.s_parentOrGuardianContact);
        softAssert.assertEquals(currentcity,DataStore.s_currentCity);
    }
	 
	@Parameters({"username", "password"})
	@Test
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

        login.login(user, pwd);
	}

    @Test(dependsOnMethods = "userLogin", dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateStudentAndVerifyDetails(
    		String username,
			String firstName, 
			String lastName, 
			String email, 
			String contactNumber,
			String location,
			String comments,
			String category,
			String collegeName,
			String collegeStartYear,
			String collegeEndYear,            
			String department,
			String companyName,
			String role,
			String address,
			String parentOrGuardianName,
			String parentOrGuardianContact,
			String currentCity,
			String courseLevel,
            String password) throws InterruptedException {
        
    	dashboard.goToPage("student");

    	dashboardStudentsPage.clickCreateStudent();

        // Create student
        CreateStudentPage createStudent = new CreateStudentPage(driver);
        createStudent.createStudent(username,
    			firstName, 
    			lastName, 
    			email, 
    			password,
    			contactNumber,
    			location,
    			comments,
    			category,
    			collegeName,
    			collegeStartYear,
    			collegeEndYear,
                department,
    			companyName,
    			role,
    			address,
    			parentOrGuardianName,
    			parentOrGuardianContact,
    			currentCity,
    			courseLevel);
        
        dashboard.goToPage("student");
       
        dashboardStudentsPage.goToStudent(DataStore.s_name);

        // VERIFY
        verifyStudentDetailsInGrid();
    }

    @Test(dependsOnMethods = "CreateStudentAndVerifyDetails")
    public void verifyLogout() {

        dashboard.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters({"studentName", "studentPassword"})
    @Test(dependsOnMethods = "verifyLogout")
	public void studentLoginCheck(@Optional("autoteststud1") String studentName, @Optional("1234") String studentPassword) {

        //Added Student login
        login.login(studentName, studentPassword,true);

        // Check #1 – Welcome banner
        softAssert.assertEquals(studentDashboardPage.getWelcomeUsername(),studentName,"Welcome banner username mismatch!");

        // Check #2 – Account menu
        softAssert.assertEquals(studentDashboardPage.getAccountMenuUsername(),studentName,"Account menu username mismatch!");

        //Store ONLY after assertAll() passes
        //DataStore.loggedInUsername = username;
        
        studentDashboardPage.logout();

        softAssert.assertEquals(loginModalPage.getSignInText(), "Sign in","Sign in text not displayed after logout");
    }

    @Parameters("studentName")
    @Test(dependsOnMethods = "studentLoginCheck")
	public void finalTest(@Optional("autoteststud1") String studentName) {
        //cleanups happen here

        // 1. Delete record from MongoDB
        MongoDbUtil.deleteUserByName(studentName,"student");     
        
        // 2. Delete record from Elasticsearch index
        ElasticSearchCleanupUtil.deleteUserFromElastic(studentName,"student");

        softAssert.assertAll();
    }
}
