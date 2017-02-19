package jenkins;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import usability.configuration.Application;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//@Rollback
public class AbstractJenkinsTest {

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
