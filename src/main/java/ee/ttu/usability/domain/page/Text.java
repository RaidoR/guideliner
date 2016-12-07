package ee.ttu.usability.domain.page;

import ee.ttu.usability.domain.attribute.AbstractAttribute;
import jevg.ee.ttu.dataproperty.Case;
import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;

@Data
public class Text extends AbstractAttribute {
	
	private Case caseType;
	
	private Integer contentLength;
	
	private Unit unit;
	
}
