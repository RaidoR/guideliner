package usability.rest;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.estimation.result.EvaluationResult;

/**
 * @author jevgeni.marenkov
 */
@RestController
public class UsabilityController {
	
    private OntologyEvaluatorService evaluatorService;
    
    private OntologyRepository ontologyRepository;
    
    @Autowired
    public UsabilityController(OntologyEvaluatorService evaluatorService, OntologyRepository ontologyRepository) {
    	this.evaluatorService = evaluatorService;
    	this.ontologyRepository = ontologyRepository;
    }

    @RequestMapping("/usability")
    public EvaluationResult sampleExample(@RequestParam(value="guideline", defaultValue="05-07_LimitHomePageLength") String name) {
        OWLClass selectedGuideline = ontologyRepository.loadClass("05-07_LimitHomePageLength");
        return evaluatorService.evaluate(selectedGuideline);
    }

}
