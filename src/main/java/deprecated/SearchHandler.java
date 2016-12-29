package deprecated;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchHandler {

	String SEARCH_FIELD_TITLE = "Sisesta märksõnad, mida soovid otsida.";
	
	public void checkSearch(WebDriver driver) throws Exception{
		 driver.findElement(By.name("search_block_form")).sendKeys("aaaaaaa");
		 driver.findElement(By.id("edit-submit")).click();
		 File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 
		 // TODO make the second request and compare screenshots
	}
	
}
