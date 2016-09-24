package usability.estimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import usability.estimation.result.EvaluationResult;
import usability.estimation.utils.ContrastEstimator;
import ee.ttu.usability.domain.element.content.Paragraph;

public class ParagrapgAdaptor extends AbstractAdaptor {
	
	public EvaluationResult execute(Paragraph paragraph) throws IOException {
		if (paragraph.getContrast() != null && paragraph.getContrast().getContrast() != null) {
			return evaluateContrast(paragraph);
		}
		return null;
	}

	private EvaluationResult evaluateContrast(Paragraph paragraph) throws IOException {
		ContrastEstimator estimator = new ContrastEstimator();
		List<WebElement> allLinks = getAllElelements(driver);
		List<WebElement> filteredElement = new ArrayList<WebElement>();
		for (WebElement element : allLinks) {
			if (element.getText().trim().length() > 100) {
				System.out.println(element.getText());		
				filteredElement.add(element);
			}
		}
		System.out.println("ESTIMATION");
		return estimator.estimate(filteredElement, driver);
	}

	public List<WebElement> getAllElelements(WebDriver driver) {
		return driver.findElements(By.cssSelector("*"));
	}
}
