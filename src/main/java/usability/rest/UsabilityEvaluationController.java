package usability.rest;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.Guideline;
import usability.estimation.result.ResultType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
public class UsabilityEvaluationController {

    private OntologyEvaluatorService evaluatorService;

    private OntologyRepository ontologyRepository;

    private OntologyService ontologyService;

    @Autowired
    public UsabilityEvaluationController(OntologyEvaluatorService evaluatorService, OntologyRepository ontologyRepository, OntologyService ontologyService) {
        this.evaluatorService = evaluatorService;
        this.ontologyRepository = ontologyRepository;
        this.ontologyService = ontologyService;
    }

    @RequestMapping("/usability/evaluation/{category}")
    public List<EvaluationResult> evaluateByCategory(@PathVariable("category") String category,
                                                     @RequestParam(value="url", defaultValue="http://www.etis.ee") String webURL) {
        return evaluatorService.evaluate(category, webURL);
    }

}
