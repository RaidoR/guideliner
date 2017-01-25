package usability.estimation;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import ee.ttu.usability.domain.attribute.Contrast;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.element.content.Paragraph;

public class UsabilityEstimator2 {

	UIPageAdaptor estimator = new UIPageAdaptor();
	
	public static void main(String[] args) throws IOException {
		
		ParagraphAdaptor estimator = new ParagraphAdaptor();
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		WebDriver driver = new FirefoxDriver();
		
		GuidelinetElement element = new Paragraph();
		Contrast contr = new Contrast();
		contr.setContrast(5);
		contr.setValue("5");
		
		element.setContrast(contr);
		
		driver.get("https://www.etis.ee");
		
//		if (element instanceof UIPage) {
//			estimator.setDriver(driver);
//			System.out.println(estimator.execute((UIPage) element));
//		}
		
		if (element instanceof Paragraph) {
			estimator.setDriver(driver);
			System.out.println(estimator.execute((Paragraph) element));
		}
		
		driver.close();
	}
	
}
