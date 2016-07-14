package deprecated;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TitleHandler {

	List<String> elements = new ArrayList<String>();
	
	public void checkTitles(WebDriver driver) throws Exception{
		int amountOfProcessesLinks = 0;
		int amountOfIncorrectLinks = 0;

		 List<WebElement> titles = getAllTitles(driver);
		 for (WebElement element : titles) {
			 String el = element.getText();
			 if (StringUtils.isEmpty(el)) {
				continue;				 
			 }
			 //System.out.println(el);
			 amountOfProcessesLinks++;
			 if (elements.contains(el)) {
				 System.out.println("Duplicate links" + el);
				 amountOfIncorrectLinks++;
			 } else {
				 elements.add(el);				 
			 }
			 
		 }
		System.out.println("Sum up: amount of links :" + amountOfProcessesLinks);
		System.out.println("Sum up: amount of incorrect links :" + amountOfIncorrectLinks);
	}
	
	
	public List<WebElement> getAllTitles(WebDriver driver) {
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		System.out.println(elements.size());
		return elements;
	}
	
}
