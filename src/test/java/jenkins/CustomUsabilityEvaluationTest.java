package jenkins;

import org.junit.Before;

public class CustomUsabilityEvaluationTest extends AbstractUsabilityGuidelineTest {

    @Before
    public void configureDriver() throws Exception {
        ontologyEvaluatorService.initialiseDriverIfNotInitialised
                (UsabilityEvaluationParameter.URL, UsabilityEvaluationParameter.CHRROME, UsabilityEvaluationParameter.FULL_SCREEN);
    }

}
