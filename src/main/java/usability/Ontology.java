package usability;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
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

import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;
import uk.ac.manchester.cs.jfact.JFactFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

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

	public Ontology() throws OWLOntologyCreationException {
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

	public static OWLClass loadClass(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLClass(composeIRI(shortName));
	}
	
	public static OWLDataProperty laodOWLDataProperty(String shortName) {
		return ontology.getOWLOntologyManager().getOWLDataFactory().getOWLDataProperty(composeIRI(shortName));
	}

	public static IRI composeIRI(String shortName) {
		return IRI.create(nameSpace + shortName);
	}
	
	public GuidelinetElement getGuidelineElement(OWLClass selectedGuideline) {
		for (OWLClassAxiom g : Ontology.ontology.getAxioms(selectedGuideline)) {
			 if (g instanceof OWLSubClassOfAxiomImpl) {
				 OWLSubClassOfAxiomImpl g2 = (OWLSubClassOfAxiomImpl) g;
//				 System.out.println("eeeeeeeeeeeeeee");
//				 System.out.println(g2.getSuperClass());
				 if (g2.getSuperClass() instanceof OWLObjectAllValuesFromImpl) {
					 OWLObjectAllValuesFromImpl allValuesOf = (OWLObjectAllValuesFromImpl) g2.getSuperClass();
//					 System.out.println("aaaaaaaaaaa" + allValuesOf.getProperty().asOWLObjectProperty().getIRI().getFragment());
//					 System.out.println("aaaaaaaaaaa" + allValuesOf.getFiller() + "type of fille" + allValuesOf.getFiller().getClass());
				 }
				 if (g2.getSuperClass() instanceof OWLObjectSomeValuesFrom) {
					 OWLObjectSomeValuesFrom someValueOf = (OWLObjectSomeValuesFrom) g2.getSuperClass();
					 if ("hasGuidelineElement".equalsIgnoreCase(someValueOf.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
						 if ("UIPage".equalsIgnoreCase(someValueOf.getFiller().asOWLClass().getIRI().getShortForm())) {
							 return new UIPage();
						 }
					 }
//					 System.out.println("aaaaaaaaaaa" + someValueOf.getProperty().asOWLObjectProperty().getIRI().getShortForm());
//					 System.out.println("aaaaaaaaaaa" + someValueOf.getFiller().asOWLClass().getIRI().getShortForm() + "type of fille" + someValueOf.getFiller().getClass());
				 }
			 }
//			 System.out.println(g.getClass());
		 }
		 return null;
	}

}
