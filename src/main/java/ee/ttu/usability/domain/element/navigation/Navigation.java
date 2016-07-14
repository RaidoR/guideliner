package ee.ttu.usability.domain.element.navigation;


import lombok.Getter;
import lombok.Setter;
import ee.ttu.usability.domain.element.GuidelinetElement;
import ee.ttu.usability.domain.structure.Position;

@Getter@Setter
public class Navigation extends GuidelinetElement{

	private Position position;
	
}
