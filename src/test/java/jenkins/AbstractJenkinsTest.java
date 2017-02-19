package jenkins;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.configuration.Application;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;

import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
@SpringBootTest(classes = Application.class)
public class AbstractJenkinsTest {

    protected static final String URL = "http://www.etis.ee";

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
                    "Problem: " + (++problemNr) + "\n" +
                            "Text: " + failedElement.getText() + "\n" +
                            "Description: " + failedElement.getDescription() + "\n" +
                            "Type: " + failedElement.getType();
            stringBuilder.append(element + "\n");
        }

        return stringBuilder.toString();
    }

}
