package usability;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import usability.estimation.FormAdaptor;
import usability.estimation.GraphicAdaptor;
import usability.estimation.LinkAdaptor;
import usability.estimation.MultimediaAdaptor;
import usability.estimation.NavigationAdaptor;
import usability.estimation.ParagrapgAdaptor;
import usability.estimation.UIPageAdaptor;
import usability.estimation.result.EvaluationResult;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Form;
import ee.ttu.usability.domain.element.link.Graphic;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.element.link.Multimedia;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;

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
			try {
				UIPageAdaptor adaptor = new UIPageAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((UIPage) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
		if (guidelineElement instanceof Form) {
			try {
				FormAdaptor adaptor = new FormAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Form) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof Graphic) {
			try {
				GraphicAdaptor adaptor = new GraphicAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Graphic) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof Navigation) {
			try {
				NavigationAdaptor adaptor = new NavigationAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Navigation) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof Multimedia) {
			try {
				MultimediaAdaptor adaptor = new MultimediaAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Multimedia) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	//	driver.close();
		return null;
	}

	private WebDriver initialiseDriver() {
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\jevgeni.marenkov\\Desktop\\yli\\chrome\\chromedriver.exe");
		// return new ChromeDriver();
		return new FirefoxDriver();
	}

	public GuidelinetElement fillWithGuidelineElement(OWLClass guideline) {
		GuidelinetElement guidelineElement = this.getGuidelineElement(guideline);

		NodeSet<OWLNamedIndividual> instances = ontologyRepository.getIndividuals(guideline);
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
				if (g2.getSuperClass() instanceof OWLObjectSomeValuesFrom) {
					OWLObjectSomeValuesFrom someValueOf = (OWLObjectSomeValuesFrom) g2
							.getSuperClass();
					if ("hasGuidelineElement".equalsIgnoreCase(someValueOf
							.getProperty().asOWLObjectProperty().getIRI()
							.getShortForm())) {
						return transform(someValueOf.getFiller().asOWLClass().getIRI().getShortForm());
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
					return transform(valueOf.getFiller().asOWLClass().getIRI().getShortForm());
				}
			}
		}
		return null;
	}
	
	private GuidelinetElement transform(String ontologyElement) {
		switch (ontologyElement) {
		case "UIPage":
			return new UIPage();
		case "Link":
			return new Link();
		case "Paragraph":
			return new Paragraph();
		case "Form":
			return new Form();
		case "Graphic":
			return new Graphic();
		case "Nav":
			return new Navigation();
		case "MultimediaContent":
			return new Multimedia();
		default:
			return null;
		}
	}

}
