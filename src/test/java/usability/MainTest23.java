package usability;

import jevg.ee.ttu.Guideline;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.NodeSet;

import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import ee.ttu.usability.domain.element.GuidelinetElement;

public class MainTest23 {

	public static void main(String[] args) throws OWLOntologyCreationException {
		OntologyRepository ontology = new OntologyRepository();
		GuildelineBuilderService builder = new GuildelineBuilderService(
				ontology);
		OntologyService ontologyService = new OntologyService(ontology);

		// consistent = Ontology.reasoner.isConsistent();

		ontologyService
				.getAllUsabilityGuidelines()
				.forEach(
						t -> {
							System.out.println(t);
							NodeSet<OWLClass> superClasses = OntologyRepository.reasoner
									.getSuperClasses(t, true);
							superClasses.entities().forEach(g -> {
							});
						});

		// let's assume that we selected one from the list
		OWLClass selectedGuideline = ontology
				.loadClass("03-03_DoNotUseColorAloneToConveyInformation");

		OntologyEvaluatorService serv = new OntologyEvaluatorService(ontology,
				builder);

		GuidelinetElement guidelineElement = serv.fillWithGuidelineElement(selectedGuideline);
		System.out.println(guidelineElement);
		// w..forEach(f -> {System.out.println(f);});
		// System.out.println(w.asOWLClass().individualsInSignature().count());
		// ;
		System.out.println("dddddddddddddddddddddddddddddddddddddd");
		NodeSet<OWLClass> superClasses = OntologyRepository.reasoner
				.getSuperClasses(selectedGuideline, true);
		superClasses.entities().forEach(g -> {
			// System.out.println(g);
			});

		NodeSet<OWLClass> superClasses3 = OntologyRepository.reasoner
				.getSubClasses(selectedGuideline, true);
		superClasses3.entities().forEach(g -> {
			// System.out.println(g);
			// sys
			});

		OWLDataProperty prop = ontology
				.laodOWLDataProperty("hasGuidelineElement");

		// Limit LimitHomePageLength
		// print individuals
		System.out.println("Individuals");

		NodeSet<OWLNamedIndividual> instances = ontology
				.getIndividuals(selectedGuideline);
		System.out.println(guidelineElement);
	}

	public static void printOwlDataProperty(
			OWLDataPropertyAssertionAxiomImpl dataProperty) {
		System.out.println(dataProperty.getProperty().asOWLDataProperty()
				.getIRI());
		System.out.println(dataProperty.getObject().getDatatype());
		System.out.println(dataProperty.getObject().getLiteral());
	}

}
