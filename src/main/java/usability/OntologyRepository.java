package usability;

import java.io.File;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.springframework.stereotype.Repository;

import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;
import uk.ac.manchester.cs.jfact.JFactFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

/**
 * @author jevgeni.marenkov
 */
@Slf4j
@Repository
public class OntologyRepository {

	//TODO move to file
	private static String ontologyFile = "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\ontology\\project\\protege-ontology\\usability-guidelines-ontology_v1.1.owl";
	private static String nameSpace = "http://www.semanticweb.org/tarmo/ontologies/2016/3/wug-ont#";
	public static OWLOntology ontology;
	public static OWLReasoner reasoner;

	public OntologyRepository() throws OWLOntologyCreationException {
		this.initialise();
	}
	public static void initialise() throws OWLOntologyCreationException {
		initialiseOntology(null);
		initialiseReasoner();
	}
	
	public static void initialiseOntology(String o) throws OWLOntologyCreationException {
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(ontologyFile));
	}
	
	public static void initialiseReasoner() {
        OWLReasonerFactory reasonerFactory = new JFactFactory();
        ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
        reasoner = reasonerFactory.createReasoner(ontology, config);
        boolean consistent = reasoner.isConsistent();
        log.debug("Reasoner initialised successfully. Consistance of ontology: " + consistent);
	}

	public OWLClass loadClass(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLClass(composeIRI(shortName));
	}
	
	public OWLDataProperty laodOWLDataProperty(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLDataProperty(composeIRI(shortName));
	}

	public IRI composeIRI(String shortName) {
		return IRI.create(nameSpace + shortName);
	}
	
	public NodeSet<OWLNamedIndividual> getIndividuals(OWLClass owlClass) {
		return reasoner.getInstances(owlClass, true);
	}


}
