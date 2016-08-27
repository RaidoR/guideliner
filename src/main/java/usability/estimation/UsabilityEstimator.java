package usability.estimation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import deprecated.LinkHandler;
import jevg.ee.ttu.dataproperty.Unit;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;

public class UsabilityEstimator {

	UIPageEstimator estimator = new UIPageEstimator();
	
	public static void main(String[] args) {
		UIPageEstimator estimator = new UIPageEstimator();
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		GuidelinetElement element = new UIPage();
		element.setContentLength(120);
		element.setUnit(Unit.WORD);
		
		driver.get("https://www.etis.ee/Portal/Projects/Index");
		
		if (element instanceof UIPage) {
			estimator.setDriver(driver);
			estimator.execute((UIPage) element);
		}
	}
	
}
