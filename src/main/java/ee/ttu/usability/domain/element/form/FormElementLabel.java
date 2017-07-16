package ee.ttu.usability.domain.element.form;

import ee.ttu.usability.domain.attribute.Label;
import ee.ttu.usability.domain.element.UsabilityGuideline;
import ee.ttu.usability.domain.structure.Position;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FormElementLabel extends UsabilityGuideline {

    private PositionType positionType;
}
