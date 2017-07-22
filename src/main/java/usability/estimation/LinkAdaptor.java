package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

import javax.swing.text.html.parser.Entity;

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

	private EvaluationResult evaluateWidth(Link link) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
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

	private EvaluationResult evaluateHeight(Link link) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
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

	class TopButton {
		public Integer top;
		public Integer buttom;
		public Integer left;
		public Integer right;
	}

	private EvaluationResult evaluateDistance(Link link) {
		// todo implement
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> links = getAllLinks(driver);

		List<WebElement> linkColors = new ArrayList<>();

//		System.out.println("Size" + links.size());

		Map<String, TopButton> topButtomCoordinates = new HashMap<>();

		for (WebElement existingElement : links) {
			if (StringUtils.isEmpty(existingElement.getText())) {
				continue;
			}
			TopButton topButton = new TopButton();
			topButton.top = existingElement.getLocation().getY();
			topButton.buttom = existingElement.getLocation().getY() +  existingElement.getSize().getHeight();
			topButton.left = existingElement.getLocation().getX();
			topButton.right = existingElement.getLocation().getX() + existingElement.getSize().getWidth();
			topButtomCoordinates.put(existingElement.getText(), topButton);
		}
		for (WebElement element : links) {
			if (StringUtils.isEmpty(element.getText())) {
				continue;
			}
			String errorMessage = isLinkWithBadLocation(element, topButtomCoordinates, link.getDistance().getContentLength());
			if (errorMessage != null) {
//				System.out.println("---------------------------------");
//				System.out.println(element.getText());
//				System.out.println("---------------------------------");
				File file = screenshoter.takeScreenshot(screenshot, element, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.LINK.name(), element.getText(),errorMessage, file));
			}
		}

		// find all links
		// iterate over all links and finf if there are another link close to it

		return setSuccessFlag(result);
	}

	private String isLinkWithBadLocation(WebElement element, Map<String, TopButton> elements, Integer distanceBetween) {
		for (Map.Entry<String, TopButton> entry : elements.entrySet()) {
			// top
			Integer top = element.getLocation().getY();
			Integer button = element.getLocation().getY() + element.getSize().getHeight();

			Integer distanceY;
			if (entry.getValue().top > top) {
				distanceY = entry.getValue().top - button;
			} else {
				continue;
			}

			Integer left = element.getLocation().getX();
			Integer right = element.getLocation().getX() + element.getSize().getWidth();
			Integer distanceX;

			if (entry.getValue().left > left) {
				distanceX = entry.getValue().left - right;
			} else {
				distanceX = left - entry.getValue().right;
			}

//			System.out.println(element.getText() + "--top" + top + "--butto" + button);
//			System.out.println(entry.getKey() + "--top" + entry.getValue().top + "--butto" + entry.getValue().buttom);

			if (distanceY < 0) distanceY = distanceY * (-1);

			if ((distanceY != 0 && distanceY < distanceBetween) && (distanceX != 0 && distanceX < distanceBetween)) {
//				System.out.println("--------" + entry.getValue().top);
//				System.out.println("Distance from top " + distanceY);
//				System.out.println(element.getText() + "-->" + entry.getKey());
//				System.out.println("--------" + element.getLocation().getY());
//				System.out.println("AAAAAAAAAAAAAA" + distanceX);
				return "Element: with text " + element.getText() + " is very close to " + entry.getKey();
			}
		}
		return null;
	}

	private EvaluationResult evaluateSameColor(Link link) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.LINK);

		List<WebElement> webLinks = getAllLinks(driver);

		String linkColor = null;



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
		}

//		Map<String, Integer> results = linkColors.entrySet().stream()
//				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
//				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		Map.Entry<String, Integer> min = Collections.min(linkColors.entrySet(),
				Comparator.comparingDouble(Map.Entry::getValue));

		if (min.getValue() < 3) {
			for (WebElement webLink : webLinks) {
				if (webLink.getText() == null || webLink.getText().length() == 0) {
					continue;
				}
				String color = webLink.getCssValue("color");

				if (color.equals(min.getKey())) {
					File file = screenshoter.takeScreenshot(screenshot, webLink, driver);
					result.getFailedElements().add(prepareFailedElement(
							ElementType.LINK.name(), webLink.getText(), "The link has different color then other links", file));
				}
			}
		}

		return setSuccessFlag(result);
	}

	// TODO remove
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
		}

		driver.navigate().refresh();

		String mostlyUsedColor = linkColors.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
//		System.out.println("Mostly used colot");
//		System.out.println(mostlyUsedColor);
//		System.out.println(linkColors.get(mostlyUsedColor));

		int numberOfTrials = 0;
		int processed = 0;

		List<String> allVisitedLinkTexts = new ArrayList<>();
		while (numberOfTrials < 5) {
			webLinks = getAllLinks(driver);
			int numberOfProcessed = 0;
			for (WebElement webLink : webLinks) {
				if (webLink.getText() == null || webLink.getText().length() == 0) {
					continue;
				}
				String color = webLink.getCssValue("color");
				if (color.equals(mostlyUsedColor)) {
					System.out.println("---------------------");
					System.out.println(webLink.getText());
					System.out.println(numberOfProcessed);
					System.out.println(numberOfTrials);
					numberOfProcessed++;
					if (numberOfProcessed < numberOfTrials) {
						continue;
					}
					try {
						allVisitedLinkTexts.add(webLink.getText());
						webLink.click();
						driver.navigate().back();
						numberOfTrials++;
						break;
					} catch (Exception ex) {
						ex.printStackTrace();
						numberOfTrials++;
					}
				}
			}

			for (String visitedLink : allVisitedLinkTexts) {
				String color = driver.findElement(By.linkText(visitedLink)).getCssValue("color");
//				System.out.println(visitedLink);
//				System.out.println(color);

			}
		}



		return setSuccessFlag(result);
	}

	public List<WebElement> getAllLinks(WebDriver driver) {
		return driver.findElements(By.tagName("a"));
	}


}
