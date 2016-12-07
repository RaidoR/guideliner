package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ee.ttu.usability.domain.attribute.AlternativeText;
import jevg.ee.ttu.dataproperty.Unit;
import jevg.ee.ttu.dataproperty.UnitAction;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
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
		} else if (link.getAlternativeText() != null) {
			return evaluateAlternativeText(link.getAlternativeText());
		}
		return null;
	}

	private EvaluationResult evaluateAlternativeText(AlternativeText alternativeText) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);

		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> links = driver.findElements(By.tagName("a"));

		if (alternativeText.getProhibitedWords() != null && alternativeText.getProhibitedWords().getUnit() != null) {
			links.forEach(l -> {
				String prohibitedWord = null;
				Unit unit = alternativeText.getProhibitedWords().getUnit();
				UnitAction action = alternativeText.getProhibitedWords().getUnitAction();
				if (unit == Unit.TEXT) {
					prohibitedWord =  l.getAttribute("innerHTML");
					if (action.DO_NOT_FOLLOW == action && StringUtils.isNotEmpty(prohibitedWord) && prohibitedWord.equals(l.getAttribute("alt"))) {
						File file = screenshoter.takeScreenshot(screenshot, l, driver);
						result.getFailedElements().add(prepareFailedElement("UI Page", "Elements with alt attribute", "The word " + prohibitedWord + "  for alternative text is not allowed as it duplicates the link text" , file));
					}
				}
			});
		}
		return setSuccessFlag(result);
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
				 System.out.println(el.getText());
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
