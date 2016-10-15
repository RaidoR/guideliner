package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;

public class NavigationAdaptor extends AbstractAdaptor {

	
	public EvaluationResult execute(Navigation link) throws IOException {
		if (link.getHorizontalScroll() != null) {
			return evaluateHorizontalScroll(link);
		} 
		return null;//footer
	}
	
	private EvaluationResult evaluateHorizontalScroll(Navigation c) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.NAVIGATION);

		List<FailedElement> failedElements = new ArrayList<FailedElement>();

		WebElement element = driver.findElement(By.id(c.getId().getValue()));
		if ((element.getLocation().getY() + element.getSize().getHeight()) > driver.manage().window().getSize().getHeight()) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.GRAPHIC.name());
			failed.setText("Navigation bar with id " + c.getId().getValue());
			failed.setDescription("Navigation bar is not fully visible without scrolling.");			
			failed.setPathToElement(NO_IMAGE);
			failedElements.add(failed);
		}
		
		result.setFailedElements(failedElements);
		if (failedElements.size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);

		return result;
	}
}
