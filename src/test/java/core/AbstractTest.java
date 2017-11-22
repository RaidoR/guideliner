package core;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import ee.ttu.usability.guideliner.service.impl.GuildelineBuilderService;
import ee.ttu.usability.guideliner.service.impl.OntologyEvaluatorService;
import ee.ttu.usability.guideliner.repository.OntologyRepository;
import ee.ttu.usability.guideliner.service.impl.OntologyService;

public class AbstractTest {

	protected static OntologyRepository ontologyRepository;
	protected static GuildelineBuilderService builder;
	protected static OntologyService ontologyService;
	protected static OntologyEvaluatorService ontologyEvaluatorService;
	
	public static void setUpClasses() throws OWLOntologyCreationException {
		ontologyRepository = new OntologyRepository();
		builder = new GuildelineBuilderService(
				ontologyRepository);
		ontologyService = new OntologyService(ontologyRepository);
		ontologyEvaluatorService = new OntologyEvaluatorService(ontologyRepository, builder, ontologyService);
	}
	
}
