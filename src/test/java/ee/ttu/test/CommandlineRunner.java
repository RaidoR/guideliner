package ee.ttu.test;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;

import usability.estimation.result.EvaluationResult;
import core.AbstractTest;

public class CommandlineRunner extends AbstractTest {

	@Test
	public void test() {
        OWLClass selectedGuideline = ontology.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
     //   EvaluationResult resutlt = evaluatorService.evaluate(selectedGuideline);
       // System.out.println(resutlt);
	}

}
