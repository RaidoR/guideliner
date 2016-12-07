package ee.ttu.usability.domain.element.link;

import ee.ttu.usability.domain.attribute.AlternativeText;
import lombok.Getter;
import lombok.Setter;
import ee.ttu.usability.domain.attribute.Href;
import ee.ttu.usability.domain.attribute.LinkType;
import ee.ttu.usability.domain.attribute.Title;
import ee.ttu.usability.domain.element.GuidelinetElement;

@Getter
@Setter
public class Link extends GuidelinetElement {

	private Href href;

	private Title title;

	private LinkType linkType;

	private AlternativeText alternativeText;

}
