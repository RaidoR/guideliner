package ee.ttu.test;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.util.Assert;

import core.AbstractTest;
import ee.ttu.usability.domain.element.link.Link;

public class TestExample extends AbstractTest {

	@Before
	public void setUp() throws OWLOntologyCreationException {
		super.setUp();
	}
	
	@Test
	public void testThatConstructionWorks() {
		// given
		OWLClass guideline = ontology
				.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
		
		// when
		Link guidelineElement = (Link) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.notNull(guidelineElement.getContrast());
		Assert.notNull(guidelineElement.getContrast().getContrast());
	}

}
