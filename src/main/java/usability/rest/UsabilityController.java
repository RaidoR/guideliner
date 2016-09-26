package usability.rest;

import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.Guideline;

/**
 * @author jevgeni.marenkov
 */
@RestController
public class UsabilityController {
	
    private OntologyEvaluatorService evaluatorService;
    
    private OntologyRepository ontologyRepository;
    
    private OntologyService ontologyService;
    
    @Autowired
    public UsabilityController(OntologyEvaluatorService evaluatorService, OntologyRepository ontologyRepository, OntologyService ontologyService) {
    	this.evaluatorService = evaluatorService;
    	this.ontologyRepository = ontologyRepository;
    	this.ontologyService = ontologyService;
    }

    @RequestMapping("/usability")
    public EvaluationResult sampleExample(@RequestParam(value="guideline", defaultValue="05-07_LimitHomePageLength") String name) {
    	// http://localhost:8080/usability
    	// OWLClass selectedGuideline = ontologyRepository.loadClass("05-07_LimitHomePageLength");
    	// OWLClass selectedGuideline = ontologyRepository.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
    	// OWLClass selectedGuideline = ontologyRepository.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
    	// OWLClass selectedGuideline = ontologyRepository.loadClass("10-11_UseAppropriateTextLinkLengths");
    	OWLClass selectedGuideline = ontologyRepository.loadClass("08-01_EliminateHorizontalScrolling");

    	
        return evaluatorService.evaluate(selectedGuideline);
    }
    
    @RequestMapping("/retrieve")
    public List<Guideline> retrieveAllGuidelines() {	
        return ontologyService.getAllUsabilityGuidelinesHardCoded();
    }

}
