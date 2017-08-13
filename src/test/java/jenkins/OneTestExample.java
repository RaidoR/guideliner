package jenkins;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.configuration.Application;
import usability.earl.EarlGuideline;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.Guideline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = Application.class)
public class OneTestExample {


    @Before
    public void configureDriver() throws Exception {
        if (this.testContextManager == null) {
            this.testContextManager = new TestContextManager(getClass());
            this.testContextManager.prepareTestInstance(this);
        }
        ontologyEvaluatorService.initialiseDriverIfNotInitialised(URL);
    }

//    protected static final String URL = "https://www.etis.ee/?lang=ENG";
//    protected static final String URL = "https://www.etis.ee/Portal/Publications/Index?searchType=detailed"; // TODO checkbox
    protected static final String URL =
        "https://www.eesti.ee/portaal/portaal.sisene?level=30&loc=%2Fest%2Fminuasjad";

    // With Form
//     protected static final String URL = "https://www.etis.ee/Portal/Persons/Index?searchType=detailed";
//     protected static final String URL = "https://www.w3schools.com/html/html_forms.asp";
//     protected static final String URL = "https://riigikantselei.ee/en";
    // protected static final String URL = "https://www.miniclip.com/games/en/";


    // protected static final String URL = "http://www.rh.ee/";
//    protected static final String URL = "https://www.etis.ee/Portal/Publications/Index";



    // protected static final String URL = "https://www.eesti.ee/et/index.html";

    // Manually config for spring to use Parameterised
    protected TestContextManager testContextManager;

    @Autowired
    protected OntologyEvaluatorService ontologyEvaluatorService;

    @Autowired
    protected OntologyRepository ontologyRepository;

    @Autowired
    protected OntologyService ontologyService;


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

    @Test
    public void testWcagGuidelines() {
        // given
        OWLClass guideline = ontologyRepository
                .loadClass("32-01_CheckButtonsShouldBeVerticallyStacked");

        // when
        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline, null, false);

        //then
        Guideline g = ontologyService.createGuideline(guideline);
        result.setGuideline(g);

        System.out.println(result);
        String guideline1 = EarlGuideline.generateEarlResult(Arrays.asList(result), "www.test.ee");
        System.out.println(guideline1);
//        assertEvaluationResult(result);
    }

}
