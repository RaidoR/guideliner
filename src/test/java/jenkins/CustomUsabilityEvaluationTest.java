package jenkins;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import usability.OntologyRepository;
import usability.estimation.result.EvaluationResult;

import java.util.List;

public class CustomUsabilityEvaluationTest extends AbstractUsabilityGuidelineTest {

    @Autowired
    private OntologyRepository repository;

    @Before
    public void configureDriver() throws Exception {
        ontologyEvaluatorService.initialiseDriverIfNotInitialised
                (UsabilityEvaluationParameter.URL, UsabilityEvaluationParameter.CHRROME, UsabilityEvaluationParameter.FULL_SCREEN);
    }

    @Test
    public void testMobileUsabilityGuidelines() {
        // given
        MobileUsabilityGuideline mobileUsabilityGuidelines =  new MobileUsabilityGuideline(this.ontologyEvaluatorService);

        // when
        List<EvaluationResult> result = mobileUsabilityGuidelines.evaluate();

        // then
        assertEvaluationResult(result);
    }

    @DataProvider
    public static Object[][] buttonUsabilityGuidelines () {
        return new Object[][]{
                {"1custom-button-CheckSizeOfTheButton"},
                {"2custom-button-EvaluateContrastRateOfTheButton"},
                {"3custom-button-EvaluateSpaceBetweenButtons"}
        };
    }

//    @Test
//    @UseDataProvider("buttonUsabilityGuidelines")
//    public void testWcagGuidelines(String guidelines) {
//        // given
//        OWLClass guideline = repository.loadClass(guidelines);
//
//        // when
//        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline);
//
//        //then
//        assertEvaluationResult(result);
//    }

}
