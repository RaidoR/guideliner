package deprecated;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ImageHandler {

	Integer OVERALL_MAX_IMAGE_SIZE = 1000000;
	
	void processImage(WebDriver driver) {
		List<WebElement> inputs = driver.findElements(By.tagName("img"));
		
		
		int sizeInKb = 0;
		
		System.out.println("hhaahh" + inputs.size());
		for (WebElement element : inputs) {
			URLConnection urlConnection;
			try {
				System.out.println("ddddddddddd");
				System.out.println(element.getAttribute("src"));
				urlConnection = new URL(element.getAttribute("src")).openConnection();
				long bytes = urlConnection. getContentLengthLong(); // in bytes	
				long kBytes =  bytes / 1024;
				System.out.println("Size:" + bytes);
				sizeInKb += kBytes;
			} catch (Exception e) {
				System.out.println("Probleem");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (sizeInKb > OVERALL_MAX_IMAGE_SIZE) {
			System.out.println("Image violates the size requirement");
		}
		System.out.println("Overall image of size in Kbytes" + sizeInKb);
	}
	
//	
//	double megabytes = (kilobytes / 1024);
}
