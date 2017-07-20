package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.ttu.usability.domain.attribute.AlternativeText;
import jevg.ee.ttu.dataproperty.Unit;
import jevg.ee.ttu.dataproperty.UnitAction;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;
import usability.estimation.utils.ContrastEstimator;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.page.UIPage;
import usability.estimation.utils.Screenshoter;

@Slf4j
@Service("LinkAdaptor")
public class LinkAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(Link link) throws IOException {
		if (link.getContrast() != null && link.getContrast().getContrast() != null) {
			return evaluateContrast(link);
		} else if (link.getContentLength() != null) {
			return evaluateContentLength(link);
		} else if (link.getAlternativeText() != null) {
			return evaluateAlternativeText(link.getAlternativeText());
		} else if (link.getWidth() != null && link.getWidth().getContentLength() != null) {
			return evaluateWidth(link);
		}  else if (link.getHeight() != null && link.getHeight().getContentLength() != null) {
			return evaluateHeight(link);
		}  else if (link.getDistance() != null && link.getDistance().getContentLength() != null && link.getDistance().getDistanceType() != null) {
			return evaluateDistance(link);
		} else if (link.getColor() != null && link.getColor().getIsSame() != null && link.getIsVisited() != null) {
			return evaluateVisitedColorScheme(link);
		} else if (link.getColor() != null && link.getColor().getIsSame() != null) {
			return evaluateSameColor(link);
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

	public EvaluationResult evaluateContentLength(Link page) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		log.debug("Evaluation evaluateContentLength for Link");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);
		result.setResult(ResultType.SUCCESS);
		List<WebElement> findElements = driver.findElements(By.tagName("a"));
		for (WebElement el : findElements) {
			Integer amountOfUnits = getAmountOfUnit(el.getText(), page.getUnit());			
			 if (amountOfUnits > page.getContentLength()) {
				 File file = screenshoter.takeScreenshot(screenshot, el, driver);
				 result.getFailedElements().add(prepareFailedElement(
				 		ElementType.LINK.name(), el.getText(),"Amount of " + page.getUnit() + " was " + amountOfUnits , file));
				 result.setResult(ResultType.FAIL);
			 }
		}
		return setSuccessFlag(result);
	}
	
	private EvaluationResult evaluateContrast(Link link) throws IOException {
		ContrastEstimator estimator = new ContrastEstimator();
		List<WebElement> allLinks = getAllLinks(driver);
		return estimator.estimate(allLinks, driver);
	}

	private EvaluationResult evaluateWidth(Link link) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> webLinks = getAllLinks(driver);
		for (WebElement webLink : webLinks) {
			// for some reason some links are narrow
			if (webLink.getSize().getWidth() == 0) {
				continue;
			}

			if (link.getWidth().getContentLength() > webLink.getSize().getWidth()) {
				File file = screenshoter.takeScreenshot(screenshot, webLink, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.LINK.name(), webLink.getText(),"The width of the link is smaller then expected. Expected minimum: "
								+ link.getWidth().getContentLength() + " actual: " + webLink.getSize().getWidth() , file));
			}
		}

		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateHeight(Link link) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> webLinks = getAllLinks(driver);
		for (WebElement webLink : webLinks) {
			// for some reason some links are narrow
			if (webLink.getSize().getHeight() == 0) {
				continue;
			}

			if (link.getHeight().getContentLength() > webLink.getSize().getHeight()) {
				File file = screenshoter.takeScreenshot(screenshot, webLink, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.LINK.name(), webLink.getText(),"The width of the link is smaller then expected. Expected minimum: "
								+ link.getHeight().getContentLength() + " actual: " + webLink.getSize().getHeight() , file));
			}
		}

		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateDistance(Link link) {
		// todo implement
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);



		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateSameColor(Link link) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> webLinks = getAllLinks(driver);

		String linkColor = null;
		for (WebElement webLink : webLinks) {
			if (webLink.getText() == null || webLink.getText().length() == 0) {
				continue;
			}
			String color = webLink.getCssValue("color");

			System.out.println(linkColor);
			System.out.println(color);

			if (linkColor == null) {
				linkColor = color;
				continue;
			}

			if (!linkColor.equals(color)) {
				File file = screenshoter.takeScreenshot(screenshot, webLink, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.LINK.name(), webLink.getText(),"The link has different color" , file));
			}
		}

		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateVisitedColorScheme(Link link) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> webLinks = getAllLinks(driver);

		Map<String, Integer> linkColors = new HashMap();

		for (WebElement webLink : webLinks) {
			if (webLink.getText() == null || webLink.getText().length() == 0) {
				continue;
			}
			String color = webLink.getCssValue("color");

			Integer countOfElements = linkColors.get(color);
			if (countOfElements == null) {
				countOfElements = 1;
			}
			linkColors.put(color, ++countOfElements);
//			if (linkColor == null) {
//				linkColor = color;
//				continue;
//			}
//
//			if (!linkColor.equals(color)) {
//				File file = screenshoter.takeScreenshot(screenshot, webLink, driver);
//				result.getFailedElements().add(prepareFailedElement(
//						ElementType.LINK.name(), webLink.getText(),"The link has different color" , file));
//			}
		}

		String mostlyUsedColor = linkColors.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		System.out.println("Mostly used colot");
		System.out.println(mostlyUsedColor);
		System.out.println(linkColors.get(mostlyUsedColor));

		webLinks = getAllLinks(driver);

		for (WebElement webLink : webLinks) {
			if (webLink.getText() == null || webLink.getText().length() == 0) {
				continue;
			}
			String color = webLink.getCssValue("color");
			if (color.equals(mostlyUsedColor)) {
				System.out.println("dddddddddd");
				System.out.println(webLink.getText());
				try {
					webLink.click();
					driver.navigate().back();
				} catch (Exception ex) {
					ex.printStackTrace();
				}


//				break;
			}
		}


		return setSuccessFlag(result);
	}

	public List<WebElement> getAllLinks(WebDriver driver) {
		return driver.findElements(By.tagName("a"));
	}


}
