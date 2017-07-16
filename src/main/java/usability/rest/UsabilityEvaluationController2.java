package usability.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.Guideline;

import java.util.List;

/**
 * Controller for retrieving usability guidelines and triggering the process of evaluation
 */
public interface UsabilityEvaluationController2 {

/**
 * @return all available categories of usability guidelines
 */
@GetMapping("/usability/categories")
List<String> getAllCategories();

/**
 * @param category Category of usability guidelines
 * @return Usability guidelines corresponding to the category provided
 */
@GetMapping("/usability/categories/{category}")
List<Guideline> getGuidelinesByCategory(@PathVariable("category") String category);

/**
 * @param category Category of usability guidelines
 * @param url URL of Web Application to be evaluated
 * @return Evaluation results
 */
@PostMapping("/usability/evaluation/category/{category}")
List<EvaluationResult> evaluateAllGuidelinesByCategory(@PathVariable("category") String category, @RequestParam(value="url") String url);

/**
 * @param guidelineCode Code of usability guidelines
 * @param url URL of Web Application to be evaluated
 * @return Result of the evaluation
 */
@PostMapping("/usability/evaluation/{code}")
EvaluationResult evaluateOneByGuidelineCode(@PathVariable("guidelineCode") String guidelineCode, @RequestParam(value="url") String url);

}
