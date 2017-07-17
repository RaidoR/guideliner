package ee.ttu.usability.domain.attribute;

import jevg.ee.ttu.dataproperty.DistanceType;
import jevg.ee.ttu.dataproperty.Unit;
import lombok.Data;

@Data
public class Distance extends AbstractAttribute {

    private Unit unit;

    private Integer contentLength;

    private DistanceType distanceType;

}
