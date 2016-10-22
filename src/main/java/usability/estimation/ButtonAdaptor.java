package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import ee.ttu.usability.domain.element.link.Button;

public class ButtonAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(Button el) throws IOException {
		if (el.getAlternativeText() != null) {
			return evaluateAlternativeText(el);
		} 
		return null;
	}

	private EvaluationResult evaluateAlternativeText(Button el) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		
		List<WebElement> imgs = driver.findElements(By.xpath("//input[@type='image']"));
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.BUTTON);
		
		for (WebElement elem : imgs) {
			try {
				String attribute = elem.getAttribute("alt");
				if (StringUtils.isBlank(attribute)) {
					FailedElement failed = new FailedElement();
					failed.setType(ElementType.IMAGE.name());
					failed.setText("Button");
					failed.setDescription("Button does not have alternative text");		
					File file = screenshoter.takeScreenshot(screenshot, elem, driver);
					failed.setPathToElement(file.getName());
					failedElements.add(failed);
			}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		result.setFailedElements(failedElements);
		if (failedElements.size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		
		return result;
	}

	
}
