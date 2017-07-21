package usability.estimation;

import ee.ttu.usability.domain.element.form.Input;
import ee.ttu.usability.domain.element.link.Link;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;
import usability.estimation.result.ResultType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jevge on 21/07/2017.
 */
public class InputAdaptor extends AbstractAdaptor {

    public EvaluationResult execute(Input input) throws IOException {
        if (input.getColor() != null && input.getColor().getIsSame() != null && input.getIsSelected()) {
            return evaluateSelectedInput(input);
        }
        throw new RuntimeException("Cannot evaluate");
    }

    private EvaluationResult evaluateSelectedInput(Input input) throws IOException {
        screenshot = screenshoter.makeScreenshot(driver);

        EvaluationResult result = new EvaluationResult();
        result.setElementType(ElementType.LINK);


        List<WebElement> elements = driver.findElements(By.xpath("//input[@type='text']"));

        for (WebElement element : elements) {
            if (!element.isDisplayed()) {
                continue;
            }
            System.out.println(element.isEnabled());
            String colorBefore = element.getCssValue("border-top-color");
            element.click();
            String colorAfter = element.getCssValue("border-top-color");
            if (colorBefore.equals(colorAfter)) {
                File file = screenshoter.takeScreenshot(screenshot, element, driver);
                result.getFailedElements().add(prepareFailedElement(
                        ElementType.INPUT.name(), element.getAttribute("outerHTML"), "Input should be highligted after selected" , file));
                result.setResult(ResultType.FAIL);            }
        }

        return setSuccessFlag(result);
    }
}
