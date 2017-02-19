package usability;

import jevg.ee.ttu.dataproperty.Unit;

import org.apache.http.util.Asserts;
import org.junit.*;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import core.AbstractTest;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Area;
import ee.ttu.usability.domain.element.link.Button;
import ee.ttu.usability.domain.element.link.Form;
import ee.ttu.usability.domain.element.link.Graphic;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.element.link.Multimedia;
import ee.ttu.usability.domain.element.link.NumberedList;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;
import org.semanticweb.owlapi.reasoner.NodeSet;
import usability.OntologyRepository;

@Ignore
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
		Assert.assertNotNull(guidelineElement.getContentLength());
		Assert.assertNotNull(guidelineElement.getUnit());
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
	
	@Test
	public void testThatConstructionWorksForRule03_05() {
		// given
		OWLClass guideline = ontology
				.loadClass("03-05_ProvideTextEquivalentsForNonTextElements");
		
		// when
		Multimedia guidelineElement = (Multimedia) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getAlternativeText());
	}
	
	@Test
	public void testThatConstructionWorksForRule06_10() {
		// given
		OWLClass guideline = ontology.loadClass("06-10_SetAppropriatePageLengths");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getHeight());
		Assert.assertNotNull(guidelineElement.getHeight().getContentLength());
		Assert.assertNotNull(guidelineElement.getHeight().getUnit());
	}

	@Test
	public void testThatConstructionWorksForRule06_08() {
		// given
		OWLClass guideline = ontology.loadClass("06-08_UseFluidLayouts");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getLayout());
		Assert.assertNotNull(guidelineElement.getLayout().getLayoutType());
	}
	
	@Test
	public void testThatConstructionWorksForRule07_08() {
		// given
		OWLClass guideline = ontology.loadClass("07-08_KeepNavigationOnlyPagesShort");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getVerticalScroll());
		Assert.assertNotNull(guidelineElement.getVerticalScroll().getValue());
	}

	@Test
	public void testThatConstructionWorksForRule03_09() {
		// given
		OWLClass guideline = ontology.loadClass("03-09_ProvideClientSideImageMaps");
		
		// when
		Button guidelineElement = (Button) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getAlternativeText());
	}
	
	@Test
	public void testThatConstructionWorksForRule04_04() {
		// given
		OWLClass guideline = ontology.loadClass("04-04_DesignForUserTypicalConnectionSpeed");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getLoadTime());
		Assert.assertNotNull(guidelineElement.getLoadTime().getContentLength());
		Assert.assertNotNull(guidelineElement.getLoadTime().getUnit());
	}

	@Test
	public void testThatConstructionWorksForRule05_03() {
		// given
		OWLClass guideline = ontology.loadClass("05-03_CreatePositiveFirstImpressionOfYourSite");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getProhibitedWords());
		Assert.assertNotNull(guidelineElement.getProhibitedWords().getValue());
	}
	
	@Test
	public void testThatConstructionWorksForRule08_04() {
		// given
		OWLClass guideline = ontology.loadClass("08-04_UsePagingRatherThanScrolling");
		
		// when
		Paragraph guidelineElement = (Paragraph) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getContentLength());
		Assert.assertNotNull(guidelineElement.getUnit());
	}
	
	@Test
	public void testThatConstructionWorksForRule11_05() {
		// given
		OWLClass guideline = ontology.loadClass("11-05_UseBoldTextSparingly");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getText());
		Assert.assertNotNull(guidelineElement.getText().getCaseType());
		Assert.assertNotNull(guidelineElement.getText().getContentLength());
	}

	// TODO implement
	@Test
	public void test1wcag_8_01_CheckHtmlStyle() {
		// given
		OWLClass guideline = ontology.loadClass("1wcag-8-01_CheckHtmlStyle");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getHtml());
		Assert.assertNotNull(guidelineElement.getHtml().isValid());
	}

	@Test
	public void test1wcag_8_10_CheckOnClickIsUsedWithOnKeyDown() {
		// given
		OWLClass guideline = ontology.loadClass("1wcag-8-10_CheckOnClickIsUsedWithOnKeyDown");
		
		// when
		Button guidelineElement = (Button) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getOnKeyPress());
		Assert.assertNotNull(guidelineElement.getOnClick());
	}
	
	@Test
	public void test3wcag_8_15_CheckThatPageHasLinkToFrontPage() {
		// given
		OWLClass guideline = ontology.loadClass("3wcag-8-15_CheckThatPageHasLinkToFrontPage");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getHref());
		Assert.assertNotNull(guidelineElement.getHref().getValue());
	}
	
	@Test
	public void test4wcag_8_16_CheckThatEveryPageHasTitle() {
		// given
		OWLClass guideline = ontology.loadClass("4wcag-8-16_CheckThatEveryPageHasTitle");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getTitle());
		Assert.assertNotNull(guidelineElement.getTitle().getIsValued());
	}
	
	@Test
	public void test5wcag_8_19_CheckThatLanguageIsIncludedToHtmlTag() {
		// given
		OWLClass guideline = ontology.loadClass("5wcag-8-19_CheckThatLanguageIsIncludedToHtmlTag");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getHtml());
		Assert.assertNotNull(guidelineElement.getHtml().getLang());
		Assert.assertNotNull(guidelineElement.getHtml().getLang().getIsValued());
	}

	@Test
	public void test6wcag_1_1_AreaShouldHaveAltAttribute() {
		// given
		OWLClass guideline = ontology.loadClass("6wcag-1-1_AreaShouldHaveAltAttribute");
		
		// when
		Area guidelineElement = (Area) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getAlternativeText());
		Assert.assertNotNull(guidelineElement.getAlternativeText().isValued());
	}
	
	@Test
	public void test7wcag_1_1_ImageShouldHaveAltAttribute() {
		// given
		OWLClass guideline = ontology.loadClass("7wcag-1-1_ImageShouldHaveAltAttribute");
		
		// when
		Graphic guidelineElement = (Graphic) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getAlternativeText());
		Assert.assertNotNull(guidelineElement.getAlternativeText().isValued());
	}

	@Test
	public void test8wcag_1_1_AlternativeTextShouldNotHaveProhibitedWords() {
		// given
		OWLClass guideline = ontology.loadClass("8wcag-1-1_AlternativeTextShouldNotHaveProhibitedWords");
		
		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getHtml());
		Assert.assertNotNull(guidelineElement.getHtml().getAlternativeText());
		Assert.assertNotNull(guidelineElement.getHtml().getAlternativeText().getProhibitedWords());
		Assert.assertNotNull(guidelineElement.getHtml().getAlternativeText().getProhibitedWords().getValue());
	}

	@Test
	public void test11wcag_1_1_LinkAltTextShouldBeDifferentFromText() {
		// given
		OWLClass guideline = ontology.loadClass("11wcag-1-1_LinkAltTextShouldBeDifferentFromText");
		
		// when
		Link guidelineElement = (Link) evaluatorService.fillWithGuidelineElement(guideline);
		
		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getAlternativeText());
		Assert.assertNotNull(guidelineElement.getAlternativeText().getProhibitedWords());
		Assert.assertNotNull(guidelineElement.getAlternativeText().getProhibitedWords().getUnit());
		Assert.assertNotNull(guidelineElement.getAlternativeText().getProhibitedWords().getUnitAction());
	}

	@Test
	public void test9wcag_1_1_TextShouldNotContainMultipleSpace() {
		// given
		OWLClass guideline = ontology.loadClass("9wcag-1-1_TextShouldNotContainMultipleSpace");

		// when
		UIPage guidelineElement = (UIPage) evaluatorService.fillWithGuidelineElement(guideline);

		// then
		Assert.assertNotNull(guidelineElement);
		Assert.assertNotNull(guidelineElement.getText());
		Assert.assertNotNull(guidelineElement.getText().getUnit());
		Assert.assertNotNull(guidelineElement.getText().getContentLength());
	}
	
	@Test
	public void testSmth() {

		System.out.println("eeeeee");
		ontologyService.getAllWcagUsabilityGuidelines();

		ontologyService
				.getAllWcagUsabilityGuidelines()
				.forEach(
						t -> {
							System.out.println("1.1" + t);
							NodeSet<OWLClass> superClasses = OntologyRepository.reasoner
									.getSuperClasses(t, true);
							superClasses.entities().forEach(g -> {
//								System.out.println("1.2" +g);
							});
						});

	}


}
