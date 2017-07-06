package ee.ttu.usability.domain.element.link;

import lombok.Data;
import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.attribute.OnClick;
import ee.ttu.usability.domain.attribute.OnKeyPress;
import ee.ttu.usability.domain.element.UsabilityGuideline;

@Data
public class Button extends UsabilityGuideline {

	private AlternativeText alternativeText;

    private OnKeyPress onKeyPress;
    
    private OnClick onClick;

}
