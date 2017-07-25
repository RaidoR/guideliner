package usability.estimation;

import ee.ttu.usability.domain.element.link.Button;
import ee.ttu.usability.domain.element.link.Link;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(Button el) throws IOException {
		if (el.getAlternativeText() != null) {
			return evaluateAlternativeText(el);
		} 
		if (el.getOnClick() != null && el.getOnKeyPress() != null) {
			return evaluateOnClickAndOnKeyPressActionst(el);
		}
		if (el.getWidth() != null && el.getWidth().getContentLength() != null) {
			return evaluateWidth(el);
		}  else if (el.getHeight() != null && el.getHeight().getContentLength() != null) {
			return evaluateHeight(el);
		} else if (el.getDistance() != null && el.getDistance().getContentLength() != null && el.getDistance().getDistanceType() != null) {
			return evaluateDistance(el);
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
					result.getFailedElements().add(prepareFailedElement(ElementType.IMAGE.name(), "Button", "Button does not have alternative text" , screenshoter.takeScreenshot(screenshot, elem, driver)));

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return setSuccessFlag(result);
	}
	
	private EvaluationResult evaluateOnClickAndOnKeyPressActionst(Button button) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.BUTTON);
		
		if (button.getOnClick().isValued() && button.getOnKeyPress().isValued()) {
			List<WebElement> elements = driver.findElements(By.tagName("button"));
			elements.forEach(el -> {
				if (StringUtils.isNotBlank(el.getAttribute("onclick")) && StringUtils.isBlank(el.getAttribute("onkeypress"))) {
					String text = el.getAttribute("innerHTML");
					result.getFailedElements().add(prepareFailedElement(ElementType.BUTTON.name(), text, "OnClick should be used with onKeyPress ", screenshoter.takeScreenshot(screenshot, el, driver)));
				}
			});
		}
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateWidth(Button button) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.BUTTON);

		List<WebElement> webButtons = getButtons(driver);
		for (WebElement webButton : webButtons) {
			// for some reason some buttons are narrow
			if (webButton.getSize().getWidth() == 0) {
				continue;
			}

			if (button.getWidth().getContentLength() > webButton.getSize().getWidth()) {
				File file = screenshoter.takeScreenshot(screenshot, webButton, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.BUTTON.name(), webButton.getText(),"The width of the button is smaller then expected. Expected minimum: "
								+ button.getWidth().getContentLength() + " actual: " + webButton.getSize().getWidth() , file));
			}
		}

		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateHeight(Button button) throws IOException {
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.BUTTON);

		List<WebElement> webButtons = getButtons(driver);
		for (WebElement webButton : webButtons) {
			// for some reason some buttons are narrow
			if (webButton.getSize().getHeight() == 0) {
				continue;
			}

			if (button.getHeight().getContentLength() > webButton.getSize().getHeight()) {
				File file = screenshoter.takeScreenshot(screenshot, webButton, driver);
				result.getFailedElements().add(prepareFailedElement(
						ElementType.BUTTON.name(), webButton.getText(),"The height of the button is smaller then expected. Expected minimum: "
								+ button.getHeight().getContentLength() + " actual: " + webButton.getSize().getHeight() , file));
			}
		}

		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateDistance(Button link) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.BUTTON);

		List<WebElement> links = getButtons(driver);

		Map<String, TopButton> topButtomCoordinates = new HashMap<>();

		for (WebElement existingElement : links) {
			if (existingElement.getSize().getHeight() == 0 && existingElement.getSize().getWidth() == 0) {
				continue;
			}
			TopButton topButton = new TopButton();
			topButton.top = existingElement.getLocation().getY();
			topButton.buttom = existingElement.getLocation().getY() +  existingElement.getSize().getHeight();
			topButton.left = existingElement.getLocation().getX();
			topButton.right = existingElement.getLocation().getX() + existingElement.getSize().getWidth();
			topButtomCoordinates.put(existingElement.getAttribute("outerHTML"), topButton);
		}

		for (WebElement element : links) {
			if (element.getSize().getHeight() == 0 && element.getSize().getWidth() == 0) {
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
		Integer top = element.getLocation().getY();
		Integer button = element.getLocation().getY() + element.getSize().getHeight();

		for (Map.Entry<String, TopButton> entry : elements.entrySet()) {
			// top

			Integer distanceY;

			if (entry.getValue().top > top) {
				distanceY = entry.getValue().top - button;
			} else if (entry.getValue().top.equals(top)) {
				distanceY = 0;
			} else {
				continue;
			}

			Integer left = element.getLocation().getX();
			Integer right = element.getLocation().getX() + element.getSize().getWidth();
			Integer distanceX;

			if (entry.getValue().left > left) {
				distanceX = entry.getValue().left - right;
			} else if (entry.getValue().left.equals(left)) {
				distanceX = 0;
			} else {
				continue;
			}

			if (distanceX.equals(0) && distanceY.equals(0)) {
				continue;
			}

			if (distanceY < 0) distanceY = distanceY * (-1);

			if ((distanceY != 0 && distanceY < distanceBetween) || (distanceX != 0 && distanceX < distanceBetween)) {
//				System.out.println("--------" + entry.getValue().top);
//				System.out.println("Distance from top " + distanceY);
//				System.out.println(element.getText() + "-->" + entry.getKey());
//				System.out.println("--------" + element.getLocation().getY());
//				System.out.println("AAAAAAAAAAAAAA" + distanceX);
				return "Element: with html " + element.getAttribute("outerHTML") + " is very close to " + entry.getKey();
			}
		}
		return null;
	}


	public List<WebElement> getButtons(WebDriver driver) {
		List<WebElement> elements = driver.findElements(By.tagName("button"));
		elements.addAll(driver.findElements(By.xpath("//input[@type='submit']")));
		return elements;
	}

}
