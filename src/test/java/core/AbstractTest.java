package core;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import usability.GuildelineBuilderService;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;

public class AbstractTest {

	protected static OntologyRepository ontology;
	protected static GuildelineBuilderService builder;
	protected static OntologyService ontologyService;
	protected static OntologyEvaluatorService evaluatorService;
	
	public static void setUpClasses() throws OWLOntologyCreationException {
		ontology = new OntologyRepository();
		builder = new GuildelineBuilderService(
				ontology);
		ontologyService = new OntologyService(ontology);
		evaluatorService = new OntologyEvaluatorService(ontology, builder);
	}
	
}
