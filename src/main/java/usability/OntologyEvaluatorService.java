package usability;

import ee.ttu.usability.domain.element.form.FormElementLabel;
import ee.ttu.usability.domain.element.form.Input;
import ee.ttu.usability.domain.element.form.Radio;
import lombok.extern.slf4j.Slf4j;
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

import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import usability.estimation.*;
import usability.estimation.result.EvaluationResult;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import ee.ttu.usability.domain.element.content.Paragraph;
import ee.ttu.usability.domain.element.link.Area;
import ee.ttu.usability.domain.element.link.Button;
import ee.ttu.usability.domain.element.link.Form;
import ee.ttu.usability.domain.element.link.Graphic;
import ee.ttu.usability.domain.element.link.Link;
import ee.ttu.usability.domain.element.link.Multimedia;
import ee.ttu.usability.domain.element.link.NumberedList;
import ee.ttu.usability.domain.element.navigation.Navigation;
import ee.ttu.usability.domain.page.UIPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OntologyEvaluatorService {

	private OntologyRepository ontologyRepository;

	private GuildelineBuilderService builder;

	private OntologyService ontologyService;

	private WebDriver driver;

	private List<AbstractAdaptor> adaptors;
	
	@Autowired
	public OntologyEvaluatorService(OntologyRepository ontologyRepository,
			GuildelineBuilderService builder, OntologyService ontologyService) {
		this.ontologyRepository = ontologyRepository;
		this.builder = builder;
		this.ontologyService = ontologyService;
	}

	public List<EvaluationResult>  evaluate(String category, String url) {
		if (driver == null) {
			driver = initialiseDriver();
		}

		driver.get(url);

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			//Handle exception
		}

		List<EvaluationResult> results = new ArrayList<>();


		OntologyRepository.reasoner.getSubClasses(ontologyRepository.loadClass(category))
				.entities()
				.filter(c -> !c.getIRI().getShortForm().equals("Nothing"))
				.forEach(c -> {
					log.info("Starting evaluating guideline: " + c.getIRI().getIRIString());
					EvaluationResult result = evaluate(c, url);
					if (result != null) {
						result.setGuideline(ontologyService.createGuideline(c));
						results.add(result);
					}
					log.info("Finishing evaluating guideline: " + c.getIRI().getIRIString());
				});
		Collections.sort(results, Comparator.comparing(o -> o.getResult().name()));
		return results;
	}

	public EvaluationResult evaluateByName(String guideline, String url) {
		return evaluate(ontologyRepository.loadClass(guideline), url, true);
	}

	public EvaluationResult evaluate(OWLClass guideline, String url) {
		return evaluate(guideline, url, false);
	}

	public EvaluationResult evaluate(OWLClass guideline, String url, boolean openUrl) {
		if (driver == null) {
			driver = initialiseDriver();
		}
		// get guideline
		UsabilityGuideline guidelineElement = fillGuidelines(guideline);

//		WebDriver driver = initialiseDriver();

		if (openUrl) {
			driver.get(url);
		}

		guidelineElement.setUrl(url);
		
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
				ParagraphAdaptor adaptor = new ParagraphAdaptor();
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
		if (guidelineElement instanceof Button) {
			try {
				ButtonAdaptor adaptor = new ButtonAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Button) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof  NumberedList) {
			try {
				NumberedListAdaptor adaptor = new NumberedListAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((NumberedList) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (guidelineElement instanceof  Area) {
			try {
				AreaAdaptor adaptor = new AreaAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Area) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (guidelineElement instanceof  FormElementLabel) {
			try {
				FormElementLabelAdaptor adaptor = new FormElementLabelAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((FormElementLabel) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (guidelineElement instanceof  Input) {
			try {
				InputAdaptor adaptor = new InputAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Input) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		//	driver.close();
		return null;
	}

	private WebDriver initialiseDriver() {
//
//		 System.setProperty("webdriver.chrome.driver",
//		 "..\\chrome\\chromedriver.exe");
//		 return new ChromeDriver();
	  return new FirefoxDriver();
	}

	public WebDriver initialiseDriverIfNotInitialised(String url) {
//
		 System.setProperty("webdriver.chrome.driver",
		 "..\\chrome\\chromedriver.exe");
		 this.driver = new ChromeDriver();
		driver.get(url);
		return driver;
//		if (this.driver == null) {
//			this.driver =  new FirefoxDriver();
//			driver.get(url);
//		}
//
//		return driver;
	}

	public void initialiseDriverIfNotInitialised(String url, String url2, String url3) {

	}



	public UsabilityGuideline fillGuidelines(OWLClass guideline) {
		UsabilityGuideline guidelineElement = this.getGuidelineElement(guideline);
		NodeSet<OWLNamedIndividual> instances = ontologyRepository.getIndividuals(guideline);
		builder.fillGuideline(instances, guidelineElement);
		return guidelineElement;
	}

	// TODO refactor
	@SuppressWarnings("deprecation")
	public UsabilityGuideline getGuidelineElement(OWLClass selectedGuideline) {
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

					// TODO make more readable
					if ("hasDeviceType".equalsIgnoreCase(valueOf
							.getProperty().asOWLObjectProperty().getIRI()
							.getShortForm())) {
						continue;
					}

					return transform(valueOf.getFiller().asOWLClass().getIRI().getShortForm());
				}
			}
		}
		return null;
	}
	
	private UsabilityGuideline transform(String ontologyElement) {
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
		case "Button":
			return new Button();
		case "NumberedList":
			return new NumberedList();
		case "Area":
			return new Area();
		case "FormElementLabel":
			return new FormElementLabel();
		case "Input":
			return new Input();
		case "Radio":
			return new Radio();
		default:
			throw new RuntimeException("Cannot find the class for " + ontologyElement);
		}
	}

}
