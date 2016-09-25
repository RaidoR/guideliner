package usability;

import java.util.Optional;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.usability.domain.attribute.Contrast;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;
import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import jevg.ee.ttu.Guideline;
import jevg.ee.ttu.dataproperty.Unit;

@Service
public class GuildelineBuilderService {
	
	private OntologyRepository ontologyRepository;
	
	@Autowired
	public GuildelineBuilderService(OntologyRepository ontologyRepository) {
		this.ontologyRepository = ontologyRepository;
	}
	
	// TODO somehow combine fillGuideline and transformToObject together
	@SuppressWarnings("static-access")
	public void fillGuideline(NodeSet<OWLNamedIndividual> instances, GuidelinetElement guidelineElement) {
		 instances.forEach(f -> {
			 f.forEach(individual -> {
				 ontologyRepository.ontology.axioms(individual).forEach(axiom -> {
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
	}
	
	public void transformToObject(OWLNamedIndividual individual, GuidelinetElement element) {
		ontologyRepository.ontology.axioms(individual).forEach(axiom -> {
			 this.fillWithProperties(axiom, element);
		 });
	}
	
	private void printOwlDataProperty(OWLDataPropertyAssertionAxiomImpl dataProperty) {
		 System.out.println(dataProperty.getProperty().asOWLDataProperty().getIRI());
		 System.out.println(dataProperty.getObject().getDatatype());
		 System.out.println(dataProperty.getObject().getLiteral());
	}
	
	public void fillWithProperties(OWLIndividualAxiom axiom, GuidelinetElement element) {
		 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
			 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
			 this.fillWithDataProperty(element, dataProperty);
		 }
		 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
			 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;
			 this.fillWithObjectProperty(element, objectProperty);
		 }
	}
	
	public void fillWithObjectProperty(GuidelinetElement element, OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		 if ("hasUnit".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 if ("Word".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.WORD);
				 printOwlObjectProperty(objectProperty);
			 } else if ("Line".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.LINE);
				 printOwlObjectProperty(objectProperty);
			 }
		 }
		 if ("hasAttribute".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
		     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), element);
		 }	 
	}

	public void fillWithDataProperty(GuidelinetElement element, OWLDataPropertyAssertionAxiomImpl dataProperty) {
		switch (dataProperty.getProperty().asOWLDataProperty().getIRI().getShortForm()) {
			case "hasContentLength" : 
				 if ("integer".equals(dataProperty.getObject().getDatatype().getIRI().getShortForm())) {
					 element.setContentLength(new Integer(dataProperty.getObject().getLiteral()));
				 }
				 break;
			case "hasContrast" :
				Contrast contrast = new Contrast();
				contrast.setContrast(new Integer(dataProperty.getObject().getLiteral()));
				element.setContrast(contrast);
				break;		
			case "hasScroll" :
				Optional<OWLClassExpression> entityTypeOfIndividual = ontologyRepository.getEntityTypeOfIndividual(dataProperty.getSubject());
				if ("HScroll".equals(((OWLClassImpl) entityTypeOfIndividual.get()).getIRI().getShortForm())) {
					HorizontalScroll scroll = new HorizontalScroll();
					scroll.setValue(new Integer(dataProperty.getObject().getLiteral()));
					if (element instanceof UIPage) {
						((UIPage) element).setHorizontalScroll(scroll);
					}
				}
				break;		
		}
	}
	
	public void getTest() {
		
	}
	
	public static void printOwlObjectProperty(OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		 System.out.println(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()));
	}
	
}
