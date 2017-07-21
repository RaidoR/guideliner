package usability.estimation;


import ee.ttu.usability.domain.element.form.FormElementLabel;
import ee.ttu.usability.domain.element.link.Link;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormElementLabelAdaptor extends AbstractAdaptor {

    public EvaluationResult execute(FormElementLabel label) throws IOException {
        if (label.getPositionType() != null) {
            return evaluatePositionType(label);
        }
        throw new RuntimeException("Cannot process element");
    }

    private EvaluationResult evaluatePositionType(FormElementLabel elementLabel) throws IOException {
        screenshot = screenshoter.makeScreenshot(driver);

        EvaluationResult result = new EvaluationResult();
        result.setElementType(ElementType.FORMELEMENTLABEL);

        Map<Integer, WebElement> labelsWithCoordinates = new HashedMap();

        for (WebElement label : driver.findElements(By.cssSelector("*"))) {
            if ("label".equals(label.getTagName())) {
                System.out.println(label.getText() + " -- " + label.getLocation().getY());
                labelsWithCoordinates.put(label.getLocation().getY(), label);
            }
        }

        for (WebElement input : driver.findElements(By.cssSelector("*"))) {
            if ("input".equals(input.getTagName()) && (input.getAttribute("type") == null
                    || !input.getAttribute("type").equals("hidden"))) {
                WebElement labelWithSameCoordinate = getElementByY(input.getLocation().getY(), labelsWithCoordinates);
                System.out.println("input" + " -- " + input.getAttribute("name") + " -- " + input.getLocation().getY() + " label with coord" + (labelWithSameCoordinate != null ? labelWithSameCoordinate.getText() : null));
                if (labelWithSameCoordinate != null) {
                    File file = screenshoter.takeScreenshot(screenshot, labelWithSameCoordinate, driver);
                    result.getFailedElements().add(prepareFailedElement("Label", labelWithSameCoordinate.getText(), "Label should be above input.", file));
                }
            }
        }

        return setSuccessFlag(result);
    }

    private WebElement getElementByY(Integer y, Map<Integer, WebElement> labelsWithCoordinates) {

        for (Map.Entry<Integer, WebElement> entry : labelsWithCoordinates.entrySet()) {
            if (entry.getKey().equals(y)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public List<WebElement> getLabels(WebDriver driver) {
        return driver.findElements(By.tagName("label"));
    }

}
