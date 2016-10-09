package usability;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.page.UIPage;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import usability.estimation.LinkAdaptor;
import usability.estimation.ParagrapgAdaptor;
import usability.estimation.UIPageAdaptor;
import usability.estimation.result.EvaluationReport;
import usability.estimation.result.EvaluationResult;

@Service
public class OntologyEvaluatorService {

	private OntologyRepository ontologyRepository;

	private GuildelineBuilderService builder;

	private WebDriver driver;
	
	@Autowired
	public OntologyEvaluatorService(OntologyRepository ontologyRepository,
			GuildelineBuilderService builder) {
		this.ontologyRepository = ontologyRepository;
		this.builder = builder;
		driver = initialiseDriver(); 
	}

	public EvaluationResult evaluate(OWLClass guideline, String url) {
		// get guideline
		GuidelinetElement guidelineElement = fillWithGuidelineElement(guideline);

//		WebDriver driver = initialiseDriver();

		driver.get(url);

		if (guidelineElement instanceof UIPage) {
			UIPageAdaptor adaptor = new UIPageAdaptor();
			adaptor.setDriver(driver);
			return adaptor.execute((UIPage) guidelineElement);
		}
		if (guidelineElement instanceof Link) {
			try {
				LinkAdaptor adaptor = new LinkAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Link) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof Paragraph) {
			try {
				ParagrapgAdaptor adaptor = new ParagrapgAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Paragraph) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		driver.close();
		return null;
	}

	private WebDriver initialiseDriver() {
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		// return new ChromeDriver();
		return new FirefoxDriver();
	}

	public GuidelinetElement fillWithGuidelineElement(OWLClass guideline) {
		GuidelinetElement guidelineElement = this
				.getGuidelineElement(guideline);

		// fill based on instances
		// TODO should filling support multiple instances
		NodeSet<OWLNamedIndividual> instances = ontologyRepository
				.getIndividuals(guideline);
		builder.fillGuideline(instances, guidelineElement);

		return guidelineElement;
	}

	// TODO refactor
	@SuppressWarnings("deprecation")
	public GuidelinetElement getGuidelineElement(OWLClass selectedGuideline) {
		for (OWLClassAxiom g : OntologyRepository.ontology
				.getAxioms(selectedGuideline)) {
			if (g instanceof OWLSubClassOfAxiomImpl) {
				OWLSubClassOfAxiomImpl g2 = (OWLSubClassOfAxiomImpl) g;
				// System.out.println("eeeeeeeeeeeeeee");
				// System.out.println(g2.getSuperClass());
				if (g2.getSuperClass() instanceof OWLObjectAllValuesFromImpl) {
					OWLObjectAllValuesFromImpl allValuesOf = (OWLObjectAllValuesFromImpl) g2
							.getSuperClass();
					// System.out.println("aaaaaaaaaaa" +
					// allValuesOf.getProperty().asOWLObjectProperty().getIRI().getFragment());
					// System.out.println("aaaaaaaaaaa" +
					// allValuesOf.getFiller() + "type of fille" +
					// allValuesOf.getFiller().getClass());
				}
				if (g2.getSuperClass() instanceof OWLObjectSomeValuesFrom) {
					OWLObjectSomeValuesFrom someValueOf = (OWLObjectSomeValuesFrom) g2
							.getSuperClass();
					if ("hasGuidelineElement".equalsIgnoreCase(someValueOf
							.getProperty().asOWLObjectProperty().getIRI()
							.getShortForm())) {
						if ("UIPage".equalsIgnoreCase(someValueOf.getFiller()
								.asOWLClass().getIRI().getShortForm())) {
							return new UIPage();
						} else if ("Link".equalsIgnoreCase(someValueOf
								.getFiller().asOWLClass().getIRI()
								.getShortForm())) {
							return new Link();
						} else if ("Paragraph".equalsIgnoreCase(someValueOf
								.getFiller().asOWLClass().getIRI()
								.getShortForm())) {
							return new Paragraph();
						}
					}
					// System.out.println("aaaaaaaaaaa" +
					// someValueOf.getProperty().asOWLObjectProperty().getIRI().getShortForm());
					// System.out.println("aaaaaaaaaaa" +
					// someValueOf.getFiller().asOWLClass().getIRI().getShortForm()
					// + "type of fille" + someValueOf.getFiller().getClass());
				}
				if (g2.getSuperClass() instanceof OWLObjectAllValuesFrom) {
					OWLObjectAllValuesFrom valueOf = (OWLObjectAllValuesFrom) g2
							.getSuperClass();
					if ("Link".equalsIgnoreCase(valueOf
							.getFiller().asOWLClass().getIRI()
							.getShortForm())) {
						return new Link();
					}
				}
			}

			// System.out.println(g.getClass());
		}
		return null;
	}

}
