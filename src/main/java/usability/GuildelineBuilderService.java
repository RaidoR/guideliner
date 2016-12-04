package usability;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.usability.domain.attribute.AbstractAttribute;
import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.attribute.Contrast;
import ee.ttu.usability.domain.attribute.Height;
import ee.ttu.usability.domain.attribute.Href;
import ee.ttu.usability.domain.attribute.Html;
import ee.ttu.usability.domain.attribute.Label;
import ee.ttu.usability.domain.attribute.Lang;
import ee.ttu.usability.domain.attribute.OnClick;
import ee.ttu.usability.domain.attribute.OnKeyPress;
import ee.ttu.usability.domain.attribute.Title;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.element.link.Area;
import ee.ttu.usability.domain.element.link.Button;
import ee.ttu.usability.domain.element.link.Form;
import ee.ttu.usability.domain.element.link.Graphic;
import ee.ttu.usability.domain.element.link.Multimedia;
import ee.ttu.usability.domain.element.navigation.ID;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.element.navigation.ProhibitedWords;
import ee.ttu.usability.domain.page.Layout;
import ee.ttu.usability.domain.page.LayoutType;
import ee.ttu.usability.domain.page.LoadTime;
import ee.ttu.usability.domain.page.Text;
import ee.ttu.usability.domain.page.UIPage;
import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import ee.ttu.usability.domain.pageattributes.VerticalScroll;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import jevg.ee.ttu.Guideline;
import jevg.ee.ttu.dataproperty.Case;
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
					     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), guidelineElement, null);
					 }
				 });
			 });
		 });
	}
	
	public void transformToObject(OWLNamedIndividual individual, GuidelinetElement element, AbstractAttribute attribute) {
		ontologyRepository.ontology.axioms(individual).forEach(axiom -> {
			 this.fillWithProperties(axiom, element, attribute);
		 });
	}
	
	private void printOwlDataProperty(OWLDataPropertyAssertionAxiomImpl dataProperty) {
		 System.out.println(dataProperty.getProperty().asOWLDataProperty().getIRI());
		 System.out.println(dataProperty.getObject().getDatatype());
		 System.out.println(dataProperty.getObject().getLiteral());
	}
	
	public void fillWithProperties(OWLIndividualAxiom axiom, GuidelinetElement element, AbstractAttribute attribute) {
		 if (axiom instanceof OWLDataPropertyAssertionAxiomImpl) {
			 OWLDataPropertyAssertionAxiomImpl dataProperty = (OWLDataPropertyAssertionAxiomImpl) axiom;
			 this.fillWithDataProperty(element, dataProperty, attribute);
		 }
		 if (axiom instanceof OWLObjectPropertyAssertionAxiomImpl) {
			 OWLObjectPropertyAssertionAxiomImpl objectProperty = (OWLObjectPropertyAssertionAxiomImpl) axiom;
			 this.fillWithObjectProperty(element, objectProperty);
		 }
	}
	
	public void fillWithObjectProperty(GuidelinetElement element, OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		Optional<OWLClassExpression> ent = null;
		 if ("hasUnit".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 if ("Word".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.WORD);
				 printOwlObjectProperty(objectProperty);
			 } else if ("Line".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.LINE);
				 printOwlObjectProperty(objectProperty);
			 } else if ("KByte".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.KBYTE);
				 printOwlObjectProperty(objectProperty);
			 } else if ("WordInSentence".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.WORDS_IN_SENTENCE);
				 printOwlObjectProperty(objectProperty);
			 } else if ("Pixel".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 ent = ontologyRepository.getEntityTypeOfIndividual(objectProperty.getSubject());	 
				 if ("Height".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					 if (element instanceof UIPage) {
							if (((UIPage) element).getHeight() == null)
								((UIPage) element).setHeight(new ee.ttu.usability.domain.pageattributes.Height());
							((UIPage) element).getHeight().setUnit(Unit.PIXCEL);
					 }
				 }
			 } else if ("MiliSecond".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 ent = ontologyRepository.getEntityTypeOfIndividual(objectProperty.getSubject());	 
				 if ("LoadTime".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					 if (element instanceof UIPage) {
							if (((UIPage) element).getLoadTime() == null)
								((UIPage) element).setLoadTime(new LoadTime());
							((UIPage) element).getLoadTime().setUnit(Unit.MILI_SECOND);
					 }
				 }
			 } else if ("Element".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 element.setUnit(Unit.ELEMENT);
				 printOwlObjectProperty(objectProperty);
			 }
		 } 
		 
		 if ("hasLayoutType".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 ent = ontologyRepository.getEntityTypeOfIndividual(objectProperty.getSubject());	 
			 if ("Layout".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Layout layout = new Layout();
					layout.setLayoutType(LayoutType.convertToLayoutType(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm()));
					if (element instanceof UIPage) {
						((UIPage) element).setLayout(layout);
					}
					
			 }
		 }
		 
		 if ("hasCase".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 if ("Bold".equals(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm())) {
				 ent = ontologyRepository.getEntityTypeOfIndividual(objectProperty.getSubject());	 
				 if ("Text".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					 if (element instanceof UIPage) {
							if (((UIPage) element).getText() == null)
								((UIPage) element).setText(new Text());
							((UIPage) element).getText().setCaseType(Case.BOLD);
					 }
				 }
			 }
		 }
		 
		 if ("hasAttribute".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
		     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), element, null);
		 }


		 if ("hasTagAttribute".equals(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm())) {
			 ent = ontologyRepository.getEntityTypeOfIndividual(objectProperty.getSubject());	 
			 if ("Html".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
				 if (element instanceof UIPage) {
					 Html html = new Html();
					 ((UIPage) element).setHtml(html); 
				     transformToObject(((OWLNamedIndividualImpl) objectProperty.getObject()), element, html);
				 }
			 }

		 }
	}

	public void fillWithDataProperty(GuidelinetElement element, OWLDataPropertyAssertionAxiomImpl dataProperty, AbstractAttribute attribute) {
		Optional<OWLClassExpression> ent = ontologyRepository.getEntityTypeOfIndividual(dataProperty.getSubject());

		if (attribute != null) {
			switch (dataProperty.getProperty().asOWLDataProperty().getIRI().getShortForm()) {
				case "isValued" :
					if ("Lang".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
						Lang lang = new Lang();
						lang.setIsValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
						if (attribute instanceof Html) {
							((Html) attribute).setLang(lang);
						}
					}
					break;
				}
			return;
		}
		
		switch (dataProperty.getProperty().asOWLDataProperty().getIRI().getShortForm()) {
			case "hasContentLength" : 
				 ent = ontologyRepository.getEntityTypeOfIndividual(dataProperty.getSubject());	  
				 if ("Height".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					if (element instanceof UIPage) {
						if (((UIPage) element).getHeight() == null)
							((UIPage) element).setHeight(new ee.ttu.usability.domain.pageattributes.Height());
						((UIPage) element).getHeight().setContentLength(new Integer(dataProperty.getObject().getLiteral()));
					}
					
				 } else if ("LoadTime".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
						if (((UIPage) element).getLoadTime() == null)
							((UIPage) element).setLoadTime(new LoadTime());
						((UIPage) element).getLoadTime().setContentLength(new Integer(dataProperty.getObject().getLiteral()));
				 } else if ("Text".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
						if (((UIPage) element).getText() == null)
							((UIPage) element).setText(new Text());
						((UIPage) element).getText().setContentLength(new Integer(dataProperty.getObject().getLiteral()));
				 } else if ("integer".equals(dataProperty.getObject().getDatatype().getIRI().getShortForm())) {
					 element.setContentLength(new Integer(dataProperty.getObject().getLiteral()));
				 } 
				 break;
			case "hasContrast" :
				Contrast contrast = new Contrast();
				contrast.setContrast(new Integer(dataProperty.getObject().getLiteral()));
				element.setContrast(contrast);
				break;		
			case "hasScroll" :
				if ("HScroll".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					HorizontalScroll scroll = new HorizontalScroll();
					scroll.setValue(new Integer(dataProperty.getObject().getLiteral()));
					if (element instanceof UIPage) {
						((UIPage) element).setHorizontalScroll(scroll);
					} else if (element instanceof Navigation) {
						((Navigation) element).setHorizontalScroll(scroll);
					}
					
				}
				if ("VScroll".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					VerticalScroll scroll = new VerticalScroll();
					scroll.setValue(new Integer(dataProperty.getObject().getLiteral()));
					if (element instanceof UIPage) {
						((UIPage) element).setVerticalScroll(scroll);
					}
				}
				break;
			case "hasValue" :
				if ("Id".equalsIgnoreCase(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					ID id = new ID();
					id.setValue(dataProperty.getObject().getLiteral());
					if (element instanceof Navigation) {
						((Navigation) element).setId(id);
					}
				} else if ("ProhibitedWords".equalsIgnoreCase(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					ProhibitedWords words = new ProhibitedWords();
					words.setValue(dataProperty.getObject().getLiteral());
					if (element instanceof UIPage) {
						((UIPage) element).setProhibitedWords(words);
					}
				} else if ("Href".equalsIgnoreCase(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Href href = new Href();
					href.setValue(dataProperty.getObject().getLiteral());
					if (element instanceof UIPage) {
						((UIPage) element).setHref(href);
					}
				}
				break;
			case "isValued" :
				if ("AlternativeText".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					AlternativeText text = new AlternativeText();
					text.setValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof Form) {
						((Form) element).setAlternativeText(text);
					}
					if (element instanceof Multimedia) {
						((Multimedia) element).setAlternativeText(text);
					}
					if (element instanceof Button) {
						((Button) element).setAlternativeText(text);
					}
					if (element instanceof Area) {
						((Area) element).setAlternativeText(text);
					}
					if (element instanceof Graphic) {
						((Graphic) element).setAlternativeText(text);
					}
				}
				if ("Label".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Label label = new Label();
					label.setValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof Form) {
						((Form) element).setLabel(label);
					}
				}
				if ("OnKeyPress".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					OnKeyPress onKey = new OnKeyPress();
					onKey.setValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof Button) {
						((Button) element).setOnKeyPress(onKey);
					}
				}
				if ("OnClick".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					OnClick onClick = new OnClick();
					onClick.setValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof Button) {
						((Button) element).setOnClick(onClick);
					}
				}
				if ("Title".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Title title = new Title();
					title.setIsValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof UIPage) {
						((UIPage) element).setTitle(title);
					}
				}
				if ("Lang".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Lang title = new Lang();
					title.setIsValued(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					System.out.println("ddddddddddddddddddddddddddddddddddddddd");
					System.out.println(((OWLClassImpl) ent.get()).getIRI().getShortForm());
				}
				break;
			case "isValid" :
				if ("Html".equals(((OWLClassImpl) ent.get()).getIRI().getShortForm())) {
					Html html = new Html();
					html.setValid(Boolean.valueOf(dataProperty.getObject().getLiteral()));
					if (element instanceof UIPage) {
						((UIPage) element).setHtml(html);
					}
				}
				break;
		}
	}
	
	
	

	
	public static void printOwlObjectProperty(OWLObjectPropertyAssertionAxiomImpl objectProperty) {
		 System.out.println(objectProperty.getProperty().asOWLObjectProperty().getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()).getIRI().getShortForm());
		 System.out.println(((OWLNamedIndividualImpl) objectProperty.getObject()));
	}
	
}
