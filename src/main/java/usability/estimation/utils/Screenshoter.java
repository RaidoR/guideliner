package usability.estimation.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
public class Screenshoter {

	protected AtomicLong screenshotCounter = new AtomicLong();

	public BufferedImage makeScreenshot(WebDriver driver)
			throws IOException {
		try {
			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			BufferedImage image = ImageIO.read(screenshot);
			return image;
		} catch (Exception ex) {
			return null;
		}
//		FileUtils
//				.copyFile(
//						screenshot,
//						new File(
//								"C:\\Users\\jevgeni.marenkov\\Desktop\\Programmes\\screenshot.jpg"));
//		return ImageIO.read(screenshot);
	}

	public File takeScreenshot(BufferedImage screenshot, WebElement element, WebDriver driver) {
		try {
			BufferedImage dest = takeScreenshotAsImage(screenshot, element, driver);
			File screenshotOfElement = new File(Configuration.reportsFolder,
					generateScreenshotFileName() + ".png");
			ensureFolderExists(screenshotOfElement);
			ImageIO.write(dest, "png", screenshotOfElement);
			return screenshotOfElement;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public File save2(WebElement element) {
		try {
			BufferedImage dest = ImageIO.read(element.getScreenshotAs(OutputType.FILE));
			File screenshotOfElement = new File(Configuration.reportsFolder,
					generateScreenshotFileName() + ".png");
			ensureFolderExists(screenshotOfElement);
			ImageIO.write(dest, "png", screenshotOfElement);
			return screenshotOfElement;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage takeScreenshotAsImage(BufferedImage screenshot, WebElement element,
			WebDriver driver) {
		Point p = element.getLocation();
		Dimension elementSize = element.getSize();

		BufferedImage dest = screenshot.getSubimage(p.getX(), p.getY(),
				elementSize.getWidth(), elementSize.getHeight());
		return dest;
	}

	protected File ensureFolderExists(File targetFile) {
		File folder = targetFile.getParentFile();
		if (!folder.exists()) {
			log.info("Creating folder: " + folder);
			if (!folder.mkdirs()) {
				log.error("Failed to create " + folder);
			}
		}
		return targetFile;
	}

	protected String generateScreenshotFileName() {
		return "screen" + timestamp() + "."
				+ screenshotCounter.getAndIncrement();
	}

	protected long timestamp() {
		return System.currentTimeMillis();
	}
}
