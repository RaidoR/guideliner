package deprecated;

import io.github.bonigarcia.wdm.ChromeDriverManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MainTest {
	
	public static void main(String[] args) throws Exception{
		
//		ChromeDriverService service = new ChromeDriverService.Builder()
//        .usingDriverExecutable(new File("C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe"))
//        .usingAnyFreePort()
//        .build();
//    service.start();
//	System.out.println(service.getUrl());
//		WebDriver driver = new RemoteWebDriver(new URL(service.getUrl().toString()),
//		        DesiredCapabilities.chrome());
		
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		List<String> dd = new ArrayList<String>();
		dd.add("https://www.etis.ee/Portal/Projects/Index");
//		dd.add("http://www.envir.ee/et"); // "zone-portal-navigation"
//		dd.add("https://www.eesti.ee/est"); // nav
//		dd.add("http://www.rkas.ee");
//		dd.add("https://riha.eesti.ee/riha/main");
//		dd.add("http://www.leh.ee/");
//		dd.add("http://www.valgahaigla.ee/");
//		dd.add("http://www.rh.ee/");
//		dd.add("http://www.narvahaigla.ee/");
		
		// Links color
		//LinkHandler linkHandler = new LinkHandler();
		//linkHandler.checkAllLinks(driver);

		// Bold color
		//  4. 68	11:5 Use Bold Text Sparingly Test that bold text length is less than 70 characters. 
//		BoldTextHandler textHandler = new BoldTextHandler();
//		textHandler.checkText(driver);	
		
		for (String site : dd) {
			System.out.println("####################" + site + "##############################");
			driver.get(site);
			LinkHandler linkHandler = new LinkHandler();
			linkHandler.checkAllLinks(driver);
			System.out.println("#################### END #####################################");
			break;
		}
		
		//  52	9:2 Provide Descriptive Page Titles		Test that each title has a different title
//		TitleHandler titleHandler = new TitleHandler();
//		titleHandler.checkTitles(driver);

		//5. 98	13:25 Minimize Use of Shift Key		Test that that search allow upper and lower case letters without error messasge\
//		SearchHandler searchHandler = new SearchHandler();
//		searchHandler.checkSearch(driver);
		
		//6/ 6. 102	14:9 Limit the Use of Images		Test that the overall size of all images does not exceed the permitted maximum size. 
//		ImageHandler imageHandler = new ImageHandler();
//		imageHandler.processImage(driver);
		
		// 7:8 Keep Navigation-Only Pages Short	Functionality for scrolling in Selenium
//		ScrollingHandler handler = new ScrollingHandler();
//		handler.checkScrolling(driver);

		// 8:8 Avoid Misleading Cues to Click
//	    TextHandler handler = new TextHandler();
//		handler.chectText(driver);

		//Thread.sleep(5000);  // Let the user actually see something!
		driver.quit();
	}
	
	public static void evaluateAll() {
	}
	
}

