package usability.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.Guideline;
import usability.estimation.result.ResultType;
import usability.estimation.utils.Configuration;

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
        return Arrays.asList("WcagGuideline", "UsabilityGuideline");
    }

    @RequestMapping("/usability/categories/{category}")
    public List<Guideline> getByCategory(@PathVariable("category") String category) {
        return ontologyService.findByCategory(category);
    }

}
