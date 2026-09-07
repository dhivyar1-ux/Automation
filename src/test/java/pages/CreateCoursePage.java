package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class SubTopic {
    String name;          // "subtopic1 1"
    String feature;       // "subtopic1"
    String courseMaterial; // FULL string (spaces + special chars)

    SubTopic(String name, String feature, String courseMaterial) {
        this.name = name;
        this.feature = feature;
        this.courseMaterial = courseMaterial;
    }

    @Override
    public String toString() {
        return name + " " + feature;
    }
}

public class CreateCoursePage {

    WebDriver driver;

    public CreateCoursePage(WebDriver driver) {
        this.driver = driver;
    }

    public void createCourse(String type,
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
			String uploadeImageFile,
			String mcqTest,
			String programmingTest,
			String labConfig,
			String feedbackTemplate,
			String courseDescription,
			String hideCourse) {

    	By c_name = By.id("course-name");
        By c_duration = By.id("course-duration");
        By c_fees = By.id("course-feesTotal");
        By c_placement = By.id("course-placementAssistance");
        By c_coin = By.id("course-coin");
        By c_comments = By.id("course-comments");
        By c_validity = By.id("validityEnabled");
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\" + uploadeImageFile;
        By c_image = By.id("courseImage");
        By c_description = By.id("courseDescription");        
        By c_hideCourse = By.id("hideCourse");
                
        By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropDownTypeElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("course-type")));
        Select dropdownType = new Select(dropDownTypeElement);
        dropdownType.selectByVisibleText(type);     
    	
        driver.findElement(c_name).sendKeys(name);
        driver.findElement(c_duration).sendKeys(duration);
        driver.findElement(c_fees).sendKeys(fees);
        driver.findElement(c_placement).sendKeys(placementAssistance);
        driver.findElement(c_coin).sendKeys(coins);
        
        WebElement dropdownStatusElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("course-status")));
        Select dropdownStatus = new Select(dropdownStatusElement);
        dropdownStatus.selectByVisibleText(status); 
        
        driver.findElement(c_comments).sendKeys(comments);
        
        WebElement dropdownCourseLevelElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("course-level")));
        Select dropdownCourseLevel = new Select(dropdownCourseLevelElement);
        dropdownCourseLevel.selectByVisibleText(courseLevel); 
        
        WebElement dropdownCategoryElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("category")));
        Select dropdownCategory = new Select(dropdownCategoryElement);
        dropdownCategory.selectByVisibleText(category); 
        
        if (validity.equals("TRUE")) {
        	driver.findElement(c_validity).click();
        }
        
        driver.findElement(c_image).sendKeys(filePath);
        
        WebElement searchInputMcq = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='searchStudent']")));
            
        searchInputMcq.sendKeys("*");
        searchInputMcq.sendKeys(Keys.ENTER);
        
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='interaction-template'])[1]//child::button"))).click();        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+mcqTest+"')]"))).click();
        
        WebElement searchInputProg = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='searchProgramming']")));
        
        searchInputProg.sendKeys("*");
        searchInputProg.sendKeys(Keys.ENTER);
        
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='interaction-template'])[2]//child::button"))).click();        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+programmingTest+"')]"))).click();
        
        WebElement searchInputLab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='searchProgramming'])[2]")));
        
        searchInputLab.sendKeys("*");
        searchInputLab.sendKeys(Keys.ENTER);
        
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='interaction-template'])[1]//child::button"))).click();        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='menu']//*[contains(text(),'"+labConfig+"')]"))).click();
        
        WebElement dropdownFeedbackTemplateElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("feedbackTemplate")));
        Select dropdownFeedbackTemplate = new Select(dropdownFeedbackTemplateElement);
        dropdownFeedbackTemplate.selectByVisibleText(feedbackTemplate); 
        
        driver.findElement(c_description).sendKeys(courseDescription);
        
        if (hideCourse.equals("TRUE")) {
        	driver.findElement(c_hideCourse).click();
        }
        
        //wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        driver.findElement(backBtn).click();
    }
    
    public void createMaterial(String modules,String topics,String subTopics,String courseMaterials) throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
    	String[] moduleList = modules.split("\n");
    	String[] topicList = topics.split("\n");
    	String[] subTopicList = subTopics.split("\n");
    	String[] courseMaterialsList = courseMaterials.split("\n");
    	
    	List<String> moduleNames = new ArrayList<>();

    	for (String m : moduleList) {
    	    moduleNames.add(m.replace("-", " "));
    	}

    	Map<String, List<String>> moduleToTopics = new LinkedHashMap<>();

    	for (String t : topicList) {
    	    String[] parts = t.split(",");
    	    String module = parts[0];
    	    String topic = parts[1].replace("-", " ");

    	    moduleToTopics
    	        .computeIfAbsent(module, k -> new ArrayList<>())
    	        .add(topic);
    	}

    	List<List<String>> topicNames = new ArrayList<>(moduleToTopics.values());

    	Map<String, List<SubTopic>> subTopicMap = new LinkedHashMap<>();

    	for (String s : subTopicList) {

    	    String[] parts = s.split(",", 3);

    	    String module = parts[0];
    	    String topic = parts[1];

    	    String[] subParts = parts[2].split(" ", 2);
    	    String subName = subParts[0].replace("-", " ");
    	    String feature = subParts.length > 1 ? subParts[1] : "";

    	    String key = module + "," + topic;

    	    subTopicMap
    	            .computeIfAbsent(key, k -> new ArrayList<>())
    	            .add(new SubTopic(subName, feature, null));
    	}
    	
    	for (String c : courseMaterialsList) {

    		c = c.trim();

    	    // ✅ Skip empty or invalid lines
    	    if (c.isEmpty()) {
    	        continue;
    	    }

    	    String[] parts = c.split(",", 4);

    	    // ✅ Ensure minimum required fields exist
    	    if (parts.length < 4) {
    	        System.out.println("Skipping invalid content line: " + c);
    	        continue;
    	    }
    	    
    	    String module = parts[0].trim();
    	    String topic = parts[1].trim();
    	    String subtopic = parts[2].trim();
    	    String content = parts[3].trim(); // FULL TEXT

    	    String key = module + "," + topic;

    	    List<SubTopic> subs = subTopicMap.get(key);
    	    if (subs == null) continue;

    	    for (SubTopic st : subs) {
    	        if (st.name.startsWith(subtopic)) {
    	            st.courseMaterial = content;
    	        }
    	    }
    	}

    	List<List<List<SubTopic>>> subTopicNames = new ArrayList<>();

    	for (int i = 0; i < moduleNames.size(); i++) {

    	    String moduleKey = moduleNames.get(i).split(" ")[0];
    	    List<String> topicsForModule = topicNames.get(i);

    	    List<List<SubTopic>> moduleSubs = new ArrayList<>();

    	    for (String topic : topicsForModule) {

    	        String topicKey = topic.split(" ")[0];
    	        String mapKey = moduleKey + "," + topicKey;

    	        moduleSubs.add(
    	                new ArrayList<>(
    	                        subTopicMap.getOrDefault(mapKey, new ArrayList<>())
    	                )
    	        );
    	    }

    	    subTopicNames.add(moduleSubs);
    	}

    	System.out.println(moduleNames);
    	System.out.println(topicNames);
    	System.out.println(subTopicNames);    	
    	System.out.println(subTopicNames.get(0).get(0).get(0).courseMaterial);
    	
    	By submitBtn = By.xpath("//button[contains(normalize-space(),'Save')]");
        By backBtn = By.id("cancel-save");
         
        //adding modules from list
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div//h3[text()='Modules'])[1]//parent::div/following-sibling::div/child::a[contains(.,'Add Module')]"))).click();
    	for (int index=0;index < moduleNames.size();index++) {
    		String[] details = moduleNames.get(index).split(" ");
    		wait.until(ExpectedConditions.elementToBeClickable(By.id("module-name-"+index))).sendKeys(details[0]);
    		wait.until(ExpectedConditions.elementToBeClickable(By.id("module-duration-"+index))).sendKeys(details[1]);    		
    	}
    	//wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        driver.findElement(backBtn).click();        
        
        //add topics
        for (int index=0;index < topicNames.size();index++) {
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//div[@class='course-item']"
        					+ "//child::h6[contains(.,'"+moduleNames.get(index).split(" ")[0]+"')])[1]"
        					+ "//parent::div//following-sibling::div//child::a[contains(.,'Add Topic')]"))).click();
    		for (int tindex=0;tindex < topicNames.get(index).size();tindex++) {    			
    			String[] topicDetails = topicNames.get(index).get(tindex).split(" ");
    			wait.until(ExpectedConditions.elementToBeClickable(By.id("topic-name-"+tindex))).sendKeys(topicDetails[0]);
    			wait.until(ExpectedConditions.elementToBeClickable(By.id("topic-duration-"+tindex))).sendKeys(topicDetails[1]);
    		}
    		//wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
            driver.findElement(backBtn).click();
    	}                
        
        //add subtopics
        for (int index=0;index < subTopicNames.size();index++) {
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//div//child::div[@class='course-item']"
        					+ "//child::h6[contains(.,'"+moduleNames.get(index).split(" ")[0]+"')])[1]"))).click();
        	for (int tindex=0;tindex < subTopicNames.get(index).size();tindex++) {
        		if (!(subTopicNames.get(index).get(tindex).isEmpty())) {
        			WebElement element = wait.until(
        			    ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='course-topic']"
    							+ "//child::h6[contains(.,'"+topicNames.get(index).get(tindex).split(" ")[0]+"')]"
								+ "//parent::div//following-sibling::div//child::a[contains(.,'Add SubTopic')])[1]"))
        			);

        			((JavascriptExecutor) driver).executeScript(
        			    "arguments[0].scrollIntoView({block:'center'});", element
        			);

        			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        		
        			for (int sindex=0;sindex < subTopicNames.get(index).get(tindex).size();sindex++) {
        				String[] subtopicDetails = subTopicNames.get(index).get(tindex).get(sindex).name.split(" ");
        				wait.until(ExpectedConditions.elementToBeClickable(By.id("subtopic-name-"+sindex))).sendKeys(subtopicDetails[0]);
        				wait.until(ExpectedConditions.elementToBeClickable(By.id("subtopic-duration-"+sindex))).sendKeys(subtopicDetails[1]);
        				wait.until(ExpectedConditions.elementToBeClickable(By.id("subtopic-featuresCovered-"+sindex))).sendKeys(subTopicNames.get(index).get(tindex).get(sindex).feature);
        			}
        			//wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        			driver.findElement(backBtn).click();
        		}
    		}
    	}
        
      //add course materials
        for (int index=0;index < subTopicNames.size();index++) {
        	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//div//child::div[@class='course-item']"
        					+ "//child::h6[contains(.,'"+moduleNames.get(index).split(" ")[0]+"')])[1]")));
        	((JavascriptExecutor) driver).executeScript(
    			    "arguments[0].scrollIntoView({block:'center'});", element
    			);

    		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    			
        	for (int tindex=0;tindex < subTopicNames.get(index).size();tindex++) {
        		if (!(subTopicNames.get(index).get(tindex).isEmpty())) {
        			for (int sindex=0;sindex < subTopicNames.get(index).get(tindex).size();sindex++) {
        				element = wait.until(
        						ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='course-topic']"
        								+ "//child::h6[contains(.,'"+topicNames.get(index).get(tindex).split(" ")[0]+"')]"
        								+ "//following::div[@class='course-subtopic']"
        								+ "//child::h6[contains(.,'"+subTopicNames.get(index).get(tindex).get(sindex).name.split(" ")[0]+"')]"
        								+ "//parent::div//following-sibling::div//child::a[contains(.,'Add Course Materials')])[1]"))
        						);

        				((JavascriptExecutor) driver).executeScript(
        						"arguments[0].scrollIntoView({block:'center'});", element
        						);

        				wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        		
        				List<String> result = new ArrayList<>();

        				Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
        				Matcher matcher = pattern.matcher(subTopicNames.get(index).get(tindex).get(sindex).courseMaterial.split("-",2)[1]);

        				while (matcher.find()) {
        					if (matcher.group(1) != null) {
        						result.add(matcher.group(1)); // quoted text
        					} else {
        						result.add(matcher.group(2)); // normal token
        					}
        				}
        				String[] courseMatDetails = result.toArray(new String[0]);

        				wait.until(ExpectedConditions.elementToBeClickable(By.id("course-material-name"))).sendKeys(courseMatDetails[0]);
        				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.public-DraftEditor-content[contenteditable='true']"))).sendKeys(courseMatDetails[1]);
        				wait.until(ExpectedConditions.elementToBeClickable(By.id("course-material-youtubeLink"))).sendKeys(courseMatDetails[2]);
        				wait.until(ExpectedConditions.elementToBeClickable(By.id("course-material-githubLink"))).sendKeys(courseMatDetails[3]);
        			
        				//wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        				//Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        				//alert.accept();
        			
        				driver.findElement(backBtn).click();
        			}
        		}
    		}
    	}
    }
}

