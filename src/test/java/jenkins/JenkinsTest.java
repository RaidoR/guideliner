package jenkins;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.configuration.Application;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class JenkinsTest {

    private static final String URL = "http://www.etis.ee";

    @Autowired
    private OntologyEvaluatorService ontologyEvaluatorService;

    @Autowired
    private OntologyRepository ontologyRepository;

    @Before
    public void configureDriver() {
        ontologyEvaluatorService.initialiseDriver(URL);
    }

    @Test
    public void test03_03_DoNotUseColorAloneToConveyInformation() {
        OWLClass guideline = ontologyRepository
                .loadClass("03-03_DoNotUseColorAloneToConveyInformation");
        EvaluationResult evaluate = ontologyEvaluatorService.evaluate(guideline, null, false);
        Assert.assertEquals(null, verifyEvaluationResults(evaluate));
    }

    private String verifyEvaluationResults(EvaluationResult evaluate) {
        if (CollectionUtils.isEmpty(evaluate.getFailedElements())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int problemNr = 0;

        List<String> elements = new ArrayList<>();
        for (FailedElement failedElement : evaluate.getFailedElements()) {
                String element =
                    "Problem: " + (++problemNr) + "\n" +
                    "Text: " + failedElement.getText() + "\n" +
                    "Description: " + failedElement.getDescription() + "\n" +
                    "Type: " + failedElement.getType();
                stringBuilder.append(element + "\n");
        }

        return stringBuilder.toString();
    }

}
