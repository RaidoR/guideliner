package usability.estimation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import jevg.ee.ttu.dataproperty.Unit;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;

public class UsabilityEstimator {

	UIPageAdaptor estimator = new UIPageAdaptor();
	
	public static void main(String[] args) {
		
		UIPageAdaptor estimator = new UIPageAdaptor();
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		GuidelinetElement element = new UIPage();
		element.setContentLength(120);
		element.setUnit(Unit.WORD);
		
		driver.get("https://www.etis.ee/Portal/Projects/Index");
		
		if (element instanceof UIPage) {
			estimator.setDriver(driver);
			System.out.println(estimator.execute((UIPage) element));
		}
		
		driver.close();
	}
	
}
