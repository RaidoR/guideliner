package usability;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.page.UIPage;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import usability.estimation.UIPageAdaptor;
import usability.estimation.result.EvaluationReport;
import usability.estimation.result.EvaluationResult;

@Service
public class OntologyEvaluatorService {

	@Autowired
	private OntologyRepository ontologyRepository;
	
	@Autowired
	private GuildelineBuilderService builder;
	
	public EvaluationResult evaluate(OWLClass guideline) {
		// get guideline
		GuidelinetElement guidelineElement = this.getGuidelineElement(guideline);
		
		// fill based on instances
		// TODO should filling support multiple instances
		NodeSet<OWLNamedIndividual> instances = ontologyRepository.getIndividuals(guideline);
		builder.fillGuideline(instances, guidelineElement);
		
		WebDriver driver = initialiseDriver();
		
		driver.get("https://www.etis.ee/Portal/Projects/Index");
		
		EvaluationResult result = new EvaluationResult();
		
		if (guidelineElement instanceof UIPage) {
			UIPageAdaptor adaptor = new UIPageAdaptor();
			adaptor.setDriver(driver);
			return adaptor.execute((UIPage) guidelineElement);
		}
		driver.close();
		return null;
	}
	
    private WebDriver initialiseDriver() {
	    System.setProperty("webdriver.chrome.driver", "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
	    return new ChromeDriver();
    }
	
	// TODO refactor
	public GuidelinetElement getGuidelineElement(OWLClass selectedGuideline) {
		for (OWLClassAxiom g : OntologyRepository.ontology.getAxioms(selectedGuideline)) {
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