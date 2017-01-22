package usability;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;
import usability.estimation.utils.Screenshoter;

import java.io.File;
import java.io.IOException;

public class OntologyEvaluatorServiceFailedIntegrationTest extends OntologyEvaluatorServiceAbstractIntegrationTest {

    private static final String URL = "file:///C:/Users/jevgeni.marenkov/Desktop/yli/ontology/project/ontology-test-client/incorrect/index.html";

    @Autowired
    private OntologyEvaluatorService ontologyEvaluatorService;

    Screenshoter screenshoter;

    @Before
    public void setUp() throws IOException {
        screenshoter = Mockito.mock(Screenshoter.class);
        Mockito.doReturn(null).when(screenshoter).makeScreenshot(Mockito.<WebDriver>any());
        Mockito.doReturn(new File("test")).when(screenshoter).takeScreenshot(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void test10_11_UseAppropriateTextLinkLengths() {
        // given
        String guideline = "10-11_UseAppropriateTextLinkLengths";

        // when
        EvaluationResult evaluationResult = ontologyEvaluatorService.evaluateByName(guideline, URL);

        // then
        Assert.assertEquals(ResultType.FAIL, evaluationResult.getResult());
        Assert.assertEquals(1, evaluationResult.getFailedElements().size());
        Assert.assertEquals(ElementType.LINK.name(), evaluationResult.getFailedElements().get(0).getType());
        Assert.assertEquals("Amount of WORD was 36", evaluationResult.getFailedElements().get(0).getDescription());
    }

}