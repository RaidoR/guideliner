package usability;

import ee.ttu.guideliner.ontology.engine.OntologyProcessingService;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.List;

/**
 * Created by jevgeni.marenkov on 6/21/2017.
 */
public class OntologyProcessingServiceImpl implements OntologyProcessingService {
    @Override
    public void reloadOntology() throws OWLOntologyCreationException {
        reloadOntology();
    }

    @Override
    public List<String> findAllCategoriesOfUsabilityGuidelines() {
        return null;
    }

    @Override
    public List<UsabilityGuideline> findUsabilityGuidelinesByCategory(String category) {
        return findUsabilityGuidelinesByCategory(category);
    }

    @Override
    public UsabilityGuideline retrieveUsabilityGuidelineByName(String nameOfGuideline) {
        return retrieveUsabilityGuidelineByName(nameOfGuideline);
    }
}
