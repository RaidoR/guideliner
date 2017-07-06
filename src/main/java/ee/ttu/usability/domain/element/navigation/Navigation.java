package ee.ttu.usability.domain.element.navigation;


import lombok.Getter;
import lombok.Setter;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import ee.ttu.usability.domain.structure.Position;

@Getter@Setter
public class Navigation extends UsabilityGuideline {

	private Position position;
	
	private HorizontalScroll horizontalScroll;
	
	private ID id;
	
}
