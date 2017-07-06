package ee.ttu.usability.domain.element.link;

import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import lombok.Data;

@Data
public class Area extends UsabilityGuideline {

	private AlternativeText alternativeText;

}
