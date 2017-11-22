package ee.ttu.usability.guideliner.estimation.result;

import lombok.Data;

@Data
public class FailedElement {
	private String type;
	private String text;
	private String description;
	private String pathToElement;
}
