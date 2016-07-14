package ee.ttu.usability.domain.element;

import lombok.Getter;
import lombok.Setter;
import ee.ttu.usability.domain.attribute.Contrast;
import ee.ttu.usability.domain.attribute.Distance;
import ee.ttu.usability.domain.attribute.Height;
import ee.ttu.usability.domain.attribute.ProhibitedWordType;
import ee.ttu.usability.domain.attribute.Width;

@Setter
@Getter
public class GuidelinetElement {

	private Contrast contrast;
	
	private Distance distance;
	
	private ProhibitedWordType prohibitedWordType;
	
	private Height height;
	
	private Width width;

}
