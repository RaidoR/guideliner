package ee.ttu.usability.guideliner.domain.attribute;

import ee.ttu.usability.guideliner.domain.element.navigation.ProhibitedWords;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AlternativeText extends AbstractAttribute {

	private boolean isValued = false;
	
	private ProhibitedWords prohibitedWords;
	
}
