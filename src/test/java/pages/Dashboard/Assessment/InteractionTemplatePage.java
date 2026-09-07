package pages.Dashboard.Assessment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
import utils.DataStore;

public class InteractionTemplatePage extends BasePage {
    public InteractionTemplatePage() { super(); }

	public void clickCreateInteractionTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/interaction-template/new']"))).click();
    }
    
    public boolean isInteractionTemplateAvailable(String interactionTemplateName) {
    	
    	WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='search'][1]")));
    	searchInput.sendKeys(interactionTemplateName);
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='search'][1]//following::button)[1]"))).click();
    	
		// Wait until either table or alert appears
		wait.until(ExpectedConditions.or(
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='table-responsive']//table")),
		        ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'alert-warning')]"))
		));
		
		List<WebElement> noInteractionTemplateMsg = driver.findElements(
		        By.xpath("//div[@class='table-responsive']//div[contains(@class,'alert-warning')]")
		);
		
		return noInteractionTemplateMsg.isEmpty();
    }
    
	public void getSpecificInteractionTemplate(String interactionTemplateName, String gameType) {
    	
    	if (isInteractionTemplateAvailable(interactionTemplateName)) {
    		//collect the questions,answers,correct choice
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//div[@class='table-responsive']//tbody//tr//td//a[contains(text(),'"+interactionTemplateName+"')]//following::td[@class='text-end'])[1]//child::a//child::span[text()='View']"))).click();
        	int noOfQuestions = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//strong[contains(normalize-space(.),'Number of Questions')]/ancestor::div/following-sibling::div//div[@class='valueDesign1']"))).getText());
        	DataStore.put("noOfQuestions", noOfQuestions);
        	DataStore.put("questionset", getQA(gameType));
        	Map<String,String[]> quesDetails = (Map<String,String[]>)DataStore.get("questionset"); 
        	quesDetails.forEach((key, value) -> {
        	    System.out.println("Question: " + key);
        	    System.out.println("Choice: " + value[0]);
        	    System.out.println("Answer: " + value[1]);
        	    System.out.println("---");
        	});
        	System.out.println("noOfQuestions:"+noOfQuestions);
    	}
    }
    
	public void previewSpecificInteractionTemplate(String interactionTemplateName, String gameType) {
    	
    	if (isInteractionTemplateAvailable(interactionTemplateName)) {
    		
        	wait.until(ExpectedConditions.elementToBeClickable(
        			By.xpath("(//a[contains(text(),'"+interactionTemplateName+"')]/following::td[@class='text-end']/child::div)[1]/child::button[text()='Preview']"))).click();
        	Map<String,String[]> quesDetails =  (Map<String,String[]>)DataStore.get("questionset");
        	if (gameType.equals("FruitNinjaMcq")) {
        		for (int index = 0;index < (int)DataStore.get("noOfQuestions");index++) {
        		    System.out.println(driver.findElement(By.xpath("//div[contains(@class,'progress-bar')]")).getText());
        			String displayedQues = wait.until(ExpectedConditions.visibilityOfElementLocated(
        					By.xpath("(//div[contains(@class,'question-area')]//h5)[1]"))).getText();
        			
        			String[] answer = quesDetails.get(displayedQues)[1].split(",");
        			System.out.println("ques"+displayedQues);
        			System.out.println("ans");
        			for (int i = 0;i < answer.length;i++) {
        				System.out.println(answer[i]);
        				wait.until(ExpectedConditions.visibilityOfElementLocated(
        						By.xpath("//div[@class='choice-inner2' and text()='"+answer[i]+"']")));
        				wait.until(ExpectedConditions.elementToBeClickable(
        						By.xpath("//div[@class='choice-inner2' and text()='"+answer[i]+"']"))).click();
        				//Thread.sleep(2000);
        			}
        		}
        	} else {
        		for (int index = 0;index < (int)DataStore.get("noOfQuestions");index++) {
        			String displayedQues = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='question-text']"))).getText();
        			System.out.println("ddddd"+displayedQues+"and"+quesDetails.get(displayedQues)[1]);
        			
        			wait.until(ExpectedConditions.elementToBeClickable(
        					By.xpath("//div[@class='option-text' and text()='"+quesDetails.get(displayedQues)[1]+"']"))).click();
        			//Thread.sleep(2000);
        		}
        	}
        	
    	}
    }
   
	public Map<String,String[]> getQA(String gameType) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(
    			By.xpath("//div[@class='questionsContainer']")));
    	//taking list of questions
    	List<WebElement> qa = driver.findElements(By.xpath("(//div[@class='questionsContainer'])[1]/child::div"));
    	Map<String, String[]> quizList = new HashMap<>();
    	
    	for (int i = 0;i < qa.size();i++) {
    		String ques = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='questionText'])["+(i+1)+"]"))).getText().split(":", 2)[1].trim();
    		
    		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='questionText'])["+(i+1)+"]"))).click();
    		
    		String choice = wait.until(ExpectedConditions.visibilityOfElementLocated(
        			By.xpath("((//div[@class='questionsContainer'])[1]/child::div)["+(i+1)+"]//p[strong[contains(text(),'Correct Choice')]]"))).getText().split(":", 2)[1].trim();
    		
    		String answer = "";
    		if (gameType.equals("FruitNinjaMcq")) {
    			for (int j = 1;j<=4;j++) {
    				if (j != Integer.parseInt(choice)) {
    					answer += wait.until(ExpectedConditions.visibilityOfElementLocated(
    							By.xpath("((//div[@class='questionsContainer'])[1]/child::div)["+(i+1)+"]//p[strong[contains(text(),'Choice "+j+"')]]/div"))).getText()+",";
    				}
    			}
    			answer = answer.substring(0, answer.length()-1);
    		} else {
    			answer += wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("((//div[@class='questionsContainer'])[1]/child::div)["+(i+1)+"]//p[strong[contains(text(),'Choice "+choice+"')]]/div"))).getText();
    		}
    		quizList.put(ques,new String[]{choice,answer});
    	}
    	return quizList;
    }
    
    public String getValueByLabel(String label) {
    	if (label.equals("Interaction Templates") || label.equals("Practice Task")) {
    		By content = By.xpath("(//b[text()='"+ label +"']/parent::div/following-sibling::div)[1]//child::a");
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	} else {
    		By content = By.xpath("//b[text()='" + label + "']/parent::div/following-sibling::div[1]/span");
    		return wait.until(ExpectedConditions.visibilityOfElementLocated(content)).getText();
    	}
    }
    
}


