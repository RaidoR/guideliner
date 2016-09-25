package usability.estimation.result;

import java.util.List;

import lombok.Data;

@Data
public class EvaluationResult {

	private ElementType elementType;
	
	private ResultType result;
	
	private String description;
	
	private List<FailedElement> failedElements;
	
}
