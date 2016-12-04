package ee.ttu.usability.domain.element.link;

import ee.ttu.usability.domain.attribute.AlternativeText;
import ee.ttu.usability.domain.element.GuidelinetElement;
import lombok.Data;

@Data
public class Area extends GuidelinetElement {

	private AlternativeText alternativeText;

}
