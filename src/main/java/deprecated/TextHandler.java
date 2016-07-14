package deprecated;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextHandler {

	public void chectText(WebDriver driver) throws Exception{
		List<WebElement> elements = getAllTexts(driver);
		int amountOfProcessedElements = 0;
		int amountOfIncorrectLinks = 0;
		for (WebElement element : elements) {
			amountOfProcessedElements++;
			if ("underline".equals(element.getCssValue("text-decoration"))) {
				if (StringUtils.isNotBlank(element.getText())) {
					if (!"a".equals(element.getTagName())) {
						System.out.println("Incorrect underline: " + element.getText());
						amountOfIncorrectLinks++;
					}
				}
			}
		}
		System.out.println("Sum up: amount of underlined :" + amountOfProcessedElements);
		System.out.println("Sum up: amount of incorrect underlined :" + amountOfIncorrectLinks);

	}

	public List<WebElement> getAllTexts(WebDriver driver) {
		return driver.findElements(By.cssSelector("*"));
	}
	
}
