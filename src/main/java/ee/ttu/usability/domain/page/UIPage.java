package ee.ttu.usability.domain.page;

import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import ee.ttu.usability.domain.pageattributes.VerticalScroll;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UIPage extends GuidelinetElement {

	private HorizontalScroll horizontalScroll;

	private VerticalScroll verticalScroll;

}
