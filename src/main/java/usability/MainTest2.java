package usability;

import jevg.ee.ttu.Guideline;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.NodeSet;

import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
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
//      w..forEach(f -> {System.out.println(f);});
//      System.out.println(w.asOWLClass().individualsInSignature().count());
//      ;
      
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
	 
	 //o.axioms(cls);
	 
	 // print guideline
//	 Ontology.ontology.axioms(selectedGuideline).forEach(g -> {
//
//		 if (g instanceof OWLSubClassOfAxiomImpl) {
//			 OWLSubClassOfAxiomImpl g2 = (OWLSubClassOfAxiomImpl) g;
//			 if (g2.getSuperClass() instanceof OWLObjectAllValuesFromImpl) {
//				 OWLObjectAllValuesFromImpl allValuesOf = (OWLObjectAllValuesFromImpl) g2.getSuperClass();
//				 System.out.println("aaaaaaaaaaa" + allValuesOf.getProperty().asOWLObjectProperty().getIRI().getFragment());
//				 System.out.println("aaaaaaaaaaa" + allValuesOf.getFiller() + "type of fille" + allValuesOf.getFiller().getClass());
//			 }
//			// g2.acc
//		 }
//		 System.out.println(g.getClass());
	//	 System.out.println(g.get);
//	 });
	 
	 // Limit LimitHomePageLength 
	 // print individuals
	 System.out.println("Individuals");
	 
	 NodeSet<OWLNamedIndividual> instances = ontologyService.getIndividuals(selectedGuideline);
	 
	 Guideline guideline = new Guideline();
	 GuidelinetElement element = new GuidelinetElement();
	 instances.forEach(f -> {
		 f.forEach(individual -> {
			 Ontology.ontology.axioms(individual).forEach(axiom -> {
				 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
					 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
					 printOwlDataProperty(dataProperty);
				 }
				 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
					 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;
				     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), element);
				 }
			 });
		 });
	 });
	
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
