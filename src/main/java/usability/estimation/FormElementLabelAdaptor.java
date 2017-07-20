package usability.estimation;


import ee.ttu.usability.domain.element.form.FormElementLabel;
import ee.ttu.usability.domain.element.link.Link;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import usability.estimation.result.ElementType;
import usability.estimation.result.EvaluationResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormElementLabelAdaptor extends AbstractAdaptor {

    public EvaluationResult execute(FormElementLabel label) throws IOException {
        if (label.getPositionType() != null) {
            return evaluatePositionType(label);
        }
        throw new RuntimeException("Cannot process element");
    }

    private EvaluationResult evaluatePositionType(FormElementLabel elementLabel) {
        EvaluationResult result = new EvaluationResult();
        result.setElementType(ElementType.LINK);

        List<String> labels = new ArrayList<>();

        for (WebElement label : driver.findElements(By.cssSelector("*"))) {
            if ("label".equals(label.getTagName())  ) {
                System.out.println("label " + label.getText() + " -- " + label.getLocation());
            } else if ("input".equals(label.getTagName()) && (label.getAttribute("type") == null
                    || !label.getAttribute("type").equals("hidden"))) {
                System.out.println("input " + label.getAttribute("name") + " " + label.getLocation());
//                System.out.println(label.getRect());
            }
//            if (label.getText().length() > 0) {
//                System.out.println(label.getText());
//                labels.add(label.getText());
//                System.out.println(label.getLocation());
//
//                String xpath = "//label[text()='" + label.getText() + "']/input";
//                WebElement element = driver.findElement(By.xpath(xpath));
//                System.out.println(element.getLocation());
//            }
        }

        for (String label : labels) {
            System.out.println(label);
            String xpath = "//label[text()='" + label + "']/input";
            WebElement element = driver.findElement(By.xpath(xpath));
        }

        return setSuccessFlag(result);
    }

    public List<WebElement> getLabels(WebDriver driver) {
        return driver.findElements(By.tagName("label"));
    }

}
