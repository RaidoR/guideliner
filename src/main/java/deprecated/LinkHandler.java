package deprecated;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LinkHandler {

	public void checkAllLinks(WebDriver driver) throws Exception{
		List<WebElement> link = getAllLinks(driver);
		
		int i = 0;
		
		int amountOfProcessesLinks = 0;
		int amountOfIncorrectLinks = 0;
		
		System.out.println("Overall number of links " + link.size());
		for(WebElement ele : link)
		{
			try {
			if (StringUtils.isEmpty(ele.getText())) {
				continue;
			}
			amountOfProcessesLinks++;
			
				System.out.println("linktext " + ele.getText());
				String color = ele.getCssValue("color");
				String hexaColor = convertCssColorToHexadecimalFormat(color);
			//	System.out.println("We get the next foregraund>" + hexaColor);
				color = color.substring(5);
				StringTokenizer st = new StringTokenizer(color);
				
				int r = Integer.parseInt(st.nextToken(",").trim());
				int g = Integer.parseInt(st.nextToken(",").trim());
				int b = Integer.parseInt(st.nextToken(",").trim());
				double foregraund = ContrastUtils.calculateLuminance(r, g, b);
				double background = makeScreenShot(driver, ele, i++);  
				double contrastRatio = ContrastUtils.calculateContrastRatio(foregraund, background);
				
				float textSize = new Float(ele.getCssValue("font-size").replace("px", ""));		
				double requiredContrast = isLargeText(textSize, isTextBold(ele))
			              ? ContrastUtils.CONTRAST_RATIO_WCAG_LARGE_TEXT
			              : ContrastUtils.CONTRAST_RATIO_WCAG_NORMAL_TEXT;
				
				 if (contrastRatio < requiredContrast) {
					 amountOfIncorrectLinks++;
					 String message = String.format("Aaa does not have required contrast of "
			                  + "%f. Actual contrast is %f", requiredContrast, contrastRatio);
					 System.out.println(message);
					 System.out.println("linktext " + ele.getText());
				 }
				
			//	System.out.println("Contrast ratio" + contrastRatio);
			} catch (Exception e) {
				//System.out.println("33333333333333333 " + e.getMessage());
			}
		} 
		
		
		System.out.println("Sum up: amount of links :" + amountOfProcessesLinks);
		System.out.println("Sum up: amount of incorrect links :" + amountOfIncorrectLinks);
	}
	
	private boolean isTextBold(WebElement element) {
		String fontWeight = element.getCssValue("font-weight");
		return fontWeight.equals("bold") || fontWeight.equals("700") || fontWeight.equals("strong");
	}
	
    private String convertCssColorToHexadecimalFormat(String color) {
		String s1 = color.substring(5);
		StringTokenizer st = new StringTokenizer(s1);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		return String.format("#%02x%02x%02x", r, g, b);
	}
	
	public List<WebElement> getAllLinks(WebDriver driver) {
		return driver.findElements(By.tagName("a"));
	}
	
	public double makeScreenShot(WebDriver driver, WebElement ele, int i) throws IOException{
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);
		
		Point point = ele.getLocation();
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();
		
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		
		String hex = "#"+Integer.toHexString(eleScreenshot.getRGB(0, 0)).substring(2);
	//	System.out.println("We get the next backgroubnd>" + hex);
		
		Color color = new Color(eleScreenshot.getRGB(0, 0), true);
		return ContrastUtils.calculateLuminance(color.getRed(), color.getGreen(), color.getBlue());
		//ContrastUtils.calculateLuminance(red, green, blue)
		//
		
		//ImageIO.write(eleScreenshot, "png", screenshot);
			//Copy the element screenshot to disk
		//FileUtils.copyFile(screenshot, new File(i + "test2.png"));
	}
	
	 private static boolean isLargeText(float textSize, boolean isBold) {
		    if ((textSize >= ContrastUtils.WCAG_LARGE_TEXT_MIN_SIZE)
		        || ((textSize >= ContrastUtils.WCAG_LARGE_BOLD_TEXT_MIN_SIZE)
		            && isBold)) {
		      return true;
		    }
		    return false;
		  }
	
	// http://webaim.org/resources/contrastchecker/?fcolor=ffffff&bcolor=5c5c57
}
