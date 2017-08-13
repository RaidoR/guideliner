import com.tngtech.java.junit.dataprovider.UseDataProvider;
import jenkins.AbstractJenkinsTest;
import jenkins.UsabilityEvaluationParameter;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.test.context.TestContextManager;
import usability.estimation.result.EvaluationResult;

public class MobileUsabilityEvaluationTest extends AbstractJenkinsTest {

    @Before
    public void configureDriver() throws Exception {
        if (this.testContextManager == null) {
            this.testContextManager = new TestContextManager(getClass());
            this.testContextManager.prepareTestInstance(this);
        }
        ontologyEvaluatorService.initialiseDriverIfNotInitialised
                (UsabilityEvaluationParameter.URL, UsabilityEvaluationParameter.CHRROME, UsabilityEvaluationParameter.MOBILE_SCREEN);

    }

    @Test
    @UseDataProvider("commonMobileUsabilityGuidelines")
    public void testMobileUsabilityGuidelines(String guidelines) {
        // given
        OWLClass guideline = ontologyRepository
                .loadClass(guidelines);

        // when
        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline, null, false);

        //then
        assertEvaluationResult(result);
    }
}
