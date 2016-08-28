package usability;

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jevgeni.marenkov
 */
@Service
public class OntologyService {

	private final String USABILITY_GUIDELINES = "UsabilityGuideline";
	
	private final OntologyRepository ontology;
	
	@Autowired
	public OntologyService (OntologyRepository ontology) {
		this.ontology = ontology;
	}
	
	public Stream<OWLClass> getAllUsabilityGuidelines() {
		OWLClass guidelines = ontology.loadClass(USABILITY_GUIDELINES);
		return OntologyRepository.reasoner.getSubClasses(guidelines).entities();
	}
	
}
