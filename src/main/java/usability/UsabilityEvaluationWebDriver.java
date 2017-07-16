package usability;


import ee.ttu.usability.domain.pageattributes.Scroll;
import org.openqa.selenium.WebElement;

public interface UsabilityEvaluationWebDriver {

    void existsScrolling(Scroll scroll);

    void scroll(Integer pixelToScroll, Scroll scrollType);

    void getContrastRate(WebElement element);

}
