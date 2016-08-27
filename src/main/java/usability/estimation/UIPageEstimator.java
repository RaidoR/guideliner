package usability.estimation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.ttu.usability.domain.page.UIPage;

public class UIPageEstimator extends AbstractEstimator {

	public void execute(UIPage page) {
		if (page.getContentLength() != null) {
			 List<WebElement> findElements = driver.findElements(By.tagName("body"));
			 for (WebElement el : findElements) {
				 System.out.println(el.getText());
			 }
		}
	}
	
	public void evaluateContentLength() {
		
	}
	
}
