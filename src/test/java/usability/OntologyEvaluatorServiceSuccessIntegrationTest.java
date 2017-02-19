package usability;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;

@Ignore
public class OntologyEvaluatorServiceSuccessIntegrationTest extends OntologyEvaluatorServiceAbstractIntegrationTest {

    private static final String URL = "file:///C:/Users/jevgeni.marenkov/Desktop/yli/ontology/project/ontology-test-client/correct/index.html";

    @Autowired
    private OntologyEvaluatorService ontologyEvaluatorService;

    @Test
    public void test10_11_UseAppropriateTextLinkLengths() {
        // given
        String guideline = "10-11_UseAppropriateTextLinkLengths";
        
        // when
        EvaluationResult evaluationResult = ontologyEvaluatorService.evaluateByName(guideline, URL);

        // then
        Assert.assertEquals(ResultType.SUCCESS, evaluationResult.getResult());
        Assert.assertEquals(0, evaluationResult.getFailedElements().size());
    }

    @Test
    public void test() {
        // given
        String guideline = "14-09_LimitTheUseOfImages";

        // when
        EvaluationResult evaluationResult = ontologyEvaluatorService.evaluateByName(guideline, "https://www.etis.ee/");

        // then
        System.out.println(evaluationResult.toString());
        System.out.println(evaluationResult.getFailedElements().size());
    }

}
