package jenkins;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import ee.ttu.usability.guideliner.service.impl.OntologyEvaluatorService;
import ee.ttu.usability.guideliner.repository.OntologyRepository;
import ee.ttu.usability.guideliner.GuidelinerStarter;
import ee.ttu.usability.guideliner.estimation.result.EvaluationResult;
import ee.ttu.usability.guideliner.estimation.result.FailedElement;

import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
@SpringBootTest(classes = GuidelinerStarter.class)
public class AbstractUsabilityTest {

//    protected static final String URL = "https://www.etis.ee/?lang=ENG";
//    protected static final String URL = "https://www.google.com";
    protected static final String URL = System.getProperty("url");

    // Manually config for spring to use Parameterised
    protected TestContextManager testContextManager;

    @Autowired
    protected OntologyEvaluatorService ontologyEvaluatorService;

    @Autowired
    protected OntologyRepository ontologyRepository;


    protected void assertEvaluationResult(EvaluationResult result) {
        Assert.assertEquals(null, verifyEvaluationResults(result));
    }

    protected String verifyEvaluationResults(EvaluationResult result) {
        if (CollectionUtils.isEmpty(result.getFailedElements())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int problemNr = 0;

        List<String> elements = new ArrayList<>();
        for (FailedElement failedElement : result.getFailedElements()) {
            String element =
                    "Element violating guideline nr. : " + (++problemNr) + "\n" +
                            "Element Text: " + failedElement.getText() + "\n" +
                            "Element type: " + failedElement.getType() + "\n" +
                            "Violation reason " + failedElement.getDescription() + "\n";

            stringBuilder.append(element + "\n");
        }

        return stringBuilder.toString();
    }

}
