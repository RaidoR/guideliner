package usability.estimation;

import ee.ttu.usability.domain.element.link.Button;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

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


	public List<WebElement> getButtons(WebDriver driver) {
		List<WebElement> elements = driver.findElements(By.tagName("button"));
		elements.addAll(driver.findElements(By.xpath("//input[@type='submit']")));
		return elements;
	}

}
