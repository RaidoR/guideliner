package jenkins;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.test.context.TestContextManager;
import usability.estimation.result.EvaluationResult;


public class JenkinsWcagTest extends AbstractJenkinsTest {

    @DataProvider
    public static Object[][] wcagGuidelines() {
        return new Object[][]{
                {"4wcag-8-16_CheckThatEveryPageHasTitle"},
                {"1wcag-8-01_CheckHtmlStyle"},
                {"8wcag-1-1_AlternativeTextShouldNotHaveProhibitedWords"},
                {"7wcag-1-1_ImageShouldHaveAltAttribute"},
                {"11wcag-1-1_LinkAltTextShouldBeDifferentFromText"},
                {"10wcag-1-1_ImageAltTextShouldNotBeAsFileName"},
                {"9wcag-1-1_TextShouldNotContainMultipleSpace"},
                {"5wcag-8-19_CheckThatLanguageIsIncludedToHtmlTag"},
                {"1wcag-8-10_CheckOnClickIsUsedWithOnKeyDown"},
                {"3wcag-8-15_CheckThatPageHasLinkToFrontPage"},
                {"03-09_ProvideClientSideImageMaps"},
                {"6wcag-1-1_AreaShouldHaveAltAttribute"}
        };
    }

    @Test
    @UseDataProvider("wcagGuidelines")
    public void testWcagGuidelines(String guidelines) {
        // given
        OWLClass guideline = ontologyRepository
                .loadClass(guidelines);

        // when
        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline, null, false);

        //then
        assertEvaluationResult(result);
    }

}
