package ee.ttu.test;

import jevg.ee.ttu.dataproperty.Unit;

import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import core.AbstractTest;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Form;
import ee.ttu.usability.domain.element.link.Graphic;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;

public class TestExample extends AbstractTest {

	@BeforeClass
	public static void setUp() throws OWLOntologyCreationException {
		setUpClasses();
	} 

	@Test
	public void testThatAnnotationsAreExtractedSuccessfullyFromGuidelines() {
		// given
		OWLClass guideline = ontology
				.loadClass("03-03_DoNotUseColorAloneToConveyInformation");

		// when
		String comment = ontology.getAnnotationValueByAnnotationName(guideline, "comment");
		String guide = ontology.getAnnotationValueByAnnotationName(guideline, "guideline");
	
		// then
		Assert.assertNotNull(comment);
		Assert.assertNotNull(guide);
	}
	
	@Test
	public void testThatConstructionWorksForRule03_03() {
		// given
		OWLClass guideline = ontology
				.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
		
		// when
		Link guidelineElement = (Link) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement.getContrast());
		Assert.assertNotNull(guidelineElement.getContrast().getContrast());
	}
	
	@Test
	public void testThatConstructionWorksForRule05_07() {
		// given
		OWLClass guideline = ontology
				.loadClass("05-07_LimitHomePageLength");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement.getUnit());
		Assert.assertNotNull(guidelineElement.getContentLength());
	}
	
	@Test
	public void testThatConstructionWorksForRule11_01() {
		// given
		OWLClass guideline = ontology
				.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
		
		// when
		Paragraph guidelineElement = (Paragraph) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement.getContrast());
		Assert.assertNotNull(guidelineElement.getContrast().getContrast());
	}
	
	@Test
	public void testThatConstructionWorksForRule10_11() {
		// given
		OWLClass guideline = ontology
				.loadClass("10-11_UseAppropriateTextLinkLengths");
		
		// when
		Link guidelineElement = (Link) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement.getUnit());
		Assert.assertNotNull(guidelineElement.getContentLength());
		Assert.assertEquals(guidelineElement.getUnit(), Unit.WORD);
	}
	
	@Test
	public void testThatConstructionWorksForRule08_01() {
		// given
		OWLClass guideline = ontology
				.loadClass("08-01_EliminateHorizontalScrolling");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		System.out.println(guidelineElement.getHorizontalScroll().getValue());
	}
	
	@Test
	public void testThatConstructionWorksForRule03_02() {
		// given
		OWLClass guideline = ontology
				.loadClass("03-02_DesignFormsUsingAssistiveTechnologies");
		
		// when
		Form guidelineElement = (Form) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getLabel());
		Assert.assertTrue(guidelineElement.getLabel().isValued());
	}
	
	@Test
	public void testThatConstructionWorksForRule14_09() {
		// given
		OWLClass guideline = ontology
				.loadClass("14-09_LimitTheUseOfImages");
		
		// when
		Graphic guidelineElement = (Graphic) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getContentLength());
		Assert.assertEquals(guidelineElement.getUnit(), Unit.KBYTE);
	}
	
	@Test
	public void testThatConstructionWorksForRule15_07() {
		// given
		OWLClass guideline = ontology
				.loadClass("15-07_LimitTheNumberOfWordsAndSentences");
		
		// when
		Paragraph guidelineElement = (Paragraph) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getContentLength());
		Assert.assertEquals(guidelineElement.getUnit(), Unit.WORDS_IN_SENTENCE);
	}
	
	@Test
	public void testThatConstructionWorksForRule16_05() {
		// given
		OWLClass guideline = ontology
				.loadClass("16-05_MinimizeTheNumberOfClicksOrPages");
		
		// when
		Navigation guidelineElement = (Navigation) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getId());
		Assert.assertNotNull(guidelineElement.getHorizontalScroll());
		Assert.assertNotNull(guidelineElement.getHorizontalScroll().getValue());
		Assert.assertNotNull(guidelineElement.getId().getValue());
	}

}
