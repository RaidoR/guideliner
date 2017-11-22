package ee.ttu.usability.guideliner.domain.element.link;

import ee.ttu.usability.guideliner.domain.characteristic.text.TextualContent;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TextualLink extends Link{
	
	private TextualContent textualContent;
	
}
