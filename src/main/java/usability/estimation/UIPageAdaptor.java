package usability.estimation;

import java.util.List;
import java.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;
import ee.ttu.usability.domain.page.UIPage;

@Slf4j
public class UIPageAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(UIPage page) {
		if (page.getContentLength() != null) {
			return evaluateContentLength(page);
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
				 result.setElementType(ElementType.PAGE);
				 result.setResult(ResultType.FAIL);
				 result.setDescription("Amount of " + page.getUnit() + " was " + amountOfUnits);
			 }
		}
		return result;
	}
	
}
