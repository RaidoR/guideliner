package ee.ttu.usability.domain.element.form;

import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.attribute.Color;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import lombok.Data;

@Data
public class Input extends UsabilityGuideline {

	private AlternativeText alternativeText;

	private Boolean isSelected;


	
}
