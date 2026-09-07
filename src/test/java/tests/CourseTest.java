package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.CreateCoursePage;
import pages.LoginPage;
import pages.Dashboard.CoursesandBatches.CoursePage;
import pages.Dashboard.Users.DashboardPage;
import utils.DataStore;
import utils.ExcelUtils;

public class CourseTest extends BaseTest{
	//covers 1. Admin login 2. Create a new course and verify the added details 
	
	 DashboardPage dashboard = new DashboardPage(driver);
	 CoursePage coursePage = new CoursePage(driver);
	
	 public void verifyCourseDetailsInGrid(String courseName) {

		 dashboard.goToPage("course");
	       
		 Map<String, String> courseDetails = coursePage.getCourseDetails(courseName);
		 
		 SoftAssert softAssert = new SoftAssert();

         softAssert.assertEquals(courseDetails.get("name"),DataStore.get("coursename"),"CourseName check failed");
         softAssert.assertEquals(courseDetails.get("type"),DataStore.get("coursetype"),"CourseType check failed");
         softAssert.assertEquals(courseDetails.get("duration"),DataStore.get("courseduration"),"CourseDuration check failed");
         softAssert.assertEquals(courseDetails.get("placementAssistance"),DataStore.get("courseplacementAssistance"),"CoursePlacementAssistance check failed");
         softAssert.assertEquals(courseDetails.get("planApproved"),DataStore.get("courseplanApproved"),"CoursePlanApproved check failed");
         softAssert.assertEquals(courseDetails.get("status"),DataStore.get("coursestatus"),"CourseStatus check failed");
         softAssert.assertEquals(courseDetails.get("comments"),DataStore.get("coursecomments"),"CourseComments check failed");
         softAssert.assertEquals(courseDetails.get("validityDays"),DataStore.get("coursevalidity"),"CourseValidity check failed");
         softAssert.assertEquals(courseDetails.get("level"),DataStore.get("coursecourseLevel"),"CourseLevel check failed");
         softAssert.assertEquals(courseDetails.get("category"),DataStore.get("coursecategory"),"CourseCategory check failed");
         softAssert.assertEquals(courseDetails.get("fees"),DataStore.get("coursefees"),"CourseFees check failed");
         softAssert.assertEquals(courseDetails.get("labConfig"),DataStore.get("courselabConfig"),"CourseLabConfig check failed");
         softAssert.assertEquals(courseDetails.get("mcqTest"),DataStore.get("coursemcqTest"),"CourseMcqTest check failed");
         softAssert.assertEquals(courseDetails.get("progTest"),DataStore.get("courseprogrammingTest"),"CourseProgTest check failed");
         softAssert.assertEquals(courseDetails.get("feedbackTemplate"),DataStore.get("coursefeedbackTemplate"),"CourseFeedbackTemplate check failed");

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

    @Test(dataProvider = "excelData", dataProviderClass = ExcelUtils.class)
    public void CreateCourseAndVerifyDetails(
    		String type,
			String name, 
			String duration, 
			String fees, 
			String placementAssistance,
			String coins,
			String status,
			String comments,
			String courseLevel,
			String category,
			String validity,
			String uploadImageFile,
			String mcqTest,
			String programmingTest,
			String labConfig,
			String feedbackTemplate,
			String courseDescription,
			String hideCourse,
			String modules,
			String topics,
			String subTopics,
			String contents) throws InterruptedException {
            	
    	dashboard.goToPage("course");

    	coursePage.clickCreateCourse();

        // Create course
    	CreateCoursePage createCoursePage = new CreateCoursePage(driver);
    	createCoursePage.createCourse(type,
    			name, 
    			duration, 
    			fees, 
    			placementAssistance,
    			coins,
    			status,
    			comments,
    			courseLevel,
    			category,
    			validity,
    			uploadImageFile,
    			mcqTest,
    			programmingTest,
    			labConfig,
    			feedbackTemplate,
    			courseDescription,
    			hideCourse);

    	//store expected data
    	StringBuilder result = new StringBuilder();
	    String[] parts = type.toLowerCase().split("_");

	    for (String part : parts) {
	        result.append(Character.toUpperCase(part.charAt(0)))
	              .append(part.substring(1));
	    }
	    type = result.toString();
	    DataStore.put("courseType",type);
        
        DataStore.put("coursename",name);
        DataStore.put("courseduration",duration);
        DataStore.put("coursefees",fees);
        DataStore.put("courseplacementAssistance",placementAssistance);
        //when course is created, plan will not be approved
        DataStore.put("courseplanApproved","Not Approved");
        DataStore.put("coursecoins",coins);
        DataStore.put("coursestatus",status);
        DataStore.put("coursecomments",comments);
        DataStore.put("coursecourseLevel",courseLevel);
        DataStore.put("coursecategory",category);
        DataStore.put("coursevalidity",validity);
        DataStore.put("courseuploadImageFile",uploadImageFile);
        DataStore.put("coursemcqTest",mcqTest);        
		DataStore.put("courseprogrammingTest",programmingTest);
		DataStore.put("courselabConfig",labConfig);
		DataStore.put("coursefeedbackTemplate",feedbackTemplate);
		DataStore.put("coursecourseDescription",courseDescription);
       
        verifyCourseDetailsInGrid(name);
        
        createCoursePage.createMaterial(modules,topics,subTopics,contents);
        
        dashboard.logout();
    }    
}
