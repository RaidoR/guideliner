package ee.ttu.usability.guideliner.service.impl;

import ee.ttu.usability.guideliner.domain.element.form.CheckBox;
import ee.ttu.usability.guideliner.domain.element.form.FormElementLabel;
import ee.ttu.usability.guideliner.domain.element.form.Input;
import ee.ttu.usability.guideliner.domain.element.form.Radio;
import ee.ttu.usability.guideliner.estimation.adaptor.*;
import ee.ttu.usability.guideliner.repository.OntologyRepository;
import ee.ttu.usability.guideliner.domain.element.UsabilityGuideline;
import ee.ttu.usability.guideliner.domain.element.content.Paragraph;
import ee.ttu.usability.guideliner.domain.element.navigation.Navigation;
import ee.ttu.usability.guideliner.domain.page.UIPage;
import ee.ttu.usability.guideliner.estimation.result.EvaluationResult;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import ee.ttu.usability.guideliner.domain.element.link.Area;
import ee.ttu.usability.guideliner.domain.element.link.Button;
import ee.ttu.usability.guideliner.domain.element.link.Form;
import ee.ttu.usability.guideliner.domain.element.link.Graphic;
import ee.ttu.usability.guideliner.domain.element.link.Link;
import ee.ttu.usability.guideliner.domain.element.link.Multimedia;
import ee.ttu.usability.guideliner.domain.element.link.NumberedList;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OntologyEvaluatorService {

	public static Integer DEFAULT_HEIGHT = 800;
	public static Integer DEFAULT_WIDTH = 1500;

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
		List<EvaluationResult> results = new ArrayList<>();

		if ("MobileUsabilityGuideline".equals(category)) {
			this.initializeForMobile();
		} else {
			this.initialiseDriver();
		}

		log.info("Opening URL " + url);

		driver.get(url);

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("Starting evaluation: " + url);
		final AtomicInteger i = new AtomicInteger(1);
		OntologyRepository.reasoner.getSubClasses(ontologyRepository.loadClass(category))
				.entities()
				.filter(c -> !c.getIRI().getShortForm().equals("Nothing"))
				.forEach(c -> {
					i.addAndGet(1);
					LocalDateTime startTime = LocalDateTime.now();
					log.info("Starting evaluating guideline - "+i+": " + c.getIRI().getIRIString());
					EvaluationResult result = evaluate(c, url);
					if (result != null) {
						result.setGuideline(ontologyService.createGuideline(c));
						results.add(result);
					}
					log.info("Finishing evaluating guideline: " + c.getIRI().getIRIString() + ". Evaluation took:" + startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS));
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

		if (guidelineElement instanceof FormElementLabel) {
			try {
				FormElementLabelAdaptor adaptor = new FormElementLabelAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((FormElementLabel) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (guidelineElement instanceof Input) {
			try {
				InputAdaptor adaptor = new InputAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Input) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (guidelineElement instanceof Radio) {
			try {
				RadioAdaptor adaptor = new RadioAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((Radio) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (guidelineElement instanceof  CheckBox) {
			try {
				CheckButtonAdaptor adaptor = new CheckButtonAdaptor();
				adaptor.setDriver(driver);
				return adaptor.execute((CheckBox) guidelineElement);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		//	driver.close();
		return null;
	}

	public WebDriver initialiseDriver() {
		// TODO add some logic
//		FirefoxProfile ffprofile = new FirefoxProfile();
//		ffprofile.setPreference("general.useragent.override", "iPhone");
//		driver = new FirefoxDriver(ffprofile);
//		driver.manage().window().setSize(new Dimension(400,800));

//				 System.setProperty("webdriver.chrome.driver",
//		 "..\\chrome\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//


		// desctop
//		driver = new FirefoxDriver();
		driver = new HtmlUnitDriver( true );
		driver.manage().window().setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));

		return driver;
	}

	public WebDriver initializeForMobile() {
//		FirefoxProfile ffprofile = new FirefoxProfile();
//		ffprofile.setPreference("general.useragent.override", "iPhone");
//		driver = new FirefoxDriver(ffprofile);

//		DesiredCapabilities capabilities = DesiredCapabilities.iphone();
		DesiredCapabilities capabilities = new DesiredCapabilities(  );
		capabilities.setBrowserName( "iPhone" );
		capabilities.setVersion( "" );
		capabilities.setPlatform( Platform.ANY );
		driver = new HtmlUnitDriver( capabilities );
		driver.manage().window().setSize(new Dimension(400,800));
		return driver;
	}

	public WebDriver initialiseDriverIfNotInitialised(String url) {
//
		if (this.driver != null) {
			return this.driver;
		}
		driver = new HtmlUnitDriver(true);
//		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		driver.get(url);
		return driver;
//		 System.setProperty("webdriver.chrome.driver",
//		 "..\\chrome\\chromedriver.exe");
//		 this.driver = new ChromeDriver();
//		driver.get(url);
//		return driver;
//		if (this.driver == null) {
//			this.driver =  new FirefoxDriver();
//			driver.get(url);
//		}
//
//		return driver;
	}

	public void closeDriver() {
		if (this.driver != null) {
			driver.close();
		}
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
			case "CheckBox":
				return new CheckBox();
			default:
				throw new RuntimeException("Cannot find the class for " + ontologyElement);
		}
	}

}
