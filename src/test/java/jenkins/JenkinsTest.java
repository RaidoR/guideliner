package jenkins;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.*;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;
import jevg.ee.ttu.dataproperty.Unit;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestContextManager;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.configuration.Application;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;

import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class JenkinsTest extends AbstractJenkinsTest {

    private static final String URL = "http://www.etis.ee";

    @Autowired
    private OntologyEvaluatorService ontologyEvaluatorService;

    @Autowired
    private OntologyRepository ontologyRepository;


    // Manually config for spring to use Parameterised
    private TestContextManager testContextManager;


    @Before
    public void configureDriver() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);

        ontologyEvaluatorService.initialiseDriver(URL);
    }

    @DataProvider
    public static Object[][] wcagGuidelines() {
        return new Object[][] {
                { "03-03_DoNotUseColorAloneToConveyInformation"}
        };
    }

    @Test
    @UseDataProvider("wcagGuidelines")
    public void testWcagGuidelines(String guidelines) {
        // given
        System.out.println("lll"+guidelines);
        OWLClass guideline = ontologyRepository
                .loadClass(guidelines);

        // when
        EvaluationResult result = ontologyEvaluatorService.evaluate(guideline, null, false);

        //then
        assertEvaluationResult(result);
    }

}
