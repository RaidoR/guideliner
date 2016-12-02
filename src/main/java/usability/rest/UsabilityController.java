package usability.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import usability.OntologyEvaluatorService;
import usability.OntologyRepository;
import usability.OntologyService;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.FailedElement;
import usability.estimation.result.Guideline;
import usability.estimation.result.ResultType;
import usability.estimation.utils.Configuration;

/**
 * @author jevgeni.marenkov
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class UsabilityController {
	
    private OntologyEvaluatorService evaluatorService;
    
    private OntologyRepository ontologyRepository;
    
    private OntologyService ontologyService;
    
    @Autowired
    public UsabilityController(OntologyEvaluatorService evaluatorService, OntologyRepository ontologyRepository, OntologyService ontologyService) {
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
    	Guideline guideline = ontologyService.fillWithGuidelineInformation(selectedGuideline, "03-03_DoNotUseColorAloneToConveyInformation");
    	result.setGuideline(guideline);
    	results.add(result);
    	
    	OWLClass selectedGuideline2 = ontologyRepository.loadClass("05-07_LimitHomePageLength");
    	EvaluationResult result2 = evaluatorService.evaluate(selectedGuideline2, webURL);
    	Guideline guideline2 = ontologyService.fillWithGuidelineInformation(selectedGuideline2, "05-07_LimitHomePageLength");
    	result2.setGuideline(guideline2); 
    	results.add(result2);
		
    	EvaluationResult result3 = new EvaluationResult();
    	result3.setResult(ResultType.SUCCESS);
    	selectedGuideline = ontologyRepository.loadClass("11-01_UseBlackTextonPlainHighContrastBackgrounds");
    	Guideline guideline3 = ontologyService.fillWithGuidelineInformation(selectedGuideline, "11-01_UseBlackTextonPlainHighContrastBackgrounds");
    	result3.setGuideline(guideline3);
    	results.add(result3);
    	
    	
    	EvaluationResult result4 = new EvaluationResult();
    	result4.setResult(ResultType.SUCCESS);
    	selectedGuideline = ontologyRepository.loadClass("10-11_UseAppropriateTextLinkLengths");
    	Guideline guideline4 = ontologyService.fillWithGuidelineInformation(selectedGuideline, "10-11_UseAppropriateTextLinkLengths");
    	result4.setGuideline(guideline4);
    	results.add(result4);
    	
    	EvaluationResult result5 = new EvaluationResult();
    	result5.setResult(ResultType.SUCCESS);
    	selectedGuideline = ontologyRepository.loadClass("08-01_EliminateHorizontalScrolling");
    	Guideline guideline5 = ontologyService.fillWithGuidelineInformation(selectedGuideline, "08-01_EliminateHorizontalScrolling");
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
    	
    	OWLClass selectedGuideline2 = ontologyRepository.loadClass("3wcag-8-15_CheckThatPageHasLinkToFrontPage");
    	
    	EvaluationResult result2 = evaluatorService.evaluate(selectedGuideline2, webURL);
    	
        return Arrays.asList(result2);
    }
    
    @RequestMapping("/retrieve")
    public List<Guideline> retrieveAllGuidelines() {	
        return ontologyService.getAllUsabilityGuidelinesHardCoded();
    }
    
    @RequestMapping("/results")
    public List<EvaluationResult> getAllResults() {	
        return ontologyService.getAllUsabilityGuidelinesHardCodedWithResults();
    }

    // TODO move to setting contoller
    @RequestMapping(value = "/downloadImage", method = RequestMethod.GET)
    public void downloadImage(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String name) throws IOException {

        int BUFFER_SIZE = 4096;

        // get the absolute path of the application

        ServletContext context = request.getSession().getServletContext();

        String appPath = context.getRealPath("");


        // construct the complete absolute path of the file


        File downloadFile = new File(Configuration.reportsFolder + "/" + name);

        FileInputStream inputStream = new FileInputStream(downloadFile);


        // get MIME type of the file

        String mimeType = context.getMimeType(Configuration.reportsFolder + "/screen1474980098829.0.png" + name);

        if (mimeType == null) {

            // set to binary type if MIME mapping not found

            mimeType = "application/octet-stream";

        }

        // check the mime type

        System.out.println("MIME type: " + mimeType);


        // set content attributes for the response

        response.setContentType(mimeType);

        response.setContentLength((int) downloadFile.length());


        // set headers for the response

        String headerKey = "Content-Disposition";

        String headerValue = String.format("attachment; filename=\"%s\"",

                downloadFile.getName());

        response.setHeader(headerKey, headerValue);


        // get output stream of the response

        OutputStream outStream = response.getOutputStream();


        byte[] buffer = new byte[BUFFER_SIZE];

        int bytesRead = -1;


        // write bytes read from the input stream into the output stream

        while ((bytesRead = inputStream.read(buffer)) != -1) {

            outStream.write(buffer, 0, bytesRead);

        }


        inputStream.close();

        outStream.close();


    }
}
