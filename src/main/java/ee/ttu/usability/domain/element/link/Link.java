package ee.ttu.usability.domain.element.link;

import ee.ttu.usability.domain.attribute.*;
import lombok.Getter;
import lombok.Setter;
import ee.ttu.usability.domain.element.UsabilityGuideline;

@Getter
@Setter
public class Link extends UsabilityGuideline {

	private Href href;

	private Title title;

	private LinkType linkType;

	private AlternativeText alternativeText;

	private Width width;

}
