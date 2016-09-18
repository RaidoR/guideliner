package core;

import org.junit.Before;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import usability.GuildelineBuilderService;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;

public class AbstractTest {

	protected OntologyRepository ontology;
	protected GuildelineBuilderService builder;
	protected OntologyService ontologyService;
	protected OntologyEvaluatorService evaluatorService;
	
	@Before
	public void setUp() throws OWLOntologyCreationException {
		ontology = new OntologyRepository();
		builder = new GuildelineBuilderService(
				ontology);
		ontologyService = new OntologyService(ontology);
		evaluatorService = new OntologyEvaluatorService(ontology,
				builder);
	}
	
}
