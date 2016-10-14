package ee.ttu.usability.domain.element.link;

import lombok.Data;
import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.attribute.Label;
import ee.ttu.usability.domain.element.GuidelinetElement;

@Data
public class Form extends GuidelinetElement {
	
	private AlternativeText alternativeText;
	
	private Label label;
	
}
