package usability.estimation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import usability.estimation.utils.Screenshoter;
import ee.ttu.usability.domain.element.link.Graphic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphicAdaptor extends AbstractAdaptor {

	Screenshoter screenshoter = new Screenshoter();
	
	BufferedImage screenshot = null;
	
	public EvaluationResult execute(Graphic graphic) throws IOException {
		if (graphic.getContentLength() != null) {
			return evaluateContentLength(graphic);
		}
		return null;
	}

	private EvaluationResult evaluateContentLength(Graphic graphic) throws IOException {
		log.debug("Evaluating Graphic.");
		
		screenshot = screenshoter.makeScreenshot(driver);
		
		List<FailedElement> failedElements = new ArrayList<FailedElement>();
		
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.GRAPHIC);
		
		List<WebElement> inputs = driver.findElements(By.tagName("img"));

		for (WebElement element : inputs) {
			URLConnection urlConnection;
			try {
				urlConnection = new URL(element.getAttribute("src")).openConnection();
				long bytes = urlConnection. getContentLengthLong(); // in bytes	
				long kBytes =  bytes / 1024;
				if (kBytes > graphic.getContentLength()) {
					FailedElement failed = new FailedElement();
					failed.setType(ElementType.GRAPHIC.name());
					failed.setText("Image");
					failed.setDescription("Expected image size is " + graphic.getContentLength() + ". Actual image has size is : " + kBytes);			
					File file = screenshoter.takeScreenshot(screenshot, element, driver);
					failed.setPathToElement(file.getName());
					failedElements.add(failed);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
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
