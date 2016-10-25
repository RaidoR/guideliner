package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import ee.ttu.usability.domain.page.UIPage;

@Slf4j
public class UIPageAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(UIPage page) throws IOException {
		if (page.getContentLength() != null) {
			return evaluateContentLength(page);
		}
		if (page.getHorizontalScroll() != null) {
			return evaluateHorizontalScroll(page);
		}
		if (page.getVerticalScroll() != null) {
			return evaluateVerticalScrolling(page);
		}
		if (page.getHeight() != null) {
			return evaluateHeight(page);
		}
		if (page.getLayout() != null) {
			return evaluateLayout(page);
		}
		if (page.getLoadTime() != null) {
			return evaluateLoadTime(page);
		}
		if (page.getProhibitedWords() != null) {
			return evaluateProhibitedWords(page);
		}
		if (page.getText() != null) {
			return evaluateText(page);
		}
		return null;
	}

	public List<WebElement> getBoldText(WebDriver driver) {
		List<WebElement> bolds = driver.findElements(By.tagName("b"));
		List<WebElement> strongs = driver.findElements(By.tagName("strong"));
		bolds.addAll(strongs);
		return bolds;
	}
	
	private EvaluationResult evaluateLoadTime(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		long start = System.currentTimeMillis();
	
		driver.get(page.getUrl());
	
		WebElement ele = driver.findElement(By.tagName("body"));
		long finish = System.currentTimeMillis();
		long totalTime = finish - start; 
		System.out.println("Total Time for page load - "+totalTime); 
		 
		if (totalTime > page.getLoadTime().getContentLength()) {
			 result.setResult(ResultType.FAIL);
			 result.setDescription("Load time exceeded the expected. Load time : " + totalTime);
		}
		return result;
	}

	public EvaluationResult evaluateContentLength(UIPage page) {
		log.debug("Evaluation evaluateContentLength for UIPage");
		EvaluationResult result = new EvaluationResult();
		List<WebElement> findElements = driver.findElements(By.tagName("body"));
		for (WebElement el : findElements) {
			Integer amountOfUnits = getAmountOfUnit(el.getText(), page.getUnit());			
			 if (amountOfUnits > page.getContentLength()) {
				 String description = "Amount of " + page.getUnit() + " was " + amountOfUnits;
				 result.getFailedElements().add(prepareFailedElement("UI Page", "Home page", description, NO_IMAGE));
					
				 result.setElementType(ElementType.PAGE);
				 result.setResult(ResultType.FAIL);
				 result.setDescription(description);
			 }
		}
		return result;
	}

	private EvaluationResult evaluateHorizontalScroll(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(0, 10000);");
		
		Long value = (Long) executor.executeScript("return window.scrollY;");

		if (value > page.getHorizontalScroll().getValue()) {
			 result.setElementType(ElementType.PAGE);
			 result.setResult(ResultType.FAIL);
			 result.setDescription("Horizontal scrol value is bigger then defined. Real value is : " + value);
		}
		return result;
	}
	

	private EvaluationResult evaluateHeight(UIPage page) throws IOException {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);

		screenshot = screenshoter.makeScreenshot(driver);
		
		if (screenshot.getHeight() > page.getHeight().getContentLength()) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Page horizontal length value is bigger then defined. Expected value is " + page.getHeight().getContentLength() + 
					 "Actual value is : " + screenshot.getHeight());		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		return result;
	}

	private EvaluationResult evaluateVerticalScrolling(UIPage page) {
		System.out.println("vertical scrolling");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(10000, 0);");
		
		Long value = (Long) executor.executeScript("return window.scrollX;");

		if (value > page.getVerticalScroll().getValue()) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Horizontal scrol value is bigger then defined. Real value is : " + value);		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		return result;
	}

	private EvaluationResult evaluateLayout(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		driver.manage().window().setSize(new Dimension(1100, 768));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(1000, 0);");
		Long value = (Long) executor.executeScript("return window.scrollX;");
		System.out.println(value);
		if (value != 0) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Layout is not compatible with changing the size of brawser.");		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		
		return result;
	}
	
	private EvaluationResult evaluateProhibitedWords(UIPage page) {
		log.debug("Evaluation evaluateProhibitedWords for UIPage");
		EvaluationResult result = new EvaluationResult();
		List<WebElement> findElements = driver.findElements(By.tagName("body"));
		for (WebElement el : findElements) {
			String entireText = el.getText();
			for (String element : page.getProhibitedWords().getValue().split(",")) {
				if (entireText.contains(element)) {
					 result.getFailedElements().add(prepareFailedElement("UI Page", "Whole page", "The word " + element + "is not allowed" , NO_IMAGE));
					 result.setElementType(ElementType.PAGE);
				}
			}
		}
		return setSuccessFlag(result);
	}
	
	private EvaluationResult evaluateText(UIPage page) throws IOException {
		log.debug("Evaluating evaluateText");
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		List<WebElement> texts = getBoldText(driver);
		
		for(WebElement ele : texts)
		{
			if (StringUtils.isEmpty(ele.getText())) {
				continue;
			}
			if (ele.getText().length() > page.getText().getContentLength()) {
				 File file = screenshoter.takeScreenshot(screenshot, ele, driver);
				 String description = "Amount of Bold text was " + ele.getText().length();
				 result.getFailedElements().add(prepareFailedElement("UI Page", "UI Page", description, file.getName()));
			}
			

		}
		return setSuccessFlag(result);
	}

}
