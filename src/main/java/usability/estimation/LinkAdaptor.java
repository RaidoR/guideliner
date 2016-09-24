package usability.estimation;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;
import usability.estimation.utils.ContrastEstimator;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.page.UIPage;

@Slf4j
public class LinkAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(Link link) throws IOException {
		if (link.getContrast() != null && link.getContrast().getContrast() != null) {
			return evaluateContrast(link);
		} else if (link.getContentLength() != null) {
			return evaluateContentLength(link);
		}
		return null;
	}

	public EvaluationResult evaluateContentLength(Link page) {
		log.debug("Evaluation evaluateContentLength for Link");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);
		result.setResult(ResultType.SUCCESS);
		List<WebElement> findElements = driver.findElements(By.tagName("a"));
		for (WebElement el : findElements) {
			Integer amountOfUnits = getAmountOfUnit(el.getText(), page.getUnit());			
			 if (amountOfUnits > page.getContentLength()) {
				 result.setResult(ResultType.FAIL);
				 result.setDescription("Amount of " + page.getUnit() + " was " + amountOfUnits);
			 }
		}
		return result;
	}
	
	private EvaluationResult evaluateContrast(Link link) throws IOException {
		ContrastEstimator estimator = new ContrastEstimator();
		List<WebElement> allLinks = getAllLinks(driver);
		return estimator.estimate(allLinks, driver);
	}
	
	public List<WebElement> getAllLinks(WebDriver driver) {
		return driver.findElements(By.tagName("a"));
	}
	
}
