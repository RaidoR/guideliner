package usability.estimation;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
		if (page.getHeight() != null) {
			return evaluateHeight(page);
		}
		return null;
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
	
}
