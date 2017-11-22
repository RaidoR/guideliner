package ee.ttu.usability.guideliner.estimation.adaptor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.StringTokenizer;

import ee.ttu.usability.guideliner.domain.dataproperty.Unit;
import ee.ttu.usability.guideliner.estimation.result.ResultType;
import lombok.Data;

import org.openqa.selenium.WebDriver;

import ee.ttu.usability.guideliner.estimation.result.EvaluationResult;
import ee.ttu.usability.guideliner.estimation.result.FailedElement;
import ee.ttu.usability.guideliner.util.Screenshoter;

@Data
public class AbstractAdaptor {

	protected Screenshoter screenshoter = new Screenshoter();

	protected static final String NO_IMAGE = "NONE";
	
	protected WebDriver driver;
	
	protected BufferedImage screenshot = null;


	protected Integer getAmountOfUnit(String string, Unit unit) {
		if (Unit.WORD == unit) {
			 StringTokenizer st = new StringTokenizer(string);
			 return st.countTokens();
		}
		if (Unit.LINE == unit) {
		   String[] lines = string.split("\r\n|\r|\n");
		   return  lines.length;
		}
		return null;
	}

	FailedElement prepareFailedElement(String type, String text, String description, String path) {
		FailedElement element = new FailedElement();
		element.setType(type);
		element.setText(text);
		element.setDescription(description);
		element.setPathToElement(path);
		return element;
	}
	
	FailedElement prepareFailedElement(String type, String text, String description, File file) {
		FailedElement element = new FailedElement();
		element.setType(type);
		element.setText(text);
		element.setDescription(description);
		if (file != null) {
			element.setPathToElement(file.getName());			
		}
		return element;
	}
	
	protected EvaluationResult setSuccessFlag(EvaluationResult result) {
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		
		return result;
	}
	
}
