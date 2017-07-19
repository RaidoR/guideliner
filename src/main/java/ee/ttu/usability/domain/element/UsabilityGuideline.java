package ee.ttu.usability.domain.element;

import ee.ttu.usability.domain.attribute.*;
import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;
import ee.ttu.usability.domain.pageattributes.Height;

@Data
public class UsabilityGuideline {

	private Contrast contrast;
	
	private Distance distance;
	
	private ProhibitedWordType prohibitedWordType;
	
	private Height height;
	
	private Width width;
	
	private Unit unit;
	
	private Integer contentLength;
	
	private String url;

	private Color color;

}
