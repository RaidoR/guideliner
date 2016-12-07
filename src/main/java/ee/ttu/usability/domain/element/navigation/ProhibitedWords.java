package ee.ttu.usability.domain.element.navigation;

import jevg.ee.ttu.dataproperty.Unit;
import jevg.ee.ttu.dataproperty.UnitAction;
import lombok.Data;

@Data
public class ProhibitedWords {

	private String value;

	private Unit unit;

	private UnitAction unitAction;
	
}
