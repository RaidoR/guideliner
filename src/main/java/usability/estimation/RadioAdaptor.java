package usability.estimation;


import ee.ttu.usability.domain.element.form.Radio;
import ee.ttu.usability.domain.element.link.Link;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RadioAdaptor extends AbstractAdaptor {

    public EvaluationResult execute(Radio radio) throws IOException {
        if (radio.getLayout() != null && radio.getLayout().getLayoutType() != null) {
            return evaluateLayout(radio);
        }
        return null;
    }

    private EvaluationResult evaluateLayout(Radio radio) throws IOException {
        screenshot = screenshoter.makeScreenshot(driver);
        EvaluationResult result = new EvaluationResult();
        result.setElementType(ElementType.LINK);

        List<Integer> radiosWihtCoordinates = new ArrayList<>();

        List<WebElement> elements = driver.findElements(By.xpath("//input[@type='radio']"));

        for (WebElement radioEl : elements) {
            if (existsElementByY(radioEl.getLocation().getY(), radiosWihtCoordinates)) {
                File file = screenshoter.takeScreenshot(screenshot, radioEl, driver);
                result.getFailedElements().add(prepareFailedElement("Label", radioEl.getAttribute("outerHTML"), "Radio buttons should be vertically aligned.", file));
            }
            radiosWihtCoordinates.add(radioEl.getLocation().getY());
        }

        return setSuccessFlag(result);
    }

    private boolean existsElementByY(Integer y, List<Integer> radiosWihtCoordinates) {
        for (Integer radioY : radiosWihtCoordinates) {
            if (radioY.equals(y)) {
                return true;
            }
        }
        return false;
    }

}
