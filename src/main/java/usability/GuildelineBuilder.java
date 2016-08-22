package usability;

import ee.ttu.usability.domain.element.GuidelinetElement;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import jevg.ee.ttu.Guideline;
import jevg.ee.ttu.dataproperty.Unit;

public class GuildelineBuilder {

	public void fillWithObjectProperty(GuidelinetElement element, OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		 if ("hasUnit".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 if ("Word".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.WORD);
				 printOwlObjectProperty(objectProperty);
			 }
		 }
	}

	public void fillWithDataProperty(GuidelinetElement element, OWLDataPropertyAssertionAxiomImpl dataProperty) {
		 if ("hasContentLength".equals(dataProperty.getProperty().asOWLDataProperty().getIRI().getShortForm())) {
			 if ("integer".equals(dataProperty.getObject().getDatatype().getIRI().getShortForm())) {
				 element.setContentLength(new Integer(dataProperty.getObject().getLiteral()));
			 }
		 }
	}
	
	
	public static void printOwlObjectProperty(OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		 System.out.println(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()));
	}
}
