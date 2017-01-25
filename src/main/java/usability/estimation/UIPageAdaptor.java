package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import deprecated.HtmlValidator;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.attribute.Lang;
import ee.ttu.usability.domain.page.UIPage;
import jevg.ee.ttu.dataproperty.Unit;

@Slf4j
public class UIPageAdaptor extends AbstractAdaptor {

	public EvaluationResult execute(UIPage page) throws IOException {
		if (page.getContentLength() != null) {
			return evaluateContentLength(page);
		}
		if (page.getHorizontalScroll() != null) {
			return evaluateHorizontalScroll(page);
		}
		if (page.getVerticalScroll() != null) {
			return evaluateVerticalScrolling(page);
		}
		if (page.getHeight() != null) {
			return evaluateHeight(page);
		}
		if (page.getLayout() != null) {
			return evaluateLayout(page);
		}
		if (page.getLoadTime() != null) {
			return evaluateLoadTime(page);
		}
		if (page.getProhibitedWords() != null) {
			return evaluateProhibitedWords(page);
		}
		if (page.getText() != null) {
			return evaluateText(page);
		}
		if (page.getHtml() != null) {
			return evaluateHtml(page);
		}
		if (page.getHref() != null) {
			return evaluateHref(page);
		}
		if (page.getTitle() != null) {
			return evaluateTitle(page);
		}
		return null;
	}

	public List<WebElement> getBoldText(WebDriver driver) {
		List<WebElement> bolds = driver.findElements(By.tagName("b"));
		List<WebElement> strongs = driver.findElements(By.tagName("strong"));
		bolds.addAll(strongs);
		return bolds;
	}
	
	private EvaluationResult evaluateLoadTime(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		long start = System.currentTimeMillis();
	
		driver.get(page.getUrl());
	
		WebElement ele = driver.findElement(By.tagName("body"));
		long finish = System.currentTimeMillis();
		long totalTime = finish - start; 
		System.out.println("Total Time for page load - "+totalTime); 
		 
		if (totalTime > page.getLoadTime().getContentLength()) {
			 result.setResult(ResultType.FAIL);
			 result.setDescription("Load time exceeded the expected. Load time : " + totalTime);
		}
		return setSuccessFlag(result);
	}

	public EvaluationResult evaluateContentLength(UIPage page) {
		log.debug("Evaluation evaluateContentLength for UIPage");
		EvaluationResult result = new EvaluationResult();
		List<WebElement> findElements = driver.findElements(By.tagName("body"));
		for (WebElement el : findElements) {
			Integer amountOfUnits = getAmountOfUnit(el.getText(), page.getUnit());			
			 if (amountOfUnits > page.getContentLength()) {
				 String description = "Amount of " + page.getUnit() + " was " + amountOfUnits;
				 result.getFailedElements().add(prepareFailedElement("UI Page", "Home page", description, NO_IMAGE));
					
				 result.setElementType(ElementType.PAGE);
				 result.setResult(ResultType.FAIL);
				 result.setDescription(description);
			 }
		}
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateHorizontalScroll(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(0, 10000);");
		
		Long value = (Long) executor.executeScript("return window.scrollY;");

		if (value > page.getHorizontalScroll().getValue()) {
			 result.setElementType(ElementType.PAGE);
			 result.setResult(ResultType.FAIL);
			 result.setDescription("Horizontal scrol value is bigger then defined. Real value is : " + value);
		}
		return setSuccessFlag(result);
	}
	

	private EvaluationResult evaluateHeight(UIPage page) throws IOException {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);

		screenshot = screenshoter.makeScreenshot(driver);
		
		if (screenshot.getHeight() > page.getHeight().getContentLength()) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Page horizontal length value is bigger then defined. Expected value is " + page.getHeight().getContentLength() + 
					 "Actual value is : " + screenshot.getHeight());		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateVerticalScrolling(UIPage page) {
		System.out.println("vertical scrolling");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(10000, 0);");
		
		Long value = (Long) executor.executeScript("return window.scrollX;");

		if (value > page.getVerticalScroll().getValue()) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Horizontal scrol value is bigger then defined. Real value is : " + value);		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateLayout(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		
		driver.manage().window().setSize(new Dimension(1100, 768));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("scroll(1000, 0);");
		Long value = (Long) executor.executeScript("return window.scrollX;");
		System.out.println(value);
		if (value != 0) {
			FailedElement failed = new FailedElement();
			failed.setType(ElementType.PAGE.name());
			failed.setText("Page");
			failed.setDescription("Layout is not compatible with changing the size of brawser.");		
			failed.setPathToElement(NO_IMAGE);
			result.getFailedElements().add(failed);
		}
		
		if (result.getFailedElements().size() == 0)
			result.setResult(ResultType.SUCCESS);
		else 
			result.setResult(ResultType.FAIL);
		
		return result;
	}
	
	private EvaluationResult evaluateProhibitedWords(UIPage page) {
		log.debug("Evaluation evaluateProhibitedWords for UIPage");
		EvaluationResult result = new EvaluationResult();
		List<WebElement> findElements = driver.findElements(By.tagName("body"));
		for (WebElement el : findElements) {
			String entireText = el.getText();
			for (String element : page.getProhibitedWords().getValue().split(",")) {
				if (entireText.contains(element)) {
					 result.getFailedElements().add(prepareFailedElement("UI Page", "Whole page", "The word " + element + "is not allowed" , NO_IMAGE));
					 result.setElementType(ElementType.PAGE);
				}
			}
		}
		return setSuccessFlag(result);
	}
	
	private EvaluationResult evaluateText(UIPage page) throws IOException {
		log.debug("Evaluating evaluateText");
		screenshot = screenshoter.makeScreenshot(driver);
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		if (page.getText().getCaseType() != null) {
			List<WebElement> texts = getBoldText(driver);
			for(WebElement ele : texts)
			{
				if (StringUtils.isEmpty(ele.getText())) {
					continue;
				}
				if (ele.getText().length() > page.getText().getContentLength()) {
					 File file = screenshoter.takeScreenshot(screenshot, ele, driver);
					 String description = "Amount of Bold text was " + ele.getText().length();
					 result.getFailedElements().add(prepareFailedElement("UI Page", "UI Page", description, file.getName()));
				}
				

			}
			return setSuccessFlag(result);
		}
		
		if (page.getText().getUnit() == Unit.SPACE) {
			List<WebElement> texts = driver.findElements(By.tagName("body"));
			texts.forEach(c -> {
				String text = c.getText();
				if (StringUtils.length(text) < 100) {
					return;
				}

				String space = "";
				for (int i = 0; i < page.getText().getContentLength(); i++) {
					space += " ";
				}
				Pattern compile = Pattern.compile(space + "+");
				Matcher matcher = compile.matcher(text);				
				while (matcher.find()) {
					int start = matcher.start();
					int finish = matcher.end();
					if (matcher.start() - 15 > 0) {
						start = matcher.start() - 15;
					}
					if (matcher.end() + 15 < text.length()) {
						finish = matcher.end() + 15;
					}
					String textWithMultipleSpaces = text.substring(start, finish);
					result.getFailedElements().add(prepareFailedElement("UI Page text", textWithMultipleSpaces, "Text contains multiple spaces" , NO_IMAGE));
				}
			});
			return setSuccessFlag(result);
		}
		
		throw new IllegalArgumentException();
	}

	private EvaluationResult evaluateHtml(UIPage page) {
		if (page.getHtml().getLang() != null) {
			return evaluateLang(page.getHtml().getLang());
		}
		if (page.getHtml().getAlternativeText() != null) {
			return evaluateAtlernativeText(page.getHtml().getAlternativeText());
		}
		HtmlValidator validator = new HtmlValidator();
		return validator.test(driver.getPageSource());
	}
	
	private EvaluationResult evaluateAtlernativeText(AlternativeText alternativeText) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
				
		if (alternativeText.getProhibitedWords() != null) {
			List<WebElement> imgs = driver.findElements(By.tagName("img"));
			imgs.addAll(driver.findElements(By.xpath("//input[@type='image']")));
			List<String> prohibitedWords = Arrays.asList(alternativeText.getProhibitedWords().getValue().split(","));
			imgs.forEach(e -> {
				String alt = e.getAttribute("alt");
				if (StringUtils.isNotBlank(alt) && prohibitedWords.contains(alt.trim())) {
					 File file = screenshoter.takeScreenshot(screenshot, e, driver);			 
					 result.getFailedElements().add(prepareFailedElement("UI Page", "Elements with alt attribute", "The word " + alt.trim() + " for alternative text is not allowed" , file));
				}
			});
		}
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateLang(Lang lang) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);

		if (lang.getIsValued()) {
			List<WebElement> findElements = driver.findElements(By.tagName("html"));

			if (findElements.size() == 0) {
				result.getFailedElements()
						.add(prepareFailedElement("Html tag", "", "Html tag does not exist.", NO_IMAGE));
				return setSuccessFlag(result);
			}

			Optional<WebElement> langElement = findElements.stream()
					.filter(el -> StringUtils.isNotBlank(el.getAttribute("lang"))).findFirst();

			if (!langElement.isPresent()) {
				result.getFailedElements()
						.add(prepareFailedElement("Lang attribute of HTML tag", "", "Lang is not defined", NO_IMAGE));
			}
			return setSuccessFlag(result);

		}

		return null;
	}

	private EvaluationResult evaluateHref(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		List<WebElement> list=driver.findElements(By.xpath("//*[@href or @src]"));

		for (WebElement e : list) {
			String link = e.getAttribute("href");
			if (null == link)
				link = e.getAttribute("src");
			if (link.equals(page.getHref().getValue())) {
				return result;
			}
		}
	    
		result.getFailedElements().add(prepareFailedElement("UI Page", "", "Link is not found: " + page.getHref().getValue(), NO_IMAGE));
	       
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateTitle(UIPage page) {
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PAGE);
		result.setResult(ResultType.SUCCESS);
		
		List<WebElement> findElements = driver.findElements(By.tagName("title"));
		
		if (findElements.size() == 0) {
			result.getFailedElements().add(prepareFailedElement("Title tag", "", "Title tag does not exist.", NO_IMAGE));
			return setSuccessFlag(result);
		}
		
		Optional<WebElement> definedText = findElements
			.stream()
			.filter(el -> StringUtils.isNotBlank(el.getAttribute("innerHTML")))
			.findFirst();
		
		if (!definedText.isPresent()) {
			result.getFailedElements().add(prepareFailedElement("Title tag", "", "Title tag does not have value.", NO_IMAGE));
			return setSuccessFlag(result);
		}
		
		return setSuccessFlag(result);
	}

}
