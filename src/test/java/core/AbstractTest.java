package core;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import usability.GuildelineBuilderService;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;

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
