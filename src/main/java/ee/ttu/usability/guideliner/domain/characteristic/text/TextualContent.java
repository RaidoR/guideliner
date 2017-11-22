package ee.ttu.usability.guideliner.domain.characteristic.text;

import ee.ttu.usability.guideliner.domain.attribute.Font;
import ee.ttu.usability.guideliner.domain.attribute.textcase.TextCase;
import ee.ttu.usability.guideliner.domain.characteristic.ContentType;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TextualContent extends ContentType {

	protected String value;
	
	protected Font font;
	
	protected TextCase textCase;
	
}
