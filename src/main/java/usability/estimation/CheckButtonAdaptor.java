package usability.estimation;


import ee.ttu.usability.domain.element.form.CheckBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckButtonAdaptor extends AbstractAdaptor {

    public EvaluationResult execute(CheckBox checkBox) throws IOException {
        if (checkBox.getLayout() != null && checkBox.getLayout().getLayoutType() != null) {
            return evaluateLayout(checkBox);
        }
        return null;
    }

    private EvaluationResult evaluateLayout(CheckBox checkBox) throws IOException {
        screenshot = screenshoter.makeScreenshot(driver);
        EvaluationResult result = new EvaluationResult();
        result.setElementType(ElementType.LINK);

        List<Integer> radiosWihtCoordinates = new ArrayList<>();

        List<WebElement> elements = driver.findElements(By.xpath("//input[@type='checkbox']"));

        for (WebElement radioEl : elements) {
            if (existsElementByY(radioEl.getLocation().getY(), radiosWihtCoordinates)) {
                File file = screenshoter.takeScreenshot(screenshot, radioEl, driver);
                result.getFailedElements().add(prepareFailedElement(ElementType.CHECKBOX.name(), radioEl.getAttribute("outerHTML"), "Checkbox should be vertically aligned.", file));
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
