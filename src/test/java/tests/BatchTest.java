package tests;

import java.util.Arrays;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.LoginPage;
import pages.Dashboard.CoursesandBatches.BatchPage;
import pages.Dashboard.CoursesandBatches.CoursePage;
import pages.Dashboard.Users.DashboardPage;
import utils.DataStore;
import utils.ExcelUtils;

public class BatchTest extends BaseTest{
	//covers 1. Admin login 2. Create a new batch and verify the added details 
	
	 String normalize(String s) {
	     String[] arr = s.split(",");
	     Arrays.sort(arr);
	     return String.join(",", arr);
	 }
	
	 public void verifyBatchDetailsInGrid(String batchName) {

		 BatchPage batchPage = new BatchPage(driver);
		 batchPage.goToBatch(batchName);
				
	   	String type = batchPage.getValueByLabel("Type");	    
	   	String name = batchPage.getValueByLabel("Batch Name");
	   	String timeSlot = batchPage.getValueByLabel("Time Slot");
	   	String startDate = batchPage.getValueByLabel("Start Date");
	   	String endDate = batchPage.getValueByLabel("End Date");
	   	String currentLag = batchPage.getValueByLabel("Current Lag");
	   	String comments = batchPage.getValueByLabel("Comments");	   
	   	String course = batchPage.getValueByLabel("Course");
	   	String meetingLink = batchPage.getValueByLabel("Meeting Link");
	   	String studentsList = batchPage.getValueByLabel("Students");
	   	String facultiesList = batchPage.getValueByLabel("Faculty");
	   	String overallMCQTest = batchPage.getValueByLabel("overallMCQTest");
	   	String overallProgTest = batchPage.getValueByLabel("overallProgTest");
	   	
        SoftAssert softAssert = new SoftAssert();

        //Batch Details
        softAssert.assertEquals(type,(String) DataStore.get("batchType"),"BatchType check failed");
        softAssert.assertEquals(name,(String) DataStore.get("batchName"),"BatchName check failed");
        softAssert.assertEquals(timeSlot,(String) DataStore.get("batchTimeSlot"),"BatchTimeSlot check failed");
        softAssert.assertEquals(startDate,(String) DataStore.get("batchStartDate"),"BatchStartDate check failed");
        softAssert.assertEquals(endDate,(String) DataStore.get("batchEndDate"),"BatchEndDate check failed");
        softAssert.assertEquals(currentLag,(String) DataStore.get("batchCurrentLag"),"BatchCurrentLag check failed");
        softAssert.assertEquals(comments,(String) DataStore.get("batchComments"),"BatchComments check failed");
        softAssert.assertEquals(course,(String) DataStore.get("batchCourse"),"BatchCourse check failed");
        softAssert.assertEquals(meetingLink,(String) DataStore.get("batchMeetingLink"),"BatchMeetingLink check failed");
        softAssert.assertEquals(normalize(studentsList),normalize((String) DataStore.get("batchStudentList")),"BatchStudentList check failed");
        softAssert.assertEquals(normalize(facultiesList),normalize((String) DataStore.get("batchfacultyList")),"BatchfacultyList check failed");
        softAssert.assertEquals(overallMCQTest,(String) DataStore.get("coursemcqTest"),"BatchOverallMCQTest check failed");
        softAssert.assertEquals(overallProgTest,(String) DataStore.get("courseprogrammingTest"),"BatchOverallProgTest check failed");        
        
        softAssert.assertAll();
    }
	 
	@Parameters({"username", "password"})
	@BeforeClass(alwaysRun = true)
	public void userLogin(@Optional("admin") String user, @Optional("admin") String pwd) {

    	LoginPage login = new LoginPage(driver);
        login.login(user, pwd);
        
		DataStore.loggedInUsername = user;
		DataStore.loggedInPassword = pwd;
	}

	@AfterClass
	public void cleanup() {
	    DataStore.clear();
	}

    @Test(dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateBatchAndVerifyDetails(
    		String type,
			String name, 
			String startTime, 
			String endTime, 
			String currentLag,
			String status,
			String comments,
			String meetingLink,
			String facultyAccess,
			String category,
			String studentList,
			String facultyList,
			String course) throws InterruptedException {
        
    	DashboardPage dashboard = new DashboardPage(driver);
/*    	dashboard.goToPage("batch");

    	BatchPage batchPage = new BatchPage(driver);
    	batchPage.clickCreateBatch();

        // Create batch
    	CreateBatchPage createBatchPage = new CreateBatchPage(driver);
    	createBatchPage.createBatch(type,
    			name, 
    			startTime, 
    			endTime, 
    			currentLag,
    			status,
    			comments,
    			meetingLink,
    			facultyAccess,
    			category,
    			studentList,
    			facultyList,
    			course);
*/
    	//get course details
    	dashboard.goToPage("course");
    	
    	CoursePage coursePage = new CoursePage(driver);
    	Map<String, String> courseDetails = coursePage.getCourseDetails(course);
		 
    	//store expected data
    	DataStore.put("batchType", type);
    	DataStore.put("batchName", name);
    	DataStore.put("batchTimeSlot", startTime+" - "+endTime);
    	DataStore.put("batchStartDate","");
    	DataStore.put("batchEndDate", "");
    	DataStore.put("batchCurrentLag", currentLag);
    	DataStore.put("batchStatus", status);
    	DataStore.put("batchComments", comments);
    	DataStore.put("batchMeetingLink", meetingLink);
    	DataStore.put("batchFacultyAccess", facultyAccess);
    	DataStore.put("batchCategory", category);
    	DataStore.put("batchStudentList", studentList);
    	DataStore.put("batchfacultyList", facultyList);
    	DataStore.put("batchCourse", course);
    	DataStore.put("coursemcqTest", courseDetails.get("mcqTest"));
    	DataStore.put("courseprogrammingTest", courseDetails.get("progTest"));        
    	
        dashboard.goToPage("batch");
       
        verifyBatchDetailsInGrid(name);
        
        dashboard.logout();
    }    
}
