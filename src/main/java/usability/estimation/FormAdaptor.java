package usability.estimation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import usability.estimation.utils.Screenshoter;
import ee.ttu.usability.domain.element.link.Form;

@Slf4j
public class FormAdaptor extends AbstractAdaptor {
	
	public EvaluationResult execute(Form form) throws IOException {
		if (form.getLabel() != null) {
			return evaluateExistanceOfLabal(form);
		} 
		return null;
	}

	// TODO finish 
	private EvaluationResult evaluateExistanceOfLabal(Form form) throws IOException {
		
		screenshot = screenshoter.makeScreenshot(driver);
		
		log.debug("Evaluation labels for Form");
		
		List<FailedElement> failedElements = new ArrayList<FailedElement>();

		log.debug("Evaluation of labels for text input");
		checkElementContainsLabel(failedElements, "//input[@type='text' or @type='password']", ElementType.TEXT_INPUT);
		
		log.debug("Evaluation of labels for select input");
		checkElementContainsLabel(failedElements, "//select", ElementType.SELECT_INPUT);
		
		
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.FORM);
		
		if (failedElements.size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		
		result.setFailedElements(failedElements);
		
		return result;
	}
	
	void checkElementContainsLabel(List<FailedElement> failedElements, String xpath, ElementType type) {
		List<WebElement> findElements = driver.findElements(By.xpath(xpath));
		for (WebElement el : findElements) {
			String id = el.getAttribute("id");
			String name = el.getAttribute("name");
			if (StringUtils.isNotBlank(id)) {
				List<WebElement> elemetns = driver.findElements(By.xpath("//label[@for='" + id + "' or @for='" + name + "']"));
				if (elemetns.size() == 0) {
					FailedElement failed = new FailedElement();
					failed.setType(type.name());
					failed.setText(id);
					failed.setDescription("No label is found for the element with id " + id);			
					File file = screenshoter.takeScreenshot(screenshot, el, driver);
					failed.setPathToElement(file.getName());
					failedElements.add(failed);
					
				}
			}
		}
	}
	
}
