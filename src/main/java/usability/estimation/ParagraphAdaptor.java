package usability.estimation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jevg.ee.ttu.dataproperty.Unit;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ibm.icu.text.BreakIterator;

import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.ResultType;
import usability.estimation.utils.ContrastEstimator;
import ee.ttu.usability.domain.element.content.Paragraph;

@Slf4j
public class ParagraphAdaptor extends AbstractAdaptor {
	
	public EvaluationResult execute(Paragraph paragraph) throws IOException {
		if (paragraph.getContrast() != null && paragraph.getContrast().getContrast() != null) {
			return evaluateContrast(paragraph);
		} else if (paragraph.getContentLength() != null) {
			return evaluateContentLength(paragraph);
		}
		return null;
	}

	public EvaluationResult evaluateContentLength(Paragraph page) throws IOException {
		if (Unit.WORDS_IN_SENTENCE == page.getUnit()) {
			return evaluateWordsInSentence(page);
		}
		return evaluateWordsInParagraph(page);
	}
	
	private EvaluationResult evaluateWordsInParagraph(Paragraph page) throws IOException {
		log.debug("Evaluating evaluateWordsInParagraph");
		screenshot = screenshoter.makeScreenshot(driver);
		log.debug("Evaluation evaluateWordsInParagraph for Link");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PARAGRAPH);
		result.setResult(ResultType.SUCCESS);
		List<WebElement> allLinks = getAllElelements(driver);
		for (WebElement el : allLinks) {
			try {
				if (el.getText().trim().length() > 100) {
					List<WebElement> subElements = el.findElements(By.cssSelector("*"));
					if (subElements.size() > 0) {
						continue;
					}
					String text = el.getText().trim();
					Integer amountOfUnit = getAmountOfUnit(text, page.getUnit());
					if (amountOfUnit > page.getContentLength()) {
						File file = screenshoter.takeScreenshot(screenshot, el, driver);
						 result.getFailedElements().add(prepareFailedElement(ElementType.PARAGRAPH.name(), ElementType.PARAGRAPH.name()
								 , "Paragraph contains more words then exepcted: " + amountOfUnit , file.getName()));
						 result.setElementType(ElementType.NUMBERED_LIST);
					}
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateWordsInSentence(Paragraph page) throws IOException {
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		
		screenshot = screenshoter.makeScreenshot(driver);
		
		List<FailedElement> failedElements = new ArrayList<FailedElement>();
		log.debug("Evaluation evaluateContentLength for Link");
		EvaluationResult result = new EvaluationResult();
		result.setElementType(ElementType.PARAGRAPH);
		result.setResult(ResultType.SUCCESS);
		List<WebElement> allLinks = getAllElelementsByTag(driver);
		for (WebElement el : allLinks) {
			try {
				if (el.getText().trim().length() > 100) {
					//System.out.println(el.getText());
					String text = el.getText().trim();
					iterator.setText(text);
					int start = iterator.first();
					for (int end = iterator.next();
						    end != BreakIterator.DONE;
						    start = end, end = iterator.next()) {
						String sentence = text.substring(start,end);
						Integer amountOfUnits = getAmountOfUnit(sentence, Unit.WORD);		
						 if (amountOfUnits > page.getContentLength()) {
							FailedElement failed = new FailedElement();
							failed.setType(ElementType.PARAGRAPH.name());
							failed.setText(sentence);
							failed.setDescription("Expected sentence size is " + page.getContentLength() + " words. Actual image has size is : " + amountOfUnits);
							failed.setPathToElement(NO_IMAGE);
							failedElements.add(failed);
						 }

						}
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		
		result.setFailedElements(failedElements);
		return setSuccessFlag(result);
	}

	private EvaluationResult evaluateContrast(Paragraph paragraph) throws IOException {
		ContrastEstimator estimator = new ContrastEstimator();
		List<WebElement> allLinks = getAllElelements(driver);
		List<WebElement> filteredElement = new ArrayList<WebElement>();
		for (WebElement element : allLinks) {
			if (element.getText().trim().length() > 100) {
				System.out.println(element.getText());
				filteredElement.add(element);
			}
		}
		System.out.println("ESTIMATION");
		return estimator.estimate(filteredElement, driver);
	}

	public List<WebElement> getAllElelements(WebDriver driver) {
		return driver.findElements(By.cssSelector("*"));
	}

	public List<WebElement> getAllElelementsByTag(WebDriver driver) {
		return driver.findElements(By.cssSelector("body"));
	}
}
