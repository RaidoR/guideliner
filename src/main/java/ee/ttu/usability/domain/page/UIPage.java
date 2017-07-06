package ee.ttu.usability.domain.page;

import lombok.Data;
import ee.ttu.usability.domain.attribute.Href;
import ee.ttu.usability.domain.attribute.Html;
import ee.ttu.usability.domain.attribute.Title;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import ee.ttu.usability.domain.element.navigation.ProhibitedWords;
import ee.ttu.usability.domain.pageattributes.HorizontalScroll;
import ee.ttu.usability.domain.pageattributes.VerticalScroll;

@Data
public class UIPage extends UsabilityGuideline {

	private HorizontalScroll horizontalScroll;

	private VerticalScroll verticalScroll;
	
	private ProhibitedWords prohibitedWords;
	
    private Layout layout;
    
    private LoadTime loadTime;
    
    private Text text;
    
    private Html html;
    
    private Href href;
    
    private Title title;
    
    
}
