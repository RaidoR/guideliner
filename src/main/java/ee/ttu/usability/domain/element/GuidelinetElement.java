package ee.ttu.usability.domain.element;

import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;
import ee.ttu.usability.domain.attribute.Contrast;
import ee.ttu.usability.domain.attribute.Distance;
import ee.ttu.usability.domain.attribute.ProhibitedWordType;
import ee.ttu.usability.domain.attribute.Width;
import ee.ttu.usability.domain.pageattributes.Height;

@Data
public class GuidelinetElement {

	private Contrast contrast;
	
	private Distance distance;
	
	private ProhibitedWordType prohibitedWordType;
	
	private Height height;
	
	private Width width;
	
	private Unit unit;
	
	private Integer contentLength;

}
