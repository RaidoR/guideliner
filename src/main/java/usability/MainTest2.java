package usability;

import java.io.File;

import jevg.ee.ttu.Guideline;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import uk.ac.manchester.cs.jfact.JFactFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

public class MainTest2 {
	public static void main(String[] args) throws OWLOntologyCreationException {
		Ontology ontology = new Ontology();
		ontology.initialise();
//		OWLOntologyManager dd = OWLManager.createOWLOntologyManager();
//		OWLOntology o = dd.loadOntologyFromOntologyDocument(new File("C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\ontology\\project\\protege-ontology\\usability-guidelines-ontology_v1.1.owl"));
		OWLClass person = ontology.loadClass("UsabilityGuideline");
		
//		Reasoner hermit=new Reasoner(o);
//       System.out.println(hermit.isConsistent());

		System.out.println("w3w3w3w3w3w3w3w3");
		System.out.println(person);
		
		// prints all classes
		//o.classesInSignature().forEach(ff -> System.out.println(ff));


      // Ask the reasoner to do all the necessary work now
      

      // We can determine if the ontology is actually consistent (in this case, it should be).
/*      boolean consistent = reasoner.isConsistent();
     
      reasoner.getSubClasses(person).entities().forEach(t -> {
    	  // 
    	  NodeSet<OWLClass> superClasses = reasoner.getSuperClasses(t, true);
    	  superClasses.entities().forEach(g -> {
    		  System.out.println(g);
    	  });
      }
    		  );
      
      System.out.println("Consistent: " + consistent);
      System.out.println("\n");

  */    
      // 
      OWLClass w = ontology.loadClass("0-11_UseAppropriateTextLinkLengths");
	  NodeSet<OWLClass> superClasses = Ontology.reasoner.getSuperClasses(w, true);
	  superClasses.entities().forEach(g -> {
		 // System.out.println(g);
	  });
	  
	  NodeSet<OWLClass> superClasses3 = Ontology.reasoner.getSubClasses(w, true);
			  superClasses3.entities().forEach(g -> {
		//  System.out.println(g);
		  //sys
	  });
			  
		
	 OWLDataProperty prop = ontology.laodOWLDataProperty("hasGuidelineElement");
	 
	 //o.axioms(cls);
	 
	 // print guideline
	 Ontology.ontology.axioms(w).forEach(g -> {

		 if (g instanceof OWLSubClassOfAxiomImpl) {
			 OWLSubClassOfAxiomImpl g2 = (OWLSubClassOfAxiomImpl) g;
			 System.out.println(g2);
			 System.out.println(g2.getSubClass() + "-" + g2.getSubClass().getClass());
			 System.out.println(g2.getSuperClass() + "-" + g2.getSuperClass().getClass());
			 if (g2.getSuperClass() instanceof OWLObjectAllValuesFromImpl) {
				 OWLObjectAllValuesFromImpl allValuesOf = (OWLObjectAllValuesFromImpl) g2.getSuperClass();
				 System.out.println("aaaaaaaaaaa" + allValuesOf.getProperty().asOWLObjectProperty().getIRI().getFragment());
				 System.out.println("aaaaaaaaaaa" + allValuesOf.getFiller() + "type of fille" + allValuesOf.getFiller().getClass());
			 }
			// g2.acc
		 }
		 System.out.println(g.getClass());
	//	 System.out.println(g.get);
	 });
	 
	 // Limit LimitHomePageLength 
	 OWLClass w2 = Ontology.ontology.getOWLOntologyManager().getOWLDataFactory().getOWLClass(IRI.create("http://www.semanticweb.org/tarmo/ontologies/2016/3/wug-ont#05-07_LimitHomePageLength"));
	 // print individuals
	 System.out.println("Individuals");
	 NodeSet<OWLNamedIndividual> instances = Ontology.reasoner.getInstances(w2, true);
	 
	 Guideline guideline = new Guideline();
	 
	 instances.forEach(f -> {
		 f.forEach(individual -> {
			 Ontology.ontology.axioms(individual).forEach(axiom -> {
				 System.out.println(axiom);
				 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
					 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
					 System.out.println(dataProperty.getProperty().asOWLDataProperty().getIRI());
					 System.out.println(dataProperty.getObject().getDatatype());
					 System.out.println(dataProperty.getObject().getLiteral());
				 }
				 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
					 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;
					 System.out.println(objectProperty.getProperty().asOWLObjectProperty().getIRI());
					 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI());
				 }
				 System.out.println(axiom.getClass());
			 });
		 });
	 });
	}
}
