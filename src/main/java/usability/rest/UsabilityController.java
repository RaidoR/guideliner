package usability.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.Guideline;

/**
 * @author jevgeni.marenkov
 */
@RestController
@CrossOrigin(maxAge = 3600)
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

    @RequestMapping("/retrieve")
    public List<Guideline> retrieveAllGuidelines() {	
        return ontologyService.getAllUsabilityGuidelinesHardCoded();
    }
    
    @RequestMapping("/results")
    public List<EvaluationResult> getAllResults() {	
        return ontologyService.getAllUsabilityGuidelinesHardCodedWithResults();
    }

    @RequestMapping("/usability/categories")
    public List<String> getAllCategories() {
        return Arrays.asList("WcagGuideline", "UsabilityGuideline", "MobileUsabilityGuideline", "AllGuidelines");
    }

    @RequestMapping("/usability/categories/{category}")
    public List<Guideline> getByCategory(@PathVariable("category") String category) {
        return ontologyService.findUsabilityGuidelinesByCategory(category);
    }

    @RequestMapping("/usability/categories2/{category}")
    public List<String> getListByCategory(@PathVariable("category") String category) {
        return ontologyService.findUsabilityGuidelinesByCategory(category).stream().map(e -> e.getCode()).collect(Collectors.toList());
    }

}
