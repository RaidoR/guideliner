package usability.estimation.result;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EvaluationResult {

	private ElementType elementType;
	
	private ResultType result;
	
	private List<FailedElement> failedElements = new ArrayList<FailedElement>();

	private Guideline guideline;
	
	private String description;
	
}
