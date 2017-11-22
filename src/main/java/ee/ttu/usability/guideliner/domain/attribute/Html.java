package ee.ttu.usability.guideliner.domain.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Html extends AbstractAttribute {

	private boolean isValid;
	
	private Lang lang;
	
	private AlternativeText alternativeText;

	private Viewport viewport;

	private Flash flash;

}
