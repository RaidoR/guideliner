package jenkins;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.test.context.TestContextManager;
import ee.ttu.usability.guideliner.estimation.result.EvaluationResult;

public class SampleUsabilityTest extends AbstractUsabilityTest {

    @Before
    public void configureDriver() throws Exception {
        if (this.testContextManager == null) {
            this.testContextManager = new TestContextManager(getClass());
            this.testContextManager.prepareTestInstance(this);
        }
        ontologyEvaluatorService.initialiseDriverIfNotInitialised(URL);
        System.out.print( "input url: " + URL );
    }

    @After
    public void tearDown() throws Exception {
        ontologyEvaluatorService.closeDriver();
    }
    
    @DataProvider
    public static Object[][] usabilityGuidelines() {
        return new Object[][]{
                {"08-04_UsePagingRatherThanScrolling"},
                {"07-08_KeepNavigationOnlyPagesShort"},
                {"10-11_UseAppropriateTextLinkLengths"},
                {"15-07_LimitTheNumberOfWordsAndSentences"},
                {"11-05_UseBoldTextSparingly"},
                {"03-02_DesignFormsUsingAssistiveTechnologies"},
                {"05-07_LimitHomePageLength"},
                {"03-03_DoNotUseColorAloneToConveyInformation"},
                {"05-03_CreatePositiveFirstImpressionOfYourSite"},
                {"16-05_MinimizeTheNumberOfClicksOrPages"},
                {"08-01_EliminateHorizontalScrolling"},
                {"06-10_SetAppropriatePageLengths"},
                {"14-09_LimitTheUseOfImages"},
                {"06-08_UseFluidLayouts"},
                {"05-06_EnsureTheHomepageLooksLikeHomepage"},
                {"03-05_ProvideTextEquivalentsForNonTextElements"},
                {"04-04_DesignForUserTypicalConnectionSpeed"}
        };
    }

    @DataProvider
    public static Object[][] usabilityGuidelinesTest() {
        return new Object[][]{
                {"5wcag-8-19_CheckThatLanguageIsIncludedToHtmlTag"}
        };
    }


    @Test
    @UseDataProvider("usabilityGuidelinesTest")
    public void testUsabilityGuidelines(String guidelines) {
        // given
        OWLClass guideline = ontologyRepository
                .loadClass(guidelines);

        // when
        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline, null, false);

        //then
        assertEvaluationResult(result);
    }
}
