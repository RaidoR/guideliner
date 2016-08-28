package usability.estimation.result;

import lombok.Data;

@Data
public class EvaluationResult {

	private ElementType elementType;
	
	private ResultType result;
	
	private String description;
	
}
