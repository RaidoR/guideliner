package usability.estimation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;

import org.openqa.selenium.WebDriver;

import usability.estimation.result.FailedElement;
import usability.estimation.utils.Screenshoter;

@Data
public class AbstractAdaptor {

	protected static final String NO_IMAGE = "NONE";
	
	protected WebDriver driver;
	
	protected Screenshoter screenshoter = new Screenshoter();
	
	protected BufferedImage screenshot = null;
	
	protected List<FailedElement> failedElements = new ArrayList<FailedElement>();
	
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
	
}
