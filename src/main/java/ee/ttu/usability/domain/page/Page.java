package ee.ttu.usability.domain.page;

import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import ee.ttu.usability.domain.pageattributes.VerticalScroll;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

	private HorizontalScroll horizontalScroll;

	private VerticalScroll verticalScroll;

}
