package ee.ttu.usability.domain.element.link;

import lombok.Data;
import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.element.UsabilityGuideline;

@Data
public class Graphic extends UsabilityGuideline {

	private AlternativeText alternativeText;
	
}
