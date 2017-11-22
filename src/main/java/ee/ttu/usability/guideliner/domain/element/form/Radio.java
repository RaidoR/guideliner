package ee.ttu.usability.guideliner.domain.element.form;

import ee.ttu.usability.guideliner.domain.element.UsabilityGuideline;
import ee.ttu.usability.guideliner.domain.page.Layout;
import lombok.Data;

@Data
public class Radio extends UsabilityGuideline {

    private Layout layout;
}
