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

public class MainTest2 {
	static GuildelineBuilder builder = new GuildelineBuilder();
	public static void main(String[] args) throws OWLOntologyCreationException {
		Ontology ontology = new Ontology();
		OntologyService ontologyService = new OntologyService(ontology);
	
		// consistent =  Ontology.reasoner.isConsistent();
      
		ontologyService.getAllUsabilityGuidelines().forEach(t -> {
    	  System.out.println(t);
    	  NodeSet<OWLClass> superClasses =  Ontology.reasoner.getSuperClasses(t, true);
    	  superClasses.entities().forEach(g -> {
    	  });
		 }
		 
    		  );
		
	  // let's assume that we selected one from the list
      OWLClass selectedGuideline = ontology.loadClass("05-07_LimitHomePageLength");
      
 	 GuidelinetElement guidelineElement = ontology.getGuidelineElement(selectedGuideline);
 	 System.out.println(guidelineElement.getClass());
//      w..forEach(f -> {System.out.println(f);});
//      System.out.println(w.asOWLClass().individualsInSignature().count());
//      ;
      System.out.println("dddddddddddddddddddddddddddddddddddddd");
	  NodeSet<OWLClass> superClasses = Ontology.reasoner.getSuperClasses(selectedGuideline, true);
	  superClasses.entities().forEach(g -> {
		 // System.out.println(g);
	  });
	  
	  NodeSet<OWLClass> superClasses3 = Ontology.reasoner.getSubClasses(selectedGuideline, true);
			  superClasses3.entities().forEach(g -> {
		//  System.out.println(g);
		  //sys
	  });
			  
			  
	 OWLDataProperty prop = ontology.laodOWLDataProperty("hasGuidelineElement");


	 // Limit LimitHomePageLength 
	 // print individuals
	 System.out.println("Individuals");
	 
	 NodeSet<OWLNamedIndividual> instances = ontologyService.getIndividuals(selectedGuideline);
	 
	 Guideline guideline = new Guideline();
	 instances.forEach(f -> {
		 f.forEach(individual -> {
			 Ontology.ontology.axioms(individual).forEach(axiom -> {
				 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
					 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
					 printOwlDataProperty(dataProperty);
				 }
				 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
					 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;				 
				     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), guidelineElement);
				 }
			 });
		 });
	 });
	
	 System.out.println(guidelineElement);
	}
	
	public static void transformToObject(OWLNamedIndividual individual, GuidelinetElement element) {
		 Ontology.ontology.axioms(individual).forEach(axiom -> {
			 System.out.println(axiom);
			 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
				 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
				 builder.fillWithDataProperty(element, dataProperty);
			 }
			 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
				 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;
				 builder.fillWithObjectProperty(element, objectProperty);
			 }
		 });
	}
	
	public static void printOwlDataProperty(OWLDataPropertyAssertionAxiomImpl dataProperty) {
		 System.out.println(dataProperty.getProperty().asOWLDataProperty().getIRI());
		 System.out.println(dataProperty.getObject().getDatatype());
		 System.out.println(dataProperty.getObject().getLiteral());
	}



}
