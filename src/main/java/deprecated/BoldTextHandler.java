package deprecated;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BoldTextHandler {

	Integer MAX_PARAM = 70;
	
	public void checkText(WebDriver driver) throws Exception{
		System.out.println("############### Starting bold text analyser ###############");
		List<WebElement> texts = getAllTexts(driver);
		
		int amountOfText = 0;
		int amountOfIncorrect = 0;
		for(WebElement ele : texts)
		{
			if (StringUtils.isEmpty(ele.getText())) {
				continue;
			}
			amountOfText++;
			if (ele.getText().length() > MAX_PARAM) {
				amountOfIncorrect++;
				System.out.println("Violates the bold text requirements " + ele.getText());				
			}
			

		}
		System.out.println("Sum up: amount of links :" + amountOfText);
		System.out.println("Sum up: amount of incorrect links :" + amountOfIncorrect);
		System.out.println("############### Finishing bold text analyser ###############");
		
	}
	
	public List<WebElement> getAllTexts(WebDriver driver) {
		List<WebElement> bolds = driver.findElements(By.tagName("b"));
		List<WebElement> strongs = driver.findElements(By.tagName("strong"));
		bolds.addAll(strongs);
		return bolds;
	}
}
