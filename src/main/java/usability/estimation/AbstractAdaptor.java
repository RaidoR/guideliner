package usability.estimation;

import java.util.StringTokenizer;

import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;

import org.openqa.selenium.WebDriver;

@Data
public class AbstractAdaptor {

	protected WebDriver driver;
	
	protected Integer getAmountOfUnit(String string, Unit unit) {
		if (Unit.WORD == unit) {
			 StringTokenizer st = new StringTokenizer(string);
			 return st.countTokens();
		}
		return null;
	}
	
}
