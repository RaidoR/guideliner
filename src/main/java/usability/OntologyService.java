package usability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usability.estimation.result.Guideline;


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
	
	public List<Guideline> getAllUsabilityGuidelinesHardCoded() {
		List<Guideline> guidelines = new ArrayList<Guideline>();
		List<String> guidelineCodes = Arrays.asList(
				"05-07_LimitHomePageLength", 
				"03-03_DoNotUseColorAloneToConveyInformation", 
				"11-01_UseBlackTextonPlainHighContrastBackgrounds",
				"10-11_UseAppropriateTextLinkLengths",
				"08-01_EliminateHorizontalScrolling");
		for (String code : guidelineCodes) {
			OWLClass clazz = ontology.loadClass(code);
			Guideline guide = new Guideline();
			guide.setName(ontology.getAnnotationValueByAnnotationName(clazz, "guideline"));
			guide.setDescription(ontology.getAnnotationValueByAnnotationName(clazz, "comment"));
			guidelines.add(guide);
		}
		return guidelines;
	}
	
}
