package deprecated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollingHandler {
	
	String navigationId = "nav";
	Integer brauserHeight = 50;
	Integer brauserWidth = 1105;
	
	public void checkScrolling(WebDriver driver) throws Exception{
		WebElement navigation = driver.findElement(By.id(navigationId));
		System.out.println("dddddd" + navigation.getSize());
		if (navigation.getSize().getHeight() > brauserHeight) {
			System.out.println("Navigation does not fill entirely into the navigation. Height is not correct. " + brauserHeight);
		}
		System.out.println("dd" + navigation.getSize().getWidth());
		if (navigation.getSize().getWidth() > brauserWidth) {
			System.out.println("Navigation does not fill entirely into the navigation. Width is not correct. " + brauserWidth);
		}

	}

}
