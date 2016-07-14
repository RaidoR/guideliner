package usability;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 * @author jevgeni.marenkov
 */
@Slf4j
public class Ontology {

	//TODO move to file
	private static String ontologyFile = "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\ontology\\project\\protege-ontology\\usability-guidelines-ontology_v1.1.owl";
	private static String nameSpace = "http://www.semanticweb.org/tarmo/ontologies/2016/3/wug-ont#";
	public static OWLOntology ontology;
	public static OWLReasoner reasoner;

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

	public static OWLClass loadClass(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLClass(composeIRI(shortName));
	}
	
	public static OWLDataProperty laodOWLDataProperty(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLDataProperty(composeIRI(shortName));
	}

	public static IRI composeIRI(String shortName) {
		return IRI.create(nameSpace + shortName);
	}
	

}
