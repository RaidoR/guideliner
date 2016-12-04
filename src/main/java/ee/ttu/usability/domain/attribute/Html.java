package ee.ttu.usability.domain.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Html extends AbstractAttribute {

	private boolean isValid;
	
	private Lang lang;
	
}
