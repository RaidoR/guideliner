package ee.ttu.usability.guideliner.rest;

import ee.ttu.usability.guideliner.repository.OntologyRepository;
import ee.ttu.usability.guideliner.estimation.result.EvaluationResult;
import ee.ttu.usability.guideliner.service.impl.OntologyEvaluatorService;
import ee.ttu.usability.guideliner.service.impl.OntologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
