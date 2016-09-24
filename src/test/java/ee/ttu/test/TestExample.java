package ee.ttu.test;

import jevg.ee.ttu.dataproperty.Unit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import core.AbstractTest;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.page.UIPage;

public class TestExample extends AbstractTest {

	@Before
	public void setUp() throws OWLOntologyCreationException {
		super.setUp();
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

}
