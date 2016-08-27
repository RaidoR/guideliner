package usability;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class OntologyService {

	private final String USABILITY_GUIDELINES = "UsabilityGuideline";
	
	private final Ontology ontology;
	
	public OntologyService (Ontology ontology) {
		this.ontology = ontology;
	}
	
	public Stream<OWLClass> getAllUsabilityGuidelines() {
		OWLClass guidelines = ontology.loadClass(USABILITY_GUIDELINES);
		return Ontology.reasoner.getSubClasses(guidelines).entities();
	}
	
	public NodeSet<OWLNamedIndividual> getIndividuals(OWLClass owlClass) {
		return ontology.reasoner.getInstances(owlClass, true);
	}
}
