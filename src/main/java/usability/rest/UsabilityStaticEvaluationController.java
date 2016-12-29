package usability.rest;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.Guideline;
import usability.estimation.result.ResultType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
public class UsabilityStaticEvaluationController {

    private OntologyEvaluatorService evaluatorService;

    private OntologyRepository ontologyRepository;

    private OntologyService ontologyService;

    @Autowired
    public UsabilityStaticEvaluationController(OntologyEvaluatorService evaluatorService, OntologyRepository ontologyRepository, OntologyService ontologyService) {
        this.evaluatorService = evaluatorService;
        this.ontologyRepository = ontologyRepository;
        this.ontologyService = ontologyService;
    }

    @RequestMapping("/usability")
    public List<EvaluationResult> sampleExample(@RequestParam(value="guideline", defaultValue="05-07_LimitHomePageLength") String name,
                                                @RequestParam(value="url", defaultValue="http://www.etis.ee") String webURL) {
        // http://localhost:8080/usability
        // OWLClass selectedGuideline = ontologyRepository.loadClass("05-07_LimitHomePageLength");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("10-11_UseAppropriateTextLinkLengths");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("08-01_EliminateHorizontalScrolling");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-02_DesignFormsUsingAssistiveTechnologies");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("14-09_LimitTheUseOfImages");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("15-07_LimitTheNumberOfWordsAndSentences");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("16-05_MinimizeTheNumberOfClicksOrPages");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-05_ProvideTextEquivalentsForNonTextElements");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("06-10_SetAppropriatePageLengths");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("06-08_UseFluidLayouts");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("07-08_KeepNavigationOnlyPagesShort");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-09_ProvideClientSideImageMaps");

        List<EvaluationResult> results = new ArrayList<EvaluationResult>();

        OWLClass selectedGuideline = ontologyRepository.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
        EvaluationResult result = evaluatorService.evaluate(selectedGuideline, webURL);
        Guideline guideline = ontologyService.createGuideline(selectedGuideline);
        result.setGuideline(guideline);
        results.add(result);

        OWLClass selectedGuideline2 = ontologyRepository.loadClass("05-07_LimitHomePageLength");
        EvaluationResult result2 = evaluatorService.evaluate(selectedGuideline2, webURL);
        Guideline guideline2 = ontologyService.createGuideline(selectedGuideline);
        result2.setGuideline(guideline2);
        results.add(result2);

        EvaluationResult result3 = new EvaluationResult();
        result3.setResult(ResultType.SUCCESS);
        selectedGuideline = ontologyRepository.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
        Guideline guideline3 = ontologyService.createGuideline(selectedGuideline);
        result3.setGuideline(guideline3);
        results.add(result3);


        EvaluationResult result4 = new EvaluationResult();
        result4.setResult(ResultType.SUCCESS);
        selectedGuideline = ontologyRepository.loadClass("10-11_UseAppropriateTextLinkLengths");
        Guideline guideline4 = ontologyService.createGuideline(selectedGuideline);
        result4.setGuideline(guideline4);
        results.add(result4);

        EvaluationResult result5 = new EvaluationResult();
        result5.setResult(ResultType.SUCCESS);
        selectedGuideline = ontologyRepository.loadClass("08-01_EliminateHorizontalScrolling");
        Guideline guideline5 = ontologyService.createGuideline(selectedGuideline);
        result5.setGuideline(guideline5);
        results.add(result5);

        return results;
    }

    @RequestMapping("/usability2")
    public List<EvaluationResult> sampleExample2(@RequestParam(value="guideline", defaultValue="05-07_LimitHomePageLength") String name,
                                                 @RequestParam(value="url", defaultValue="http://www.etis.ee") String webURL) {
        // http://localhost:8080/usability
        // OWLClass selectedGuideline = ontologyRepository.loadClass("05-07_LimitHomePageLength");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("10-11_UseAppropriateTextLinkLengths");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("08-01_EliminateHorizontalScrolling");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-02_DesignFormsUsingAssistiveTechnologies");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("14-09_LimitTheUseOfImages");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("15-07_LimitTheNumberOfWordsAndSentences");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("16-05_MinimizeTheNumberOfClicksOrPages");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-05_ProvideTextEquivalentsForNonTextElements");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("06-10_SetAppropriatePageLengths");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("06-08_UseFluidLayouts");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("07-08_KeepNavigationOnlyPagesShort");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("03-09_ProvideClientSideImageMaps");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("05-03_CreatePositiveFirstImpressionOfYourSite");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("05-06_EnsureTheHomepageLooksLikeHomepage");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("08-04_UsePagingRatherThanScrolling");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("11-05_UseBoldTextSparingly");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("1wcag-8-01_CheckHtmlStyle");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("1wcag-8-10_CheckOnClickIsUsedWithOnKeyDown");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("3wcag-8-15_CheckThatPageHasLinkToFrontPage");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("4wcag-8-16_CheckThatEveryPageHasTitle");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("5wcag-8-19_CheckThatLanguageIsIncludedToHtmlTag");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("6wcag-1-1_AreaShouldHaveAltAttribute");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("7wcag-1-1_ImageShouldHaveAltAttribute");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("8wcag-1-1_AlternativeTextShouldNotHaveProhibitedWords");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("11wcag-1-1_LinkAltTextShouldBeDifferentFromText");
        // OWLClass selectedGuideline = ontologyRepository.loadClass("10wcag-1-1_ImageAltTextShouldNotBeAsFileName");

//    	OWLClass selectedGuideline = ontologyRepository.loadClass("03-03_DoNotUseColorAloneToConveyInformation");
//    	EvaluationResult result = evaluatorService.evaluate(selectedGuideline, webURL);
//    	Guideline guideline = ontologyService.fillWithGuidelineInformation(selectedGuideline, "03-03_DoNotUseColorAloneToConveyInformation");
//    	result.setGuideline(guideline);
//
//    	OWLClass selectedGuideline2 = ontologyRepository.loadClass("05-07_LimitHomePageLength");
//    	EvaluationResult result2 = evaluatorService.evaluate(selectedGuideline2, webURL);
//    	Guideline guideline2 = ontologyService.fillWithGuidelineInformation(selectedGuideline2, "05-07_LimitHomePageLength");
//    	result2.setGuideline(guideline2);
//
//    	return Arrays.asList(result, result2);

        OWLClass selectedGuideline2 = ontologyRepository.loadClass("9wcag-1-1_TextShouldNotContainMultipleSpace");

        EvaluationResult result2 = evaluatorService.evaluate(selectedGuideline2, webURL);

        return Arrays.asList(result2);
    }
}
